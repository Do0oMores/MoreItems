package org.mores;

import org.bukkit.ChatColor;
import org.bukkit.Material;
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
import java.util.concurrent.ConcurrentHashMap;

public class healthPack implements Listener {

    moreItems moreItems=new moreItems();

    protected final Map<Player, Long> rightClickStartTimes = new ConcurrentHashMap<>();

    //小型回血包(绷带)
    protected ItemStack getSmallHealthPack(){
        //定义回血包
        ItemStack healthPack=new ItemStack(Material.BOOK);
        ItemMeta healthPackMeta= healthPack.getItemMeta();
        assert healthPackMeta != null;
        healthPackMeta.setDisplayName(ChatColor.GREEN+"小型回血包");
        List<String> smallHealthPackLore=new ArrayList<>();
        smallHealthPackLore.add(ChatColor.DARK_PURPLE+"右键回复25点血量");
        smallHealthPackLore.add(ChatColor.COLOR_CHAR+"o"+ChatColor.RED+"使用时间：5秒");
        healthPackMeta.setLore(smallHealthPackLore);
        return healthPack;
    }

    //中型回血包(医疗包)
    protected ItemStack getMediumHealthPack(){
        ItemStack healthPack=new ItemStack(Material.BOOK);
        ItemMeta healthPackMeta= healthPack.getItemMeta();
        assert healthPackMeta != null;
        healthPackMeta.setDisplayName(ChatColor.DARK_BLUE+"中型回血包");
        List<String> mediumHealthPackLore=new ArrayList<>();
        mediumHealthPackLore.add(ChatColor.DARK_PURPLE+"右键回复50点血量");
        mediumHealthPackLore.add(ChatColor.COLOR_CHAR+"o"+ChatColor.RED+"使用时间：10秒");
        healthPackMeta.setLore(mediumHealthPackLore);
        return healthPack;
    }

    //大型回血包(医疗箱)
    protected ItemStack getLargeHealthPack(){
        ItemStack healthPack=new ItemStack(Material.BOOK);
        ItemMeta healthPackMeta= healthPack.getItemMeta();
        assert healthPackMeta != null;
        healthPackMeta.setDisplayName(ChatColor.GOLD+"大型回血包");
        List<String> largeHealthPackLore=new ArrayList<>();
        largeHealthPackLore.add(ChatColor.DARK_PURPLE+"右键回复75点血量");
        largeHealthPackLore.add(ChatColor.COLOR_CHAR+"o"+ChatColor.RED+"使用时间：15秒");
        healthPackMeta.setLore(largeHealthPackLore);
        return healthPack;
    }

    @EventHandler
    public void onPlayerUseHealthPack(PlayerInteractEvent event){
        Player player=event.getPlayer();
        Action playerAction=event.getAction();
        ItemStack playerHandItem=player.getInventory().getItemInMainHand();
        if (playerHandItem.getType().equals(Material.BOOK)){
            switch (playerAction){
                case RIGHT_CLICK_BLOCK:
                case RIGHT_CLICK_AIR:
                    if (!rightClickStartTimes.containsKey(player)){
                        //moreItems.checkHoldRightClick(player,5000);
                    }
                    break;
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                case PHYSICAL:
                    rightClickStartTimes.remove(player);
                    break;
            }
        }else {
            rightClickStartTimes.remove(player);
        }
    }
}
