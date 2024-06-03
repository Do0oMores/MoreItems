package org.mores;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class moreItems extends JavaPlugin {
    Utils utils = new Utils();
    healthPack healthPack=new healthPack();

    @Override
    public void onEnable() {
        //注册监听器
        getServer().getPluginManager().registerEvents(new handListener(), this);
        getServer().getPluginManager().registerEvents(new healthPack(),this);
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }


    protected void startProgressBar(Player player, int maxProgress, int totalBars, Runnable onComplete, ItemStack itemStack, long holdTime) {
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
                        String progressBar = utils.setProgressBar(progress, maxProgress, totalBars, itemStack);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                                TextComponent.fromLegacyText(progressBar));
                    }
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(this, 0, 20);
    }
}
