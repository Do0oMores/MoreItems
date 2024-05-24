package org.mores;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class handListener implements Listener {
    Utils utils = new Utils();

    @EventHandler
    public void onPlayerUseFirework(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Action playerAction = event.getAction();
        Location playerLocation = player.getLocation();
        World playerWorld = player.getWorld();
        List<Player> nearPlayers = new ArrayList<>();
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        //判断玩家是否右键烟花火箭
        if (itemInHand.getType().equals(Material.FIREWORK_ROCKET) && playerAction.equals(Action.RIGHT_CLICK_BLOCK)) {
            // 玩家发射了烟花,检测发射烟花玩家10格内的玩家
            for (Entity entity : playerWorld.getNearbyEntities(playerLocation, 10, 10, 10)) {
                if (entity.getType().equals(EntityType.PLAYER) && !entity.getName().equals(playerName)) {
                    nearPlayers.add((Player) entity);
                }
            }
            //遍历附近10格内的玩家
            if (!nearPlayers.isEmpty()) {
                for (Player player1 : nearPlayers) {
                    //对玩家进行操作
                    utils.effectLightingPlayer(player1);
                    utils.SendActionBar(player1, player);
                }
            }
        }

    }
}
