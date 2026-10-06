package gg.lisomandiy.ecocore.shop;

import gg.lisomandiy.ecocore.economy.EconomyManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    private final EconomyManager economyManager;
    private final ShopGUI shopGUI;

    public ShopListener(EconomyManager economyManager, ShopGUI shopGUI) {
        this.economyManager = economyManager;
        this.shopGUI = shopGUI;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ShopGUI.TITLE)) {
            return;
        }

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ShopItem shopItem = shopGUI.getItem(event.getRawSlot());

        if (shopItem == null) {
            return;
        }

        if (!economyManager.withdraw(player.getUniqueId(), shopItem.getPrice())) {
            player.sendMessage(ChatColor.RED + "У вас недостаточно денег.");
            return;
        }

        player.getInventory().addItem(new ItemStack(shopItem.getMaterial()));
        player.sendMessage(ChatColor.GREEN + "Вы купили 1x " + shopItem.getMaterial().name()
                + " за " + shopItem.getPrice());
    }
}
