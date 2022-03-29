package de.niklas1623.dailyreward.util;

import java.io.File;

import com.sun.org.apache.xerces.internal.dom.CDATASectionImpl;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.niklas1623.dailyreward.DailyReward;
import de.niklas1623.dailyreward.database.MySQL;

public class Filemanager {

    public static File getConfigFile() {
        return new File("plugins/DailyReward", "config.yml");

    }

    public static FileConfiguration getConfigFileConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfigFile());
    }


    public static void readConfig() {
        FileConfiguration cfg = getConfigFileConfiguration();
        DailyReward.getInstace().prefix = ChatColor.translateAlternateColorCodes('&', cfg.getString("Settings.Prefix") + "§r");
        DailyReward.getInstace().RewardCommand = ChatColor.translateAlternateColorCodes('&', cfg.getString("Settings.RewardCommand"));
        MySQL.username = cfg.getString("Database.username");
        MySQL.password = cfg.getString("Database.password");
        MySQL.database = cfg.getString("Database.database");
        MySQL.host = cfg.getString("Database.host");
        MySQL.port = cfg.getString("Database.port");

    }

    public static void getDayReward(int onlinedays) {
        FileConfiguration cfg = getConfigFileConfiguration();
        DailyReward.getInstace().Reward = ChatColor.translateAlternateColorCodes('&', cfg.getString("Rewards."+onlinedays));
    }

}
