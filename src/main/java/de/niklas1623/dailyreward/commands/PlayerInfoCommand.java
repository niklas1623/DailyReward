package de.niklas1623.dailyreward.commands;

import de.niklas1623.dailyreward.DailyReward;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerInfoCommand implements CommandExecutor {

    private DailyReward pl;

    public PlayerInfoCommand(DailyReward plugin) {
        this.pl = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        String prefix = pl.prefix;
        if (cmd.getName().equalsIgnoreCase("playerinfo")) {
            if (sender.hasPermission("dailyreward.playerinfo") || sender.hasPermission("dailyreward.*")) {
                //player playername
                if (args.length == 1) {
                    String playername = args[0];
                    Player player = Bukkit.getPlayer(playername);
                    sender.sendMessage(pl.UUIDofPlayer.replaceAll("%player%", playername).replaceAll("%uuid%", getUUID(playername)));
                } else {
                    sender.sendMessage(pl.HowToPInfo);
                    return true;
                }
            } else {
                sender.sendMessage(pl.NoPerm);
                return true;
            }

        }



        return false;
    }


    @SuppressWarnings("deprecation")
    private String getUUID(String playername) {
        return Bukkit.getOfflinePlayer(playername).getUniqueId().toString();
    }
}
