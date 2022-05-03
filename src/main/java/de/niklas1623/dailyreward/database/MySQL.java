package de.niklas1623.dailyreward.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import de.niklas1623.dailyreward.DailyReward;
import org.bukkit.Bukkit;

public class MySQL {

    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static String options;
    public static Connection con;

    public static void connect() {
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + options, username,
                        password);
                Bukkit.getConsoleSender().sendMessage(DailyReward.getInstace().prefix + " MySQL Verbindung §aaufgebaut§7!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                Bukkit.getConsoleSender()
                        .sendMessage(DailyReward.getInstace().prefix + " MySQL Verbindung §cfehlgeschlagen§7!");
            }
        }
    }

    public static void close() {
        if (isConnected()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage(DailyReward.getInstace().prefix + " MySQL Verbindung §cgeschlossen§r!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {
        return con != null;
    }

    public static void createTable() {
        if (isConnected()) {
            try {
                con.createStatement().executeUpdate(
                        "CREATE TABLE IF NOT EXISTS player_stats (pid INT NOT NULL , onlinedays INT NOT NULL , lastjoin DATE NOT NULL , PRIMARY KEY (pid))");
                con.createStatement().executeUpdate(
                        "CREATE TABLE IF NOT EXISTS player_data (pid INT NOT NULL AUTO_INCREMENT , playername VARCHAR(100) NOT NULL , uuid VARCHAR(100) NOT NULL , PRIMARY KEY (pid))");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
