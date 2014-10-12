package pl.defabricated.discoarmor.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.Packets;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import pl.defabricated.discoarmor.ArmorPlugin;
import pl.defabricated.discoarmor.ArmorType;

public class PacketListener extends PacketAdapter {

    ArmorPlugin plugin;

    public PacketListener(ArmorPlugin plugin) {
        super(plugin, ConnectionSide.SERVER_SIDE, Packets.Server.ENTITY_EQUIPMENT);
        this.plugin = plugin;
        plugin.protocolManager.addPacketListener(this);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        if(!event.isCancelled() && event.getPacketID() == Packets.Server.ENTITY_EQUIPMENT) {
            PacketContainer packet = event.getPacket();
            Entity entity = packet.getEntityModifier(event.getPlayer().getWorld()).read(0);
            if(entity instanceof Player) {
                Player player = (Player) entity;
                ArmorType type = plugin.players.get(player.getName());
                if(type != null) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
