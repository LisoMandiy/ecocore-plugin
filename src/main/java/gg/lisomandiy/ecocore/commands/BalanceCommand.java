package gg.lisomandiy.ecocore.commands;

import gg.lisomandiy.ecocore.economy.EconomyManager;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public BalanceCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Использование: /balance <игрок>");
                return true;
            }

            Player player = (Player) sender;
            double balance = economyManager.getBalance(player.getUniqueId());
            sender.sendMessage(ChatColor.GOLD + "Ваш баланс: " + balance);
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        double balance = economyManager.getBalance(target.getUniqueId());
        sender.sendMessage(ChatColor.GOLD + "Баланс " + target.getName() + ": " + balance);
        return true;
    }
}
