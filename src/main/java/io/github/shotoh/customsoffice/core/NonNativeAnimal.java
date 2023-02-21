package io.github.shotoh.customsoffice.core;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class NonNativeAnimal {
    private EntityType type;
    private int maxAmount;
    private int remainingQuantity;
    private double multiplier;
    private String regionID;
    private String currencyName;

    public NonNativeAnimal(EntityType type, int maxAmount, int remainingQuantity, double multiplier) {
        this.type = type;
        this.maxAmount = maxAmount;
        this.remainingQuantity = remainingQuantity;
        this.multiplier = multiplier;
        this.regionID = null;
        this.currencyName = null;
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

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public ItemStack getSpawnEgg(CustomsOffice plugin) {
        String[] lore;
        if (remainingQuantity > 0) {
            lore = new String[] {
                remainingQuantity + " left in stock",
                "<gold>Cost: " + getCost() + " " + currencyName
            };
        } else {
            lore = new String[] {
                "<red>Out of stock"
            };
        }
        Material material;
        if (type == EntityType.MUSHROOM_COW) {
            material = Material.MOOSHROOM_SPAWN_EGG;
        } else {
            material = Material.valueOf(type + "_SPAWN_EGG");
        }
        return ItemUtils.createMenuItem(plugin, null, type.toString(), lore, material);
    }

    public int getCost() {
        return (int) (multiplier * maxAmount / remainingQuantity);
    }
}
