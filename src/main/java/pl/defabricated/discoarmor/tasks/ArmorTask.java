package pl.defabricated.discoarmor.tasks;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pl.defabricated.discoarmor.ArmorPlugin;
import pl.defabricated.discoarmor.ArmorType;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class ArmorTask extends BukkitRunnable {

    ArmorPlugin plugin;

    public ArmorTask(ArmorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            ArmorType type = plugin.players.get(player.getName());
            if(type != null) {
                switch(type) {
                    case RANDOM:
                        Random random = new Random();
                        Color color = Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255));

                        for(int i=1; i<5; i++) {
                            ItemStack item = new ItemStack(Material.getMaterial(297 + i), 1);
                            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                            meta.setColor(color);
                            item.setItemMeta(meta);

                            PacketContainer packet = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
                            packet.getIntegers().write(0, player.getEntityId()).write(1, i );
                            packet.getItemModifier().write(0, item);

                            for(Player online : Bukkit.getOnlinePlayers()) {
                                if(!online.getName().equals(player.getName())) {
                                    try {
                                        plugin.protocolManager.sendServerPacket(online, packet);
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        break;
                    case ULTRA:
                        random = new Random();
                        for(int i=1; i<5; i++) {
                            color = Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255));

                            ItemStack item = new ItemStack(Material.getMaterial(297 + i), 1);
                            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                            meta.setColor(color);
                            item.setItemMeta(meta);

                            PacketContainer packet = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
                            packet.getIntegers().write(0, player.getEntityId()).write(1, i );
                            packet.getItemModifier().write(0, item);

                            for(Player online : Bukkit.getOnlinePlayers()) {
                                if(!online.getName().equals(player.getName())) {
                                    try {
                                        plugin.protocolManager.sendServerPacket(online, packet);
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        break;
                    case SMOOTH:
                        color = nextRGB(plugin.colors.get(player.getName()));
                        plugin.colors.put(player.getName(), color);
                        for(int i=1; i<5; i++) {
                            ItemStack item = new ItemStack(Material.getMaterial(297 + i), 1);
                            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                            meta.setColor(color);
                            item.setItemMeta(meta);

                            PacketContainer packet = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
                            packet.getIntegers().write(0, player.getEntityId()).write(1, i);
                            packet.getItemModifier().write(0, item);

                            for(Player online : Bukkit.getOnlinePlayers()) {
                                if(!online.getName().equals(player.getName())) {
                                    try {
                                        plugin.protocolManager.sendServerPacket(online, packet);
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        break;
                    case GRAY:
                        color = nextRGB(plugin.colors.get(player.getName()));
                        plugin.colors.put(player.getName(), color);
                        color = Color.fromRGB(color.getRed(), color.getRed(), color.getRed());

                        for(int i=1; i<5; i++) {
                            ItemStack item = new ItemStack(Material.getMaterial(297 + i), 1);
                            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
                            meta.setColor(color);
                            item.setItemMeta(meta);

                            PacketContainer packet = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_EQUIPMENT);
                            packet.getIntegers().write(0, player.getEntityId()).write(1, i);
                            packet.getItemModifier().write(0, item);

                            for(Player online : Bukkit.getOnlinePlayers()) {
                                if(!online.getName().equals(player.getName())) {
                                    try {
                                        plugin.protocolManager.sendServerPacket(online, packet);
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        break;
                }
            }
        }
    }

    private Color nextRGB(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        if(r == 255 && g < 255 && b == 0) {
            g += 15;
        }
        if(g == 255 && r > 0 && b == 0) {
            r -= 15;
        }
        if(g == 255 && b < 255 && r == 0) {
            b += 15;
        }
        if(b == 255 && g > 0 && r == 0) {
            g -= 15;
        }
        if(b == 255 && r < 255 && g == 0) {
            r += 15;
        }
        if(r == 255 && b > 0 && g == 0) {
            b -= 15;
        }

        color.setRed(r);
        color.setGreen(g);
        color.setBlue(b);

        return Color.fromRGB(r, g, b);
    }

}
