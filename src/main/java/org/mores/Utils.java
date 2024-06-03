package org.mores;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.regex.Pattern;

public class Utils {

    /**
     * 给玩家发送标记的actionbar
     *
     * @param playerList 被标记的玩家列表
     * @param sender     使用标记的玩家
     */
    protected void SendActionBar(List<Player> playerList, Player sender) {
        ComponentBuilder builder=new ComponentBuilder();
        //被标记提示
        TextComponent playerActionbarText = new TextComponent(ChatColor.DARK_RED + "你已被标记！");
        playerActionbarText.setItalic(true);
        playerActionbarText.setUnderlined(true);
        TextComponent actionbarText=new TextComponent(ChatColor.DARK_GREEN+"已标记: ");
        actionbarText.setItalic(true);
        builder.append(actionbarText);
        for (Player player1 : playerList) {
            String playerName = player1.getName();
            TextComponent playerNameList=new TextComponent(playerName);
            playerNameList.setColor(ChatColor.RED);
            playerNameList.setBold(true);
            playerNameList.addExtra("|");
            playerNameList.setColor(ChatColor.GOLD);
            builder.append(playerNameList);
            player1.spigot().sendMessage(ChatMessageType.ACTION_BAR, playerActionbarText);
        }
        //标记提示
        BaseComponent[] text= builder.create();
        sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, text);
    }

    /**
     * 给玩家发光效果
     *
     * @param player 给予效果的玩家
     */
    protected void effectLightingPlayer(Player player) {
        //给予玩家10秒的发光效果
        PotionEffect glowEffect = new PotionEffect(PotionEffectType.GLOWING, 200, 1, true);
        player.addPotionEffect(glowEffect);
    }

    /**
     * 获取玩家所在的队伍
     *
     * @param player 需要获取队伍的玩家
     * @return 玩家所在的队伍
     */
    protected Team getPlayerTeam(Player player) {
        String playerName = player.getName();
        Scoreboard scoreboard = player.getScoreboard();
        return scoreboard.getEntryTeam(playerName);
    }

    /**
     * 中文字符检查
     *
     * @param str 字符串
     * @return 是否包含中文字符
     */
    protected boolean containsChinese(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        return pattern.matcher(str).find();
    }

    /**
     * 构建进度条
     *
     * @param current   目前的进度
     * @param max       最大进度
     * @param totalBars 进度条的总长度
     * @param itemStack 玩家手上的物品
     * @return 进度条文本
     */
    protected String setProgressBar(int current, int max, int totalBars, ItemStack itemStack) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        ItemMeta itemMeta = itemStack.getItemMeta();
        String itemName = null;
        if (itemMeta != null) {
            itemName = itemMeta.getDisplayName();
        }
        StringBuilder stringBuilder = new StringBuilder();
        String diyText = "正在使用:";
        stringBuilder.append(ChatColor.DARK_PURPLE);
        stringBuilder.append(diyText);
        stringBuilder.append(ChatColor.GOLD);
        stringBuilder.append(itemName);
        stringBuilder.append(ChatColor.DARK_GREEN);
        for (int i = 0; i < progressBars; i++) {
            stringBuilder.append("■");
        }
        stringBuilder.append(ChatColor.WHITE);
        for (int i = progressBars; i < totalBars; i++) {
            stringBuilder.append("□");
        }
        return stringBuilder.toString();
    }



}
