package mnt.infiniteParkour.event;

import mnt.infiniteParkour.commands.testNext;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class jumpListener implements Listener {

    // --- State Tracking ---
    private static Map<UUID, Location> expectedLocations = new HashMap<>();
    private static Map<UUID, Integer> playerScores = new HashMap<>();
    private static Map<UUID, Location> startLocations = new HashMap<>();
    private static Map<UUID, Location> previousBlocks = new HashMap<>();

    // --- Public Utility Methods ---
    public static void setExpectedLocation(Player player, Location location) {
        expectedLocations.put(player.getUniqueId(), location);
    }

    public static void resetScore(Player player) {
        playerScores.put(player.getUniqueId(), 0);
        startLocations.put(player.getUniqueId(), player.getLocation());
        previousBlocks.remove(player.getUniqueId());
    }

    // --- Event Logic ---
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // 1. Movement Optimization
        if (event.getFrom().getX() == event.getTo().getX() &&
            event.getFrom().getY() == event.getTo().getY() &&
            event.getFrom().getZ() == event.getTo().getZ()) {
            return;
        }

        Player player = event.getPlayer();
        Location expectedLoc = expectedLocations.get(player.getUniqueId());

        if (expectedLoc == null) {
            return;
        }

        Location playerLoc = event.getTo();

        // 2. Failure Check (Fell 3 blocks below target)
        if (playerLoc.getY() < expectedLoc.getY() - 3) {
            handleFailure(player, expectedLoc);
            return;
        }

        // 3. Distance & Height Calculations
        Location targetCenter = expectedLoc.clone().add(0.5, 1.0, 0.5);
        double distanceXZSq = Math.pow(playerLoc.getX() - targetCenter.getX(), 2) + 
                             Math.pow(playerLoc.getZ() - targetCenter.getZ(), 2);
        boolean correctHeight = Math.abs(playerLoc.getY() - targetCenter.getY()) < 0.2;

        // --- temp: Debugging Output ---
        player.sendMessage(String.format("§7[Debug] DistSq: %.2f | Height: %.2f | Ground: %b", 
                distanceXZSq, Math.abs(playerLoc.getY() - targetCenter.getY()), player.isOnGround()));

        // 4. Success Check (Reached Platform)
        if (playerLoc.getWorld().equals(expectedLoc.getWorld()) && 
            distanceXZSq < 0.7 && 
            correctHeight && 
            player.isOnGround()) {
            
            handleSuccess(player, expectedLoc);
        }
    }

    // --- Logic Helpers ---
    private void handleFailure(Player player, Location expectedLoc) {
        UUID uuid = player.getUniqueId();
        int finalScore = playerScores.getOrDefault(uuid, 0);
        
        player.sendMessage("§c[!] You fell! Game Over. [!]");
        player.sendMessage("§6[!] Final Score: " + finalScore + " blocks reached! [!]");

        // Clean world
        expectedLoc.getBlock().setType(Material.AIR);
        Location oldBlockLoc = previousBlocks.get(uuid);
        if (oldBlockLoc != null) {
            oldBlockLoc.getBlock().setType(Material.AIR);
        }

        // Reset player
        Location startLoc = startLocations.get(uuid);
        if (startLoc != null) {
            player.teleport(startLoc);
        }

        // Clear Maps
        expectedLocations.remove(uuid);
        playerScores.remove(uuid);
        startLocations.remove(uuid);
        previousBlocks.remove(uuid);
    }

    private void handleSuccess(Player player, Location expectedLoc) {
        UUID uuid = player.getUniqueId();
        
        // Update Score
        int currentScore = playerScores.getOrDefault(uuid, 0) + 1;
        playerScores.put(uuid, currentScore);
        player.sendMessage("§a[!] reached! (Score: " + currentScore + ") [!]");

        // Cleanup previous block
        Location oldBlockLoc = previousBlocks.get(uuid);
        if (oldBlockLoc != null) {
            oldBlockLoc.getBlock().setType(Material.AIR);
        }

        // Cycle platform state
        previousBlocks.put(uuid, expectedLoc.clone());
        expectedLocations.remove(uuid);

        // Generate next
        testNext.generateNextPlatform(player);
    }
}