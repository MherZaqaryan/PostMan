package me.MrIronMan.postman.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.MrIronMan.postman.PostMan;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {

    private HikariDataSource ds;
    private Connection connection = null;

    public MySQL(String host, String port, String database, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://"+host+":"+port+"/"+database);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
        try {
            this.connection = ds.getConnection();
            PostMan.getInstance().getLogger().info("MySQL database successfully connected and established.");
        } catch (SQLException e) {
            PostMan.getInstance().getLogger().info("Cant connect to MySQL database, something went wrong.");
        }
    }

    public Connection getConnection() {
        if (!isConnected()) return null;
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
