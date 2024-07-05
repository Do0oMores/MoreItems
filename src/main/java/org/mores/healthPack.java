package org.mores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class healthPack implements Listener {
    private final moreItems plugin;

    protected static final Map<Player, Long> rightClickStartTimes = new ConcurrentHashMap<>();

    private static Integer smallHealth;
    private static Integer smallUseTime;
    private static Integer mediumHealth;
    private static Integer mediumUseTime;
    private static Integer largeHealth;
    private static Integer largeUseTime;

    Player player;

    public healthPack(moreItems plugin) {
        this.plugin = plugin;

        // 获取配置值
        FileConfiguration config = plugin.getConfig();
        smallHealth = config.getInt("SmallHealthPack.Health");
        smallUseTime = config.getInt("SmallHealthPack.UseTime");
        mediumHealth = config.getInt("MediumHealthPack.Health");
        mediumUseTime = config.getInt("MediumHealthPack.UseTime");
        largeHealth = config.getInt("LargeHealthPack.Health");
        largeUseTime = config.getInt("LargeHealthPack.UseTime");

        // 注册事件监听器
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //构建回血包物品
    private static ItemStack HealthPackShow(Integer health, Integer useTime, String itemName) {
        ItemStack healthPack = new ItemStack(Material.BOOK);
        ItemMeta healthPackMeta = healthPack.getItemMeta();
        assert healthPackMeta != null;
        healthPackMeta.setDisplayName(itemName);
        List<String> healthPackLore = new ArrayList<>();
        healthPackLore.add(ChatColor.DARK_PURPLE + "右键回复" + health + "点血量");
        healthPackLore.add(ChatColor.COLOR_CHAR + "o" + ChatColor.RED + "使用时间：" + useTime + "秒");
        healthPackMeta.setLore(healthPackLore);
        healthPack.setItemMeta(healthPackMeta);
        return healthPack;
    }

    //小型回血包(绷带)
    protected static ItemStack getSmallHealthPack() {
        return HealthPackShow(smallHealth, smallUseTime, ChatColor.GREEN + "小型回血包");
    }

    //中型回血包(医疗包)
    protected static ItemStack getMediumHealthPack() {
        return HealthPackShow(mediumHealth, mediumUseTime, ChatColor.BLUE + "中型回血包");
    }

    //大型回血包(医疗箱)
    protected static ItemStack getLargeHealthPack() {
        return HealthPackShow(largeHealth, largeUseTime, ChatColor.GOLD + "大型回血包");
    }

    @EventHandler
    public void onPlayerUseHealthPack(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        this.player = player;
        Action playerAction = event.getAction();
        ItemStack playerHandItem = player.getInventory().getItemInMainHand();
        if (playerHandItem.getType().equals(Material.BOOK)) {
            switch (playerAction) {
                case RIGHT_CLICK_BLOCK:
                case RIGHT_CLICK_AIR:
                    if (!rightClickStartTimes.containsKey(player)) {
                        rightClickStartTimes.put(player, System.currentTimeMillis());
                        try {
                            moreItems.startProgressBar(player, useHealthPack(playerHandItem), playerHandItem);
                        } catch (IllegalArgumentException e) {
                            return;
                        }
                    }
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                case PHYSICAL:
                    rightClickStartTimes.remove(player);
                    break;
            }
        } else {
            rightClickStartTimes.remove(player);
        }
    }

    protected Runnable useHealthPack(ItemStack healthPack) {
        Integer health;
        if (isSameItem(healthPack, getSmallHealthPack())) {
            health = smallHealth;
        } else if (isSameItem(healthPack, getMediumHealthPack())) {
            health = mediumHealth;
        } else if (isSameItem(healthPack, getLargeHealthPack())) {
            health = largeHealth;
        } else {
            // 使用的物品不是定义的血量包抛出异常
            throw new IllegalArgumentException("Invalid health pack");
        }
        return () -> {
            double playerNowHealth = player.getHealth();
            // 获取玩家最大生命值
            AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert playerMaxHealth != null;
            double playerMaxHealthValue = playerMaxHealth.getValue();
            double computeValue = playerNowHealth + health;
            player.setHealth(Math.min(computeValue, playerMaxHealthValue));
        };
    }

    private boolean isSameItem(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) return false;
        if (!item1.getType().equals(item2.getType())) return false;
        if (!item1.hasItemMeta() || !item2.hasItemMeta()) return false;
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        assert meta1 != null;
        assert meta2 != null;
        return meta1.getDisplayName().equals(meta2.getDisplayName()) && Objects.equals(meta1.getLore(), meta2.getLore());
    }
}
