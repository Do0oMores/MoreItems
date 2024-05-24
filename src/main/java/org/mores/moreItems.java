package org.mores;

import org.bukkit.plugin.java.JavaPlugin;

public class moreItems extends JavaPlugin {

    @Override
    public void onEnable(){
        handListener Listener=new handListener();
        //注册监听器
        getServer().getPluginManager().registerEvents(Listener,this);
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable(){
        getLogger().info("Disabled");

    }
}
