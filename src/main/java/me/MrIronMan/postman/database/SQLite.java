package me.MrIronMan.postman.database;

import me.MrIronMan.postman.PostMan;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLite {

    private File db = new File(PostMan.getInstance().getDataFolder(), "sqlite.db");
    private Connection connection;

    public SQLite() {
        if (!db.exists()) {
            try {
                db.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + db.getAbsolutePath());
            PostMan.getInstance().getLogger().info("SQLite database successfully connected and established.");
        }catch (ClassNotFoundException | SQLException e) {
            PostMan.getInstance().getLogger().info("Cant connect to SQLite database, something went wrong.");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connection != null;
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
