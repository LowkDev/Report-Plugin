package me.mherzaqaryan.report.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.mherzaqaryan.report.ReportPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL {

    private HikariDataSource ds;
    private Connection connection = null;

    public MySQL(String host, String port, String database, String username, String password) {
        ReportPlugin.getInstance().getLogger().info("Connecting To MySQL Database, Please Wait...");
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
            ReportPlugin.getInstance().getLogger().info("MySQL Database Successfully Connected And Established.");
        } catch (SQLException e) {
            ReportPlugin.getInstance().getLogger().severe("Can't Connect To MySQL Database, Something Went Wrong.");
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
            ReportPlugin.getInstance().getLogger().severe(e.getMessage());
        }
    }

}
