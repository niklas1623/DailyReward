package de.niklas1623.dailyreward.listeners;

import java.sql.Date;
import java.text.SimpleDateFormat;

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

        if (DailyRewardManager.isJoined(uuid)) {
            countDays(uuid, playername);
            giveDailyReward(uuid, playername);
        } else {
            DailyRewardManager.FirstJoin(uuid);
            DailyRewardManager.setDays(uuid, 1);
        }
    }

    public void giveDailyReward(String uuid, String playername) {
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        java.sql.Date letzterJoin = DailyRewardManager.getDate(uuid);
        if (letzterJoin.equals(today)) {
        } else {
            DailyRewardManager.Join(uuid);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                    DailyReward.getInstace().RewardCommand.replaceAll("%player%", playername));
        }
    }

    public void countDays(String uuid, String playername) {
        java.sql.Date lastjoin = DailyRewardManager.getDate(uuid);
        Bukkit.broadcastMessage(sdf.format(yesterday()));
        assert lastjoin != null;
        if (lastjoin.equals(yesterday())) {
            Bukkit.broadcastMessage("It worked!");
            int onlinedays = DailyRewardManager.getDays(uuid);
            onlinedays = onlinedays + 1;
            DailyRewardManager.setDays(uuid, onlinedays);
            if (Filemanager.getConfigFileConfiguration().getString("Rewards." + onlinedays) == null) {

            } else {
                Filemanager.getDayReward(onlinedays);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        DailyReward.getInstace().Reward.replaceAll("%player%", playername));
            }
        } else {
            DailyRewardManager.setDays(uuid, 1);
        }

    }

    private static Date yesterday() {
        Date today = new Date(System.currentTimeMillis());
        Date yesterday = new Date(today.getTime() - (1000*60*60*24));
        return yesterday;
    }

}
