package me.mherzaqaryan.report;

import me.mherzaqaryan.report.commands.ReportCommand;
import me.mherzaqaryan.report.commands.SpectateCommand;
import me.mherzaqaryan.report.data.ConfigUtil;
import me.mherzaqaryan.report.data.CustomConfig;
import me.mherzaqaryan.report.listeners.PluginMessageListener;
import me.mherzaqaryan.report.listeners.ReportListener;
import me.mherzaqaryan.report.sql.MySQL;
import me.mherzaqaryan.report.sql.SQLData;
import net.md_5.bungee.api.plugin.Plugin;

import java.time.format.DateTimeFormatter;

public class ReportPlugin extends Plugin {

    private static ReportPlugin instance;

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private CustomConfig config;

    private MySQL mySQL;
    private SQLData sqlData;

    @Override
    public void onEnable() {
        instance = this;
        getProxy().registerChannel("Report");
        this.config = new CustomConfig("Config.yml");
        connectMySQL();
        this.getProxy().getPluginManager().registerListener(this, new PluginMessageListener());
        this.getProxy().getPluginManager().registerListener(this, new ReportListener());
        this.getProxy().getPluginManager().registerCommand(this, new ReportCommand());
        this.getProxy().getPluginManager().registerCommand(this, new SpectateCommand());

    }

    @Override
    public void onDisable() {
        mySQL.disconnect();
    }

    public static DateTimeFormatter getDtf() {
        return dtf;
    }

    public static ReportPlugin getInstance() {
        return instance;
    }

    public void connectMySQL() {
        this.mySQL = new MySQL(ConfigUtil.db_host, ConfigUtil.db_port, ConfigUtil.db_name, ConfigUtil.db_user, ConfigUtil.db_pass);
        this.sqlData = new SQLData(mySQL);
    }

    public CustomConfig getConfigData() {
        return config;
    }

    public SQLData getSQLData() {
        return sqlData;
    }

}
