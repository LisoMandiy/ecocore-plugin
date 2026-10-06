package gg.lisomandiy.ecocore;

import gg.lisomandiy.ecocore.commands.BalTopCommand;
import gg.lisomandiy.ecocore.commands.BalanceCommand;
import gg.lisomandiy.ecocore.commands.PayCommand;
import gg.lisomandiy.ecocore.commands.ShopCommand;
import gg.lisomandiy.ecocore.economy.EconomyManager;
import gg.lisomandiy.ecocore.shop.ShopGUI;
import gg.lisomandiy.ecocore.shop.ShopListener;
import org.bukkit.plugin.java.JavaPlugin;

public class EcoCorePlugin extends JavaPlugin {

    private EconomyManager economyManager;

    @Override
    public void onEnable() {
        economyManager = new EconomyManager(this, 500);
        ShopGUI shopGUI = new ShopGUI();

        getCommand("balance").setExecutor(new BalanceCommand(economyManager));
        getCommand("pay").setExecutor(new PayCommand(economyManager));
        getCommand("baltop").setExecutor(new BalTopCommand(economyManager));
        getCommand("shop").setExecutor(new ShopCommand(shopGUI));

        getServer().getPluginManager().registerEvents(new ShopListener(economyManager, shopGUI), this);
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }
}
