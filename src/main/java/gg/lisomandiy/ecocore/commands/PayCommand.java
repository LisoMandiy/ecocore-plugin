package gg.lisomandiy.ecocore.commands;

import gg.lisomandiy.ecocore.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public PayCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Эту команду могут использовать только игроки.");
            return true;
        }

        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Использование: /pay <игрок> <сумма>");
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден: " + args[0]);
            return true;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            sender.sendMessage(ChatColor.RED + "Вы не можете перевести деньги самому себе.");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Неверная сумма: " + args[1]);
            return true;
        }

        if (amount <= 0) {
            sender.sendMessage(ChatColor.RED + "Сумма должна быть положительной.");
            return true;
        }

        if (!economyManager.withdraw(player.getUniqueId(), amount)) {
            sender.sendMessage(ChatColor.RED + "У вас недостаточно денег.");
            return true;
        }

        economyManager.deposit(target.getUniqueId(), amount);

        player.sendMessage(ChatColor.GREEN + "Вы перевели " + target.getName() + " " + amount);
        target.sendMessage(ChatColor.GREEN + player.getName() + " перевёл вам " + amount);
        return true;
    }
}
