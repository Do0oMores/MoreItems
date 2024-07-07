package org.mores.HealthPack;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.mores.moreItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HealthPack implements Listener {
    private final moreItems plugin;
    private static Integer HealthPackRestoreValue;
    private static Integer HealthPckTick;
    private static Integer HealthChestRestoreValue;
    private static Integer HealthChestTick;

    public HealthPack(moreItems plugin) {
        this.plugin = plugin;

        // 获取配置值
        FileConfiguration config = plugin.getConfig();
        HealthPackRestoreValue = config.getInt("回血包.回血量");
        HealthChestRestoreValue = config.getInt("回血箱.回血量");
        HealthPckTick = config.getInt("回血包.回血间隔");
        HealthChestTick = config.getInt("回血箱.回血间隔");

        // 注册事件监听器
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //构建回血包物品
    private static ItemStack HealthItemConstruct(String itemName) {
        ItemStack healthPack = new ItemStack(Material.BOOK);
        ItemMeta healthPackMeta = healthPack.getItemMeta();
        assert healthPackMeta != null;
        healthPackMeta.setDisplayName(itemName);
        List<String> healthPackLore = new ArrayList<>();
        healthPackLore.add(ChatColor.DARK_PURPLE + "右键使用");
        healthPackMeta.setLore(healthPackLore);
        healthPack.setItemMeta(healthPackMeta);
        return healthPack;
    }

    //回血包
    protected static ItemStack getHealthPack() {
        return HealthItemConstruct(ChatColor.GREEN + "回血包");
    }

    //回血箱
    protected static ItemStack getHealthChest() {
        return HealthItemConstruct(ChatColor.BLUE + "回血箱");
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
