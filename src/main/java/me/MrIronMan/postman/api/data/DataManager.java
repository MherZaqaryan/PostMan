package me.MrIronMan.postman.api.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class DataManager {

    private Plugin plugin;
    private String name;
    private String dir;

    private YamlConfiguration yml;
    private File config;

    private boolean firstTime = false;

    public DataManager(Plugin plugin, String dir, String name) {
        this.plugin = plugin;
        this.name = name;
        this.dir = dir;
        createFile();
    }

    public void createFile() {
        File d = new File(dir);
        if (!d.exists() && !d.mkdirs()) {
            plugin.getLogger().warning("Couldn't create " + d.getPath());
            return;
        }
        this.config = new File(dir, name + ".yml");
        if (!this.config.exists()) {
            this.firstTime = true;
            plugin.getLogger().info("Creating data file " + name +".yml");
            try {
                if (!this.config.createNewFile()) {
                    plugin.getLogger().warning("Couldn't create " + this.config.getPath());
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.yml = YamlConfiguration.loadConfiguration(this.config);
        this.yml.options().copyDefaults(true);
    }

    public void reload() {
        this.yml = YamlConfiguration.loadConfiguration(this.config);
    }

    public void set(String path, Object value) {
        this.yml.set(path, value);
        save();
    }

    public YamlConfiguration getConfig() {
        return this.yml;
    }

    public void save() {
        try {
            this.yml.save(this.config);
        } catch (IOException e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }

    public String getString(String path) {
        return getConfig().getString(path);
    }

    public List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    public int getInt(String path) {
        return getConfig().getInt(path);
    }

    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public List<Integer> getIntegerList(String path) {
        return getConfig().getIntegerList(path);
    }

}
