package de.niklas1623.dailyreward.util;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.niklas1623.dailyreward.database.MySQL;

public class DailyRewardManager {

    /**
     * private static java.sql.Date getCurrentDate() {
     * java.util.Date today = new java.util.Date();
     * return new java.sql.Date(today.getTime());
     * }
     **/

    private static Date getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }


    public static void insertPlayerIntoDB(String playername, String uuid) {
        String insertIntoDB = "INSERT INTO player_data (playername, uuid) VALUES (?, ?)";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(insertIntoDB);
            ps.setString(1, playername);
            ps.setString(2, uuid);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static void updatePlayerData(int pID, String playername) {
        String updatePlayerData = "UPDATE player_data SET playername = ? WHERE pid = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(updatePlayerData);
            ps.setString(1, playername);
            ps.setInt(2, pID);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static int getPID(String uuid) {
        String getPID = "SELECT pid FROM player_data WHERE uuid = ?";
        try {
            PreparedStatement ps = MySQL.con.prepareStatement(getPID);
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("pid");
            }
            ps.close();
            rs.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static void FirstJoin(int pid) {
        String firstjoin = "INSERT INTO player_stats (pid, lastjoin, onlinedays) values (?, ?, ?);";
        try {
            PreparedStatement ps_firstjoin = MySQL.con.prepareStatement(firstjoin);
            ps_firstjoin.setInt(1, pid);
            ps_firstjoin.setDate(2, getCurrentDate());
            ps_firstjoin.setInt(3, 1);

            ps_firstjoin.execute();
            ps_firstjoin.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void resetDBStats() {
        String cmd = "UPDATE player_stats SET onlinedays = 1 WHERE 1";
        PreparedStatement ps = null;
        try {
            ps = MySQL.con.prepareStatement(cmd);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public static void onJoin(int pID) {
        String join = "UPDATE player_stats SET lastjoin = ? WHERE pid = ?";
        try {
            PreparedStatement ps_join = MySQL.con.prepareStatement(join);
            ps_join.setDate(1, getCurrentDate());
            ps_join.setInt(2, pID);

            ps_join.executeUpdate();
            ps_join.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Date getDate(int pID) {
        String getDate = "SELECT lastjoin FROM player_stats WHERE pid = ?";
        try {
            PreparedStatement ps_getDate = MySQL.con.prepareStatement(getDate);
            ps_getDate.setInt(1, pID);
            ResultSet rs = ps_getDate.executeQuery();
            while (rs.next()) {

                return rs.getDate(1);
            }
            ps_getDate.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void setDays(int pID, int onlinedays) {
        String days = "UPDATE player_stats SET onlinedays = ? WHERE pid = ?";
        try {
            PreparedStatement ps_days = MySQL.con.prepareStatement(days);
            ps_days.setInt(1, onlinedays);
            ps_days.setInt(2, pID);

            ps_days.executeUpdate();
            ps_days.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getDays(int pID) {
        String getDays = "SELECT onlinedays FROM player_stats WHERE pid = ?";
        try {
            PreparedStatement ps_getDays = MySQL.con.prepareStatement(getDays);
            ps_getDays.setInt(1, pID);
            ResultSet rs = ps_getDays.executeQuery();
            while (rs.next()) {
                return rs.getInt("onlinedays");
            }
            ps_getDays.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
