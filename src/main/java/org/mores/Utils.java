package org.mores;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Utils {
    protected void SendActionBar(Player player, Player sender) {
        String playerName = player.getName();
        TextComponent actionbarText = new TextComponent(ChatColor.DARK_GREEN + "已标记:" + ChatColor.DARK_RED + playerName);
        //被标记提示
        TextComponent playerActionbarText = new TextComponent(ChatColor.DARK_RED + "你已被标记！");
        actionbarText.setItalic(true);
        playerActionbarText.setItalic(true);
        playerActionbarText.setUnderlined(true);
        sender.spigot().sendMessage(ChatMessageType.ACTION_BAR, actionbarText);
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, playerActionbarText);
    }

    protected void effectLightingPlayer(Player player) {
        //给予玩家10秒的发光效果
        PotionEffect glowEffect = new PotionEffect(PotionEffectType.GLOWING, 200, 1,false);
        player.addPotionEffect(glowEffect);
    }

    protected Team getPlayerTeam(Player player) {
        String playerName = player.getName();
        Scoreboard scoreboard = player.getScoreboard();
        return scoreboard.getEntryTeam(playerName);
    }
}
