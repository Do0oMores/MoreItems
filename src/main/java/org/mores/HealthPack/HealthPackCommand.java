package org.mores.HealthPack;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HealthPackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.isOp()&&args.length == 1) {
                String type = args[0].toLowerCase();
                ItemStack healthPack;
                switch (type) {
                    case "回血包":
                        healthPack = getHealthPack();
                        break;
                    case "回血箱":
                        healthPack = getHealthChest();
                        break;
                    default:
                        player.sendMessage(ChatColor.RED + "错误的回血包类型. Use: 回血包，回血箱");
                        return false;
                }

                player.getInventory().addItem(healthPack);
                player.sendMessage(ChatColor.GREEN + "你得到了" + type );
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /gethealthpack <回血包|回血箱>");
            }
        } else {
            commandSender.sendMessage("This command can only be run by a player.");
        }
        return false;
    }

    private ItemStack getHealthPack() {
        return HealthPack.getHealthPack();
    }

    private ItemStack getHealthChest() {
        return HealthPack.getHealthChest();
    }
}
