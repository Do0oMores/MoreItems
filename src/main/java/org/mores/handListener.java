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
        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        // 判断玩家是否右键烟花火箭
        if (itemInHand.getType().equals(Material.FIREWORK_ROCKET) && playerAction.equals(Action.RIGHT_CLICK_BLOCK)) {
            List<Player> nearPlayers = new ArrayList<>();
            List<Player> lightingPlayers = new ArrayList<>();

            // 获取使用者的队伍
            Team team = utils.getPlayerTeam(player);

            // 检测发射烟花玩家20格内的玩家
            for (Entity entity : playerWorld.getNearbyEntities(playerLocation, 20, 20, 20)) {
                if (entity.getType().equals(EntityType.PLAYER)) {
                    Player nearPlayer = (Player) entity;
                    String entityName = nearPlayer.getName();
                    boolean containsChinese = utils.containsChinese(entityName);

                    // 防止标记到使用者和NPC
                    if (!nearPlayer.getName().equals(playerName) && !containsChinese) {
                        nearPlayers.add(nearPlayer);
                    }
                }
            }

            // 遍历附近的玩家
            for (Player nearPlayer : nearPlayers) {
                Team playerTeam = utils.getPlayerTeam(nearPlayer);

                if ((team != null && playerTeam != null && !playerTeam.equals(team)) || team == null || playerTeam == null) {
                    lightingPlayers.add(nearPlayer);
                    utils.effectLightingPlayer(nearPlayer);
                }
            }

            // 发送提示信息
            if (!lightingPlayers.isEmpty()) {
                utils.SendActionBar(lightingPlayers, player);
            }
        }
    }

}
