package me.mherzaqaryan.report.data;

import me.mherzaqaryan.report.ReportPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {

    public static FileConfiguration getData() {
        return ReportPlugin.getConfigData().getConfig();
    }

    public static String db_host = getData().getString("mysql.host");
    public static String db_port = getData().getString("mysql.port");
    public static String db_name = getData().getString("mysql.database");
    public static String db_user = getData().getString("mysql.username");
    public static String db_pass = getData().getString("mysql.password");

}
