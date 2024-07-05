package org.mores;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Objects;

public class moreItems extends JavaPlugin {
    public static moreItems instance;
    public FileConfiguration config;

    static Utils utils = new Utils();

    static Integer smallHoldTime;
    static Integer mediumHoldTime;
    static Integer largeHoldTime;

    @Override
    public void onEnable() {
        //加载配置文件
        loadConfig();
        //注册监听器
        getServer().getPluginManager().registerEvents(new handListener(), this);
        getServer().getPluginManager().registerEvents(new healthPack(this), this);
        //注册指令
        Objects.requireNonNull(this.getCommand("gethealthpack")).setExecutor(new MoreItemsCommand());
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }

    protected static void startProgressBar(Player player, Runnable onComplete, ItemStack itemStack) {
        int holdTime;
        int maxProgress;
        if (itemStack.equals(healthPack.getSmallHealthPack())) {
            holdTime = smallHoldTime;
            maxProgress = smallHoldTime;
        } else if (itemStack.equals(healthPack.getMediumHealthPack())) {
            holdTime = mediumHoldTime;
            maxProgress = mediumHoldTime;
        } else if (itemStack.equals(healthPack.getLargeHealthPack())) {
            holdTime = largeHoldTime;
            maxProgress = largeHoldTime;
        } else {
            throw new IllegalArgumentException("Invalid health pack");
        }
        new BukkitRunnable() {
            int progress = 0;

            @Override
            public void run() {
                if (healthPack.rightClickStartTimes.containsKey(player)) {
                    long duration = System.currentTimeMillis() - healthPack.rightClickStartTimes.get(player);
                    if (duration >= holdTime) {
                        this.cancel();
                        onComplete.run();
                        healthPack.rightClickStartTimes.remove(player);
                    } else {
                        progress = (int) (duration * maxProgress / holdTime);
                        String progressBar = utils.setProgressBar(progress, maxProgress, itemStack);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                TextComponent.fromLegacyText(progressBar));
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(getInstance(), 0, 20);
    }

    private void loadConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
        config = getConfig();
        // 获取配置值
        smallHoldTime = config.getInt("SmallHealthPack.UseTime");
        mediumHoldTime = config.getInt("MediumHealthPack.UseTime");
        largeHoldTime = config.getInt("LargeHealthPack.UseTime");
    }

    public static moreItems getInstance(){
        return instance;
    }
}
