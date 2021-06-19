package me.mherzaqaryan.report.data;

import net.md_5.bungee.config.Configuration;
import me.mherzaqaryan.report.ReportPlugin;

public class ConfigUtil {

    public static Configuration getData() {
        return ReportPlugin.getInstance().getConfigData().getConfig();
    }

    public static String db_host = getData().getString("mysql.host");
    public static String db_port = getData().getString("mysql.port");
    public static String db_name = getData().getString("mysql.database");
    public static String db_user = getData().getString("mysql.username");
    public static String db_pass = getData().getString("mysql.password");

    public static String PLAYER_OFFLINE = getData().getString("player-offline");
    public static String REPORT_USAGE = getData().getString("report-usage");
    public static String SELF_REPORT = getData().getString("self-report");

}
