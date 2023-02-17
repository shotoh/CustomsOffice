package io.github.shotoh.customsoffice.core;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.ItemUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public class NonNativeAnimal {
    private EntityType type;
    private int maxAmount;
    private int remainingQuantity;
    private double multiplier;
    private String regionID;

    public NonNativeAnimal(EntityType type, int maxAmount, int remainingQuantity, double multiplier) {
        this.type = type;
        this.maxAmount = maxAmount;
        this.remainingQuantity = remainingQuantity;
        this.multiplier = multiplier;
        this.regionID = null;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public String getRegionID() {
        return regionID;
    }

    public void setRegionID(String regionID) {
        this.regionID = regionID;
    }

    public ItemStack getSpawnEgg(CustomsOffice plugin) {
        String[] lore;
        if (remainingQuantity > 0) {
            lore = new String[] {
                remainingQuantity + " left in stock",
                "<gold>Cost: " + getCost()
            };
        } else {
            lore = new String[] {
                "<red>Out of stock"
            };
        }
        return ItemUtils.createMenuItem(plugin, null, StringUtils.capitalize(type.toString().toLowerCase(Locale.ROOT)), lore, Material.valueOf(type + "_SPAWN_EGG"));
    }

    public int getCost() {
        return (int) (multiplier * maxAmount / remainingQuantity);
    }
}
