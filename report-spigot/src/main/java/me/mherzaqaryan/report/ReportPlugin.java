package me.mherzaqaryan.report;

import me.mherzaqaryan.report.commands.OpenReportCommands;
import me.mherzaqaryan.report.commands.ReportsCommand;
import me.mherzaqaryan.report.data.ConfigUtil;
import me.mherzaqaryan.report.data.CustomData;
import me.mherzaqaryan.report.listener.MenuListener;
import me.mherzaqaryan.report.menuSystem.PlayerMenuUtility;
import me.mherzaqaryan.report.sql.MySQL;
import me.mherzaqaryan.report.sql.SQLData;
import me.mherzaqaryan.report.utility.ProxyUtil;
import me.mherzaqaryan.report.utility.ReflectUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class ReportPlugin extends JavaPlugin {

    private static ReportPlugin instance;
    private static CustomData configData;
    private static ReflectUtils reflectUtils;

    private MySQL mySQL;
    private SQLData sqlData;

    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        reflectUtils = new ReflectUtils();
        configData = new CustomData("Config.yml");
        connectMySql();
        this.getCommand("openReport").setExecutor(new OpenReportCommands());
        this.getCommand("reports").setExecutor(new ReportsCommand());
        this.getServer().getPluginManager().registerEvents(new MenuListener(), this);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel( this, "Report", new ProxyUtil());
    }

    @Override
    public void onDisable() {
        this.getMySQL().disconnect();
    }

    public static ReportPlugin getInstance() {
        return instance;
    }

    public static CustomData getConfigData() {
        return configData;
    }

    public static ReflectUtils getReflectUtils() {
        return reflectUtils;
    }

    public void connectMySql() {
        this.mySQL = new MySQL(ConfigUtil.db_host, ConfigUtil.db_port, ConfigUtil.db_name, ConfigUtil.db_user, ConfigUtil.db_pass);
        this.sqlData = new SQLData(mySQL);

    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public SQLData getSQLData() {
        return sqlData;
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) {
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);
            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p);
        }
    }

    public static DateTimeFormatter getDtf() {
        return dtf;
    }
}
