package de.niklas1623.dailyreward.util;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import de.niklas1623.dailyreward.database.MySQL;

public class DailyRewardManager {

    /**
     * private static java.sql.Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Date(today.getTime());
    }
     **/

    private static Date getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        return date;
    }

    public static void FirstJoin(String uuid) {

        try {
            String firstjoin = "INSERT INTO player (uuid, lastjoin, onlinedays) values (?, ?, ?);";
            PreparedStatement ps_firstjoin = MySQL.con.prepareStatement(firstjoin);
            ps_firstjoin.setString(1, uuid);
            ps_firstjoin.setDate(2, getCurrentDate());
            ps_firstjoin.setInt(3, 1);

            ps_firstjoin.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Join(String uuid) {

        try {
            String join = "UPDATE player SET lastjoin = ? WHERE uuid = ?";
            PreparedStatement ps_join = MySQL.con.prepareStatement(join);
            ps_join.setDate(1, getCurrentDate());
            ps_join.setString(2, uuid);

            ps_join.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isJoined(String uuid) {
        ResultSet rs = MySQL.getResult("SELECT * FROM player WHERE uuid='" + uuid + "'");
        try {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Date getDate(String uuid) {
        String getDate = "SELECT lastjoin FROM player WHERE uuid = ?";
        try {
            PreparedStatement ps_getDate = MySQL.con.prepareStatement(getDate);
            ps_getDate.setString(1, uuid);
            ResultSet rs = ps_getDate.executeQuery();
            while (rs.next()) {
                return rs.getDate(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static void setDays(String uuid, int onlinedays) {
        String days = "UPDATE player SET onlinedays = ? WHERE uuid = ?";
        try {
            PreparedStatement ps_days = MySQL.con.prepareStatement(days);
            ps_days.setInt(1, onlinedays);
            ps_days.setString(2, uuid);

            ps_days.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getDays(String uuid) {
        String getDays = "SELECT onlinedays FROM player WHERE uuid = ?";
        try {
            PreparedStatement ps_getDays = MySQL.con.prepareStatement(getDays);
            ps_getDays.setString(1, uuid);
            ResultSet rs = ps_getDays.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
