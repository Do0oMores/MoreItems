package org.mores;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class moreItems extends JavaPlugin {
    Utils utils = new Utils();

    @Override
    public void onEnable() {
        handListener Listener = new handListener();
        //注册监听器
        getServer().getPluginManager().registerEvents(Listener, this);
        getLogger().info("Enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }

//    protected void startProgressBar(Player player, int maxProgress, int totalBars, Runnable onComplete, ItemStack itemStack) {
//        new BukkitRunnable() {
//            int progress = 0;
//
//            @Override
//            public void run() {
//                if (progress > maxProgress) {
//                    this.cancel();
//                    onComplete.run();
//                }
//                String progressBar = utils.setProgressBar(progress, maxProgress, totalBars, itemStack);
//                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
//                        TextComponent.fromLegacyText(progressBar));
//                progress++;
//            }
//            //20tick(1秒)执行一次
//        }.runTaskTimer(this, 0, 20);
//    }
}
