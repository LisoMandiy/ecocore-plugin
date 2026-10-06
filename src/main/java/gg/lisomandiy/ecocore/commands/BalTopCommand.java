package gg.lisomandiy.ecocore.commands;

import gg.lisomandiy.ecocore.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class BalTopCommand implements CommandExecutor {

    private final EconomyManager economyManager;

    public BalTopCommand(EconomyManager economyManager) {
        this.economyManager = economyManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        List<Map.Entry<UUID, Double>> sorted = economyManager.getAllBalances().entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(5)
                .collect(Collectors.toList());

        sender.sendMessage(ChatColor.GOLD + "Самые богатые игроки:");

        int place = 1;
        for (Map.Entry<UUID, Double> entry : sorted) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(entry.getKey());
            String name = player.getName() != null ? player.getName() : entry.getKey().toString();
            sender.sendMessage(ChatColor.YELLOW + "" + place + ". " + name + " - " + entry.getValue());
            place++;
        }

        return true;
    }
}
