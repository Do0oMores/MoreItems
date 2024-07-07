package org.mores;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.mores.HealthPack.HealthPack;
import org.mores.HealthPack.HealthPackCommand;

import java.io.File;
import java.util.Objects;

public class moreItems extends JavaPlugin {
    public static moreItems instance;
    public FileConfiguration config;

    @Override
    public void onEnable() {
        //加载配置文件
        loadConfig();
        //注册监听器
        getServer().getPluginManager().registerEvents(new MarkItemListener(), this);
        getServer().getPluginManager().registerEvents(new HealthPack(this), this);
        //注册指令
        Objects.requireNonNull(this.getCommand("gethealthpack")).setExecutor(new HealthPackCommand());
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = getConfig();
    }

    public static moreItems getInstance(){
        return instance;
    }
}
