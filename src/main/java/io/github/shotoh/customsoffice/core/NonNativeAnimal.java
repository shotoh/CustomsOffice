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

    public NonNativeAnimal(EntityType type, int maxAmount) {
        this.type = type;
        this.maxAmount = maxAmount;
        this.remainingQuantity = maxAmount;
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

    public ItemStack getSpawnEgg(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, null, StringUtils.capitalize(type.toString().toLowerCase(Locale.ROOT)),
            null, Material.valueOf(type + "_SPAWN_EGG"));
    }
}
