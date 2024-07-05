package org.mores;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoreItemsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (args.length == 1) {
                String type = args[0].toLowerCase();
                ItemStack healthPack;
                switch (type) {
                    case "small":
                        healthPack = getSmallHealthPack();
                        break;
                    case "medium":
                        healthPack = getMediumHealthPack();
                        break;
                    case "large":
                        healthPack = getLargeHealthPack();
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "错误的回血包类型. Use: small, medium, or large.");
                        return false;
                }

                if (healthPack != null) {
                    player.getInventory().addItem(healthPack);
                    player.sendMessage(ChatColor.GREEN + "你得到了" + type + " 回血包!");
                }
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /gethealthpack <small|medium|large>");
            }
        } else {
            commandSender.sendMessage("This command can only be run by a player.");
        }
        return false;
    }

    private ItemStack getSmallHealthPack() {
        return healthPack.getSmallHealthPack();
    }

    private ItemStack getMediumHealthPack() {
        return healthPack.getMediumHealthPack();
    }

    private ItemStack getLargeHealthPack() {
        return healthPack.getLargeHealthPack();
    }
}
