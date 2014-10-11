package pl.defabricated.discoarmor.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.defabricated.discoarmor.ArmorPlugin;

public class PlayerQuitListener implements Listener {

    ArmorPlugin plugin;

    public PlayerQuitListener(ArmorPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.players.remove(player.getName());
        plugin.colors.remove(player.getName());
    }

}
