package de.niklas1623.dailyreward.listeners;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.niklas1623.dailyreward.DailyReward;
import de.niklas1623.dailyreward.util.DailyRewardManager;
import de.niklas1623.dailyreward.util.Filemanager;

public class JoinListener implements Listener {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String playername = p.getName();
        String uuid = p.getUniqueId().toString();

        if (DailyRewardManager.getPID(uuid) >= 1) {
            //Bukkit.broadcastMessage("§e"+playername + " joined the game.");
            int pID = DailyRewardManager.getPID(uuid);
            countDays(uuid, playername, p);
            giveDailyReward(uuid, playername, p);
            DailyRewardManager.updatePlayerData(pID, playername);
        } else {
            DailyRewardManager.insertPlayerIntoDB(playername, uuid);
            DailyRewardManager.FirstJoin(DailyRewardManager.getPID(uuid));
        }
    }

    public void giveDailyReward(String uuid, String playername, Player p) {
        int pID = DailyRewardManager.getPID(uuid);
        String DBDate = sdf.format(DailyRewardManager.getDate(pID));
        if (!DBDate.equalsIgnoreCase(sdf.format(today()))) {
            DailyRewardManager.onJoin(pID);
            Bukkit.getScheduler().runTaskLater(DailyReward.getInstace(), new Runnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                            DailyReward.getInstace().RewardCommand.replaceAll("%player%", playername));
                    if (DailyReward.getInstace().MSG_Setting) {
                        p.sendMessage(DailyReward.getInstace().RewardMSG.replaceAll("%player%", playername));
                    }
                }
            }, 40);

        } else {
            DailyRewardManager.onJoin(pID);
        }
    }

    public void countDays(String uuid, String playername, Player player) {
        int pID = DailyRewardManager.getPID(uuid);
        String DBDate = sdf.format(DailyRewardManager.getDate(pID));
        Bukkit.getLogger().log(Level.INFO, DBDate);
        if (!DBDate.equalsIgnoreCase(sdf.format(today()))) {
            if (DBDate.equalsIgnoreCase(sdf.format(yesterday()))) {
                Bukkit.getLogger().log(Level.INFO, sdf.format(yesterday()));
                int onlinedays = DailyRewardManager.getDays(pID);
                int new_onlinedays = onlinedays + 1;
                Bukkit.getLogger().log(Level.INFO, "onlinedays " + onlinedays);
                Bukkit.getLogger().log(Level.INFO, "new_onlinedays " + new_onlinedays);
                DailyRewardManager.setDays(pID, new_onlinedays);
                if (Filemanager.cfg.getString("Rewards." + new_onlinedays + ".Cmd") != null) {
                    String CMD = Filemanager.getDayReward(new_onlinedays, playername);
                    Bukkit.getLogger().log(Level.INFO, CMD);
                    Bukkit.getScheduler().runTaskLater(DailyReward.getInstace(), new Runnable() {
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), CMD);
                            if (Filemanager.cfg.getString("Rewards." + new_onlinedays + ".Msg") != null) {
                                player.sendMessage(Filemanager.getDayRewardMSG(new_onlinedays));
                            }
                        }
                    }, 40);
                }
            } else {
                DailyRewardManager.setDays(DailyRewardManager.getPID(uuid), 1);
            }
        }


    }

    private static Date yesterday() {
        Date today = new Date(System.currentTimeMillis());
        Date yesterday = new Date(today.getTime() - (1000 * 60 * 60 * 24));
        return yesterday;
    }

    private static Date today() {
        Date today = new Date(System.currentTimeMillis());
        return today;
    }

}
