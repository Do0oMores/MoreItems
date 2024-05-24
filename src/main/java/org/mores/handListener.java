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
import org.bukkit.scoreboard.Team;

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
        //获取使用者的队伍
        Team team = utils.getPlayerTeam(player);
        //判断玩家是否右键烟花火箭
        if (itemInHand.getType().equals(Material.FIREWORK_ROCKET) && playerAction.equals(Action.RIGHT_CLICK_BLOCK)) {
            // 玩家发射了烟花,检测发射烟花玩家20格内的玩家
            for (Entity entity : playerWorld.getNearbyEntities(playerLocation, 20, 20, 20)) {
                //防止标记到使用者
                if (entity.getType().equals(EntityType.PLAYER) && !entity.getName().equals(playerName)) {
                    nearPlayers.add((Player) entity);
                }
            }
        }
        //遍历附近的玩家
        if (!nearPlayers.isEmpty()) {
            for (Player player1 : nearPlayers) {
                //对玩家进行操作,排除队友
                Team playerTeam = utils.getPlayerTeam(player1);
                //增加空队伍判断
                //如果有队伍则排除队友
                if (team != null && playerTeam != null) {
                    if (!playerTeam.equals(team)) {
                        utils.effectLightingPlayer(player1);
                        utils.SendActionBar(player1, player);
                    } else return;
                    //没有队伍就全部标记
                } else {
                    utils.effectLightingPlayer(player1);
                    utils.SendActionBar(player1, player);
                }
            }
        }
    }
}
