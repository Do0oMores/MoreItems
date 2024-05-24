package org.mores;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Utils {
    protected void SendActionBar(Player player,Player sender){
        String playerName= player.getName();
        TextComponent actionbarText=new TextComponent(ChatColor.DARK_GREEN+"已标记:"+ ChatColor.DARK_RED+playerName);
        actionbarText.setItalic(true);
        sender.spigot().sendMessage(ChatMessageType.ACTION_BAR,actionbarText);
    }

    protected void effectLightingPlayer(Player player){
        //给予玩家10秒的发光效果
        PotionEffect glowEffect =new PotionEffect(PotionEffectType.GLOWING,200,1);
        player.addPotionEffect(glowEffect);
    }
}
