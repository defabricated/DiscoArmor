package pl.defabricated.discoarmor;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import pl.defabricated.discoarmor.commands.ArmorCommand;
import pl.defabricated.discoarmor.listeners.PacketListener;
import pl.defabricated.discoarmor.listeners.PlayerKickListener;
import pl.defabricated.discoarmor.listeners.PlayerQuitListener;
import pl.defabricated.discoarmor.tasks.ArmorTask;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Random;

public class ArmorPlugin extends JavaPlugin {

    public ProtocolManager protocolManager;

    PacketListener packetListener;
    PlayerQuitListener playerQuitListener;
    PlayerKickListener playerKickListener;

    ArmorCommand armorCommand;

    BukkitTask armorTask;

    @Override
    public void onEnable() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();

        this.packetListener = new PacketListener(this);
        this.playerQuitListener = new PlayerQuitListener(this);
        this.playerKickListener = new PlayerKickListener(this);

        this.armorCommand = new ArmorCommand(this);

        this.armorTask = new ArmorTask(this).runTaskTimerAsynchronously(this, 1L, 1L);
    }

    @Override
    public void onDisable() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            updatePlayerArmor(player);
        }
    }

    public HashMap<String, ArmorType> players = new HashMap();
    public HashMap<String, Color> colors = new HashMap();

    public void updatePlayerArmor(Player player) {
        for(int i=1; i<5; i++) {
            Random random = new Random();

            PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
            packet.getIntegers().write(0, player.getEntityId()).write(1, i);
            packet.getItemModifier().write(0, player.getInventory().getArmorContents()[i - 1]);

            for(Player online : Bukkit.getOnlinePlayers()) {
                if(!online.getName().equals(player.getName())) {
                    try {
                        protocolManager.sendServerPacket(online, packet);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
