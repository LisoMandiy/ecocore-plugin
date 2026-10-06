package gg.lisomandiy.ecocore.shop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShopGUI {

    public static final String TITLE = ChatColor.DARK_GREEN + "Магазин EcoCore";

    private final Map<Integer, ShopItem> itemsBySlot = new LinkedHashMap<>();

    public ShopGUI() {
        register(new ShopItem(Material.IRON_SWORD, 150, 10));
        register(new ShopItem(Material.DIAMOND_SWORD, 800, 11));
        register(new ShopItem(Material.BOW, 200, 12));
        register(new ShopItem(Material.ARROW, 5, 13));
        register(new ShopItem(Material.IRON_CHESTPLATE, 300, 14));
        register(new ShopItem(Material.DIAMOND_CHESTPLATE, 1000, 15));
        register(new ShopItem(Material.GOLDEN_APPLE, 120, 16));
        register(new ShopItem(Material.ENDER_PEARL, 90, 19));
        register(new ShopItem(Material.COOKED_BEEF, 10, 20));
        register(new ShopItem(Material.OAK_LOG, 8, 21));
    }

    private void register(ShopItem item) {
        itemsBySlot.put(item.getSlot(), item);
    }

    public ShopItem getItem(int slot) {
        return itemsBySlot.get(slot);
    }

    public Inventory build() {
        Inventory inventory = org.bukkit.Bukkit.createInventory(null, 27, TITLE);

        for (ShopItem shopItem : itemsBySlot.values()) {
            ItemStack stack = new ItemStack(shopItem.getMaterial());
            ItemMeta meta = stack.getItemMeta();

            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + formatName(shopItem.getMaterial()));
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Цена: " + ChatColor.GOLD + shopItem.getPrice());
                lore.add(ChatColor.GRAY + "Нажмите, чтобы купить");
                meta.setLore(lore);
                stack.setItemMeta(meta);
            }

            inventory.setItem(shopItem.getSlot(), stack);
        }

        return inventory;
    }

    private String formatName(Material material) {
        String[] parts = material.name().split("_");
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part.charAt(0)).append(part.substring(1).toLowerCase()).append(" ");
        }
        return builder.toString().trim();
    }
}
