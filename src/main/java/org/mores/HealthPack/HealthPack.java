package org.mores.HealthPack;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.mores.moreItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HealthPack implements Listener {
    Player PLAYER;

    public HealthPack(moreItems plugin) {
        // 注册事件监听器
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    // 构建回血包物品
    private static ItemStack HealthItemConstruct(String itemName) {
        ItemStack healthPack = new ItemStack(Material.BOOK);
        ItemMeta healthPackMeta = healthPack.getItemMeta();
        assert healthPackMeta != null;
        healthPackMeta.setDisplayName(itemName);
        List<String> healthPackLore = new ArrayList<>();
        healthPackLore.add(ChatColor.DARK_PURPLE + "右键使用");
        healthPackMeta.setLore(healthPackLore);
        healthPackMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
        healthPack.setItemMeta(healthPackMeta);
        return healthPack;
    }

    // 获取回血包
    protected static ItemStack getHealthPack() {
        return HealthItemConstruct(ChatColor.GREEN + "回血包");
    }

    // 获取回血箱
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

    @EventHandler
    protected void onPlayerUseHealthPack(PlayerInteractEvent event){
        Player player = event.getPlayer();
        this.PLAYER = player;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        // 判断是否为回血包
        if (isSameItem(item, getHealthPack())) {
            // 给予玩家生命恢复 6 级，持续 5 秒
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 5,true));
            player.sendMessage(ChatColor.GREEN + "已使用了回血包！");
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().setItemInMainHand(null);
            }
        }
        // 判断是否为回血箱
        else if (isSameItem(item, getHealthChest())) {
            // 给予玩家生命恢复 10 级，持续 5 秒
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 9,true));
            player.sendMessage(ChatColor.BLUE + "已使用回血箱！");
            if (item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                player.getInventory().setItemInMainHand(null);
            }
        }
    }
}
