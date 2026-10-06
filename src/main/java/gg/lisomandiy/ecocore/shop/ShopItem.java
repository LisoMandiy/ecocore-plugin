package gg.lisomandiy.ecocore.shop;

import org.bukkit.Material;

public class ShopItem {

    private final Material material;
    private final double price;
    private final int slot;

    public ShopItem(Material material, double price, int slot) {
        this.material = material;
        this.price = price;
        this.slot = slot;
    }

    public Material getMaterial() {
        return material;
    }

    public double getPrice() {
        return price;
    }

    public int getSlot() {
        return slot;
    }
}
