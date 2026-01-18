package mnt.infiniteParkour;

import org.bukkit.plugin.java.JavaPlugin;

public final class InfiniteParkour extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("\u001b[35m\u001b[1m[InfiniteParkour] Starting up with style!\u001b[0m");
        getCommand("sendtoparkour").setExecutor(new mnt.infiniteParkour.commands.sendPlayerToParkour());
        getCommand("testnext").setExecutor(new mnt.infiniteParkour.commands.testNext());
        getServer().getPluginManager().registerEvents(new mnt.infiniteParkour.event.jumpListener(), this);

    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("\u001b[35m\u001b[1m[InfiniteParkour] Shutting down with style!\u001b[0m");
    }
}
