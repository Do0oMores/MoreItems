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

    Integer smallHealth=moreItems.config.getInt("SmallHealthPack.Health");
    Integer smallUseTime=moreItems.config.getInt("SmallHealthPack.UseTime");
    Integer mediumHealth=moreItems.config.getInt("MediumHealthPack.Health");
    Integer mediumUseTime=moreItems.config.getInt("MediumHealthPack.UseTime");
    Integer largeHealth=moreItems.config.getInt("LargeHealthPack.Health");
    Integer largeUseTime=moreItems.config.getInt("LargeHealthPack.UseTime");

    //构建回血包物品
    private ItemStack HealthPackShow(Integer health,Integer useTime,String itemName){
        ItemStack healthPack=new ItemStack(Material.BOOK);
        ItemMeta healthPackMeta=healthPack.getItemMeta();
        assert healthPackMeta!=null;
        healthPackMeta.setDisplayName(itemName);
        List<String> healthPackLore=new ArrayList<>();
        healthPackLore.add(ChatColor.DARK_PURPLE+"右键回复"+health+"点血量");
        healthPackLore.add(ChatColor.COLOR_CHAR+"o"+ChatColor.RED+"使用时间："+useTime+"秒");
        healthPackMeta.setLore(healthPackLore);
        return healthPack;
    }

    //小型回血包(绷带)
    protected ItemStack getSmallHealthPack(){
        return HealthPackShow(smallHealth,smallUseTime,ChatColor.GREEN+"小型回血包");
    }

    //中型回血包(医疗包)
    protected ItemStack getMediumHealthPack(){
        return HealthPackShow(mediumHealth,mediumUseTime,ChatColor.BLUE+"中型回血包");
    }

    //大型回血包(医疗箱)
    protected ItemStack getLargeHealthPack(){
        return HealthPackShow(largeHealth,largeUseTime,ChatColor.GOLD+"大型回血包");
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
