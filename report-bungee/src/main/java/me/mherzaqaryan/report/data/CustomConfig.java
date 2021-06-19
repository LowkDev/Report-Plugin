package me.mherzaqaryan.report.data;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import me.mherzaqaryan.report.ReportPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Level;

public class CustomConfig {

    private File file;
    private Configuration configuration;
    private final String config;

    public CustomConfig(String config) {
        this.config = config;
        saveDefaultConfig();
    }

    public void saveDefaultConfig() {
        if (!ReportPlugin.getInstance().getDataFolder().exists())
            ReportPlugin.getInstance().getDataFolder().mkdir();

        file = new File(ReportPlugin.getInstance().getDataFolder(), config);

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                InputStream is = ReportPlugin.getInstance().getResourceAsStream(file.getName());
                try {
                    Files.copy(is, file.toPath());
                    reloadConfig();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        if (configuration == null)
            reloadConfig();
        return configuration;
    }

    public void saveConfig() {
        if (configuration == null || file == null)
            return;

        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
        } catch (IOException e) {
            ReportPlugin.getInstance().getLogger().log(Level.SEVERE, "Could not save " + config + " to" + file, e);
        }
    }

}
