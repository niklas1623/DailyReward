package de.niklas1623.dailyreward.commands;

import de.niklas1623.dailyreward.DailyReward;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.niklas1623.dailyreward.util.Filemanager;

public class ReloadCommand implements CommandExecutor {

    private DailyReward plugin;

    public ReloadCommand(DailyReward plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String prefix = plugin.prefix;
        if (cmd.getName().equalsIgnoreCase("dailyreload")) {
            if (sender.hasPermission("dailyreward.reload") || sender.hasPermission("dailyreward.*")) {
                plugin.reloadConfig();
                Filemanager.readConfig();
                sender.sendMessage(prefix + " §aConfig wurde neu geladen!");

            } else
                sender.sendMessage(prefix + " §cDazu hast du keine Rechte!");
            return true;
        }
        return true;
    }

}
