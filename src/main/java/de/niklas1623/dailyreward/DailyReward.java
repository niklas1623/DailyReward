package de.niklas1623.dailyreward;

import de.niklas1623.dailyreward.util.Filemanager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import de.niklas1623.dailyreward.listeners.JoinListener;
import de.niklas1623.dailyreward.commands.ReloadCommand;
import de.niklas1623.dailyreward.database.MySQL;

public class DailyReward extends JavaPlugin{

    public void onEnable() {
        instance = this;
        PluginDescriptionFile pdf = this.getDescription();
        registerEvents();
        registerCommand();
        saveDefaultConfig();
        Filemanager.readConfig();
        MySQL.connect();
        MySQL.createTable();
        Bukkit.getConsoleSender().sendMessage(prefix + " Plugin wurde §ageladen§r! §eVersion: §c"+ pdf.getVersion());

    }


    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new JoinListener(), this);

    }


    private void registerCommand() {
        ReloadCommand reloadCMD = new ReloadCommand(this);
        getCommand("dailyreload").setExecutor(reloadCMD);
    }

    public void onDisable() {
        MySQL.close();


    }


    public String prefix;
    public String RewardCommand;
    public String Reward;
    public String RewardMSG;
    public boolean MSG_Setting;




    public static DailyReward instance;

    public static DailyReward getInstace() {
        return instance;

    }

}
