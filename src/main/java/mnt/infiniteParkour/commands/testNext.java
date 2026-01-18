package mnt.infiniteParkour.commands;

import mnt.infiniteParkour.event.jumpListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class testNext implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[!] Only players can use this command! [!]");
            return true;
        }
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("testnext")) {
            // Initialize game state and start generation
            jumpListener.resetScore(player);
            generateNextPlatform(player);
            return true;
        }

        return true;
    }

    public static void generateNextPlatform(Player player) {
        player.sendMessage("§6[!] Generating Next Platform! [!]");

        // --- Configuration ---
        int delta_x = 3;
        int delta_y = 0;
        int delta_z = 3;

        // --- Debug: Current Position ---
        // temp
        player.sendMessage(String.format("§7[Debug] Origin: X:%d Y:%d Z:%d",
                player.getLocation().getBlockX(),
                player.getLocation().getBlockY(),
                player.getLocation().getBlockZ()));

        // --- Logic: Calculate Target ---
        Location expected_loc = new Location(player.getWorld(),
                player.getLocation().getBlockX() + delta_x,
                player.getLocation().getBlockY() + delta_y,
                player.getLocation().getBlockZ() + delta_z
        );

        // --- Debug: Target Position ---
        // temp
        player.sendMessage(String.format("§7[Debug] Target: X:%d Y:%d Z:%d",
                expected_loc.getBlockX(),
                expected_loc.getBlockY(),
                expected_loc.getBlockZ()));

        // --- Execution: Place Block & Update Listener ---
        expected_loc.getBlock().setType(Material.STRIPPED_BIRCH_LOG);
        jumpListener.setExpectedLocation(player, expected_loc.getBlock().getLocation());
    }
}

