package me.MrIronMan.postman.database;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.configuration.ConfigData;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbManager {

    private Connection getConn() {
        if (ConfigData.MYSQL_ENABLED) {
            return PostMan.getInstance().getMySQL().getConnection();
        }else {
            return PostMan.getInstance().getSqLite().getConnection();
        }
    }

    public boolean createTable() {
        PreparedStatement ps;
        PreparedStatement ps2;
        try {
            String player_data = "CREATE TABLE IF NOT EXISTS player_data (" +
                    "player VARCHAR(50)," +
                    " uuid VARCHAR(50)," +
                    " email VARCHAR(50))";
            String subject_data = "CREATE TABLE IF NOT EXISTS subject_data (" +
                    "fileName VARCHAR(100)," +
                    " subject VARCHAR(200))";
            ps = getConn().prepareStatement(player_data);
            ps2 = getConn().prepareStatement(subject_data);
            ps.executeUpdate();
            ps2.executeUpdate();
            return false;
        }catch (SQLException e) {
            return true;
        }
    }

    public void addPlayer(Player player) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();
        if (!exists(name)) {
            try {
                PreparedStatement ps = getConn().prepareStatement("INSERT INTO player_data (player,uuid) VALUES (?,?)");
                ps.setString(1, name);
                ps.setString(2, uuid);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getPlayers() {
        List<String> emails = new ArrayList<>();
        try {
            PreparedStatement ps = getConn().prepareStatement("SELECT player FROM player_data");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emails.add(rs.getString("player"));
            }
            return emails;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setEmail(String player, String email) {
        try {
            PreparedStatement ps = getConn().prepareStatement("UPDATE player_data SET email=? WHERE player=?");
            ps.setString(1, email);
            ps.setString(2, player);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isVerified(String player) {
        return getEmail(player) != null;
    }

    public boolean isEmailOccupied(String email) {
        try {
            PreparedStatement ps = getConn().prepareStatement("SELECT * FROM player_data WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public String getEmail(String player) {
        try {
            PreparedStatement ps = getConn().prepareStatement("SELECT email FROM player_data WHERE player=?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            String s;
            if (rs.next()) {
                s = rs.getString("email");
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getEmails() {
        List<String> emails = new ArrayList<>();
        try {
            PreparedStatement ps = getConn().prepareStatement("SELECT email FROM player_data");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                emails.add(rs.getString("email"));
            }
            return emails;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean exists(String player) {
        try {
            PreparedStatement ps = getConn().prepareStatement("SELECT * FROM player_data WHERE player=?");
            ps.setString(1, player);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isFileExists(String fileName) {
        try {
            PreparedStatement ps = getConn().prepareStatement("SELECT * FROM subject_data WHERE fileName=?");
            ps.setString(1, fileName);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setSubject(String fileName, String subject) {
        String updateData = "UPDATE subject_data SET subject=? WHERE fileName=?";
        String createFile = "INSERT INTO subject_data (fileName,subject) VALUES (?,?)";
        try {
            if (isFileExists(fileName)) {
                PreparedStatement ps = getConn().prepareStatement(updateData);
                ps.setString(1, subject);
                ps.setString(2, fileName);
                ps.executeUpdate();

            }else {
                PreparedStatement ps2 = getConn().prepareStatement(createFile);
                ps2.setString(1, fileName);
                ps2.setString(2, subject);
                ps2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getSubject(String fileName) {
        try {
            PreparedStatement ps = getConn().prepareStatement("SELECT subject FROM subject_data WHERE fileName=?");
            ps.setString(1, fileName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("subject");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
