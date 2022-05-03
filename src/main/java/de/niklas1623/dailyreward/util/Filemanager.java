package de.niklas1623.dailyreward.util;

import java.io.File;
import java.util.Objects;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.niklas1623.dailyreward.DailyReward;
import de.niklas1623.dailyreward.database.MySQL;

public class Filemanager {

    public static FileConfiguration cfg;

    private static DailyReward pl = DailyReward.getInstace();

    public static File getConfigFile() {
        return new File("plugins/DailyReward", "config.yml");

    }

    public static FileConfiguration getConfigFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());
    }

    public static String getMSG(String path) {
        String msg = "";
        msg = ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(cfg.getString(path)).replaceAll("%prefix%", pl.prefix));
        return msg;
    }


    public static void readConfig() {
        cfg = getConfigFileConfiguration();
        pl.prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("Settings.Prefix") + "§r");
        pl.MSG_Setting = cfg.getBoolean("Messages.DailyReward.Send");
        pl.RewardCommand = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(cfg.getString("Settings.RewardCommand")));
        pl.RewardMSG = getMSG("Messages.DailyReward.Msg");
        pl.NoPerm = getMSG("Messages.NoPerm");
        pl.HowToPInfo = getMSG("Messages.HowToPInfo");
        pl.UUIDofPlayer = getMSG("Messages.UUIDofPlayer");

        MySQL.username = cfg.getString("Database.username");
        MySQL.password = cfg.getString("Database.password");
        MySQL.database = cfg.getString("Database.database");
        MySQL.host = cfg.getString("Database.host");
        MySQL.port = cfg.getString("Database.port");
        MySQL.options = cfg.getString("Database.options");
    }


    public static String getDayReward(int onlinedays, String playername) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(cfg.getString("Rewards." + onlinedays + ".Cmd")).replaceAll("%player%", playername));
    }

    public static String getDayRewardMSG(int onlinedays) {
        return ChatColor.translateAlternateColorCodes('&', (Objects.requireNonNull(cfg.getString("Rewards." + onlinedays + ".Msg")).replaceAll("%prefix%", pl.prefix)));
    }

}
