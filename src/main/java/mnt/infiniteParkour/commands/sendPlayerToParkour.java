package mnt.infiniteParkour.commands;
import org.bukkit.command.Command;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class sendPlayerToParkour implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[!] Only players can use this command! [!]");
            return true;
        }
        Player player = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("sendtoparkour")) {
            player.sendMessage("§6[!] Sending to Parkour! [!]");

                // Define your coordinates here
                double x = 19;
                double y = 172.0;
                double z = 16.0;

                Location loc = new Location(player.getWorld(), x, y, z);
                player.teleport(loc);

                return true;
            }
        return true;
    }
}
