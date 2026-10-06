package gg.lisomandiy.ecocore.economy;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {

    private final JavaPlugin plugin;
    private final File file;
    private final YamlConfiguration config;
    private final double startingBalance;
    private final Map<UUID, Double> cache = new LinkedHashMap<>();

    public EconomyManager(JavaPlugin plugin, double startingBalance) {
        this.plugin = plugin;
        this.startingBalance = startingBalance;
        this.file = new File(plugin.getDataFolder(), "economy.yml");

        if (!file.exists()) {
            plugin.getDataFolder().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().warning("Could not create economy.yml");
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            cache.put(UUID.fromString(key), config.getDouble(key));
        }
    }

    public double getBalance(UUID uuid) {
        return cache.computeIfAbsent(uuid, u -> startingBalance);
    }

    public void setBalance(UUID uuid, double amount) {
        cache.put(uuid, Math.max(0, amount));
        save();
    }

    public void deposit(UUID uuid, double amount) {
        setBalance(uuid, getBalance(uuid) + amount);
    }

    public boolean withdraw(UUID uuid, double amount) {
        double current = getBalance(uuid);
        if (current < amount) {
            return false;
        }
        setBalance(uuid, current - amount);
        return true;
    }

    public boolean has(UUID uuid, double amount) {
        return getBalance(uuid) >= amount;
    }

    public Map<UUID, Double> getAllBalances() {
        return cache;
    }

    private void save() {
        for (Map.Entry<UUID, Double> entry : cache.entrySet()) {
            config.set(entry.getKey().toString(), entry.getValue());
        }
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().warning("Could not save economy.yml");
        }
    }
}
