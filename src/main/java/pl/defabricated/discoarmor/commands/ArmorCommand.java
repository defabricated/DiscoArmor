package pl.defabricated.discoarmor.commands;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.defabricated.discoarmor.ArmorPlugin;
import pl.defabricated.discoarmor.ArmorType;

public class ArmorCommand implements CommandExecutor {

    ArmorPlugin plugin;

    public ArmorCommand(ArmorPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("armor").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Ta komenda nie moze zostac wywolana z konsoli!");
            return true;
        }
        Player player = (Player) sender;
        if(args.length != 1) {
            return false;
        }
        String arg = args[0];
        if(arg.equalsIgnoreCase("usun")) {
            plugin.players.remove(player.getName());
            plugin.colors.remove(player.getName());
            plugin.updatePlayerArmor(player);
            player.sendMessage(ChatColor.GOLD + "Tryb disco zbroi zostal usuniety!");
        } else if(arg.equalsIgnoreCase("random")) {
            plugin.colors.remove(player.getName());
            plugin.players.put(player.getName(), ArmorType.RANDOM);
            player.sendMessage(ChatColor.GOLD + "Tryb disco zbroi poprawnie ustawiony!");
        } else if(arg.equalsIgnoreCase("ultra")) {
            plugin.colors.remove(player.getName());
            plugin.players.put(player.getName(), ArmorType.ULTRA);
            player.sendMessage(ChatColor.GOLD + "Tryb disco zbroi poprawnie ustawiony!");
        } else if(arg.equalsIgnoreCase("smooth")) {
            plugin.colors.put(player.getName(), Color.fromRGB(255, 0, 0));
            plugin.players.put(player.getName(), ArmorType.SMOOTH);
            player.sendMessage(ChatColor.GOLD + "Tryb disco zbroi poprawnie ustawiony!");
        } else if(arg.equalsIgnoreCase("gray")) {
            plugin.colors.put(player.getName(), Color.fromRGB(255, 0, 0));
            plugin.players.put(player.getName(), ArmorType.GRAY);
            player.sendMessage(ChatColor.GOLD + "Tryb disco zbroi poprawnie ustawiony!");
        } else {
            return false;
        }
        return true;
    }

}
