package io.github.shotoh.customsoffice.core;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.ItemUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;
import java.util.UUID;

public class PurchaseOrder {
    private UUID playerUUID;
    private EntityType type;
    private int cost;
    private String currencyName;

    public PurchaseOrder(UUID playerUUID, EntityType type, int cost, String currencyName) {
        this.playerUUID = playerUUID;
        this.type = type;
        this.cost = cost;
        this.currencyName = currencyName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public EntityType getType() {
        return type;
    }

    public void setType(EntityType type) {
        this.type = type;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public ItemStack getOrderDetails(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, null, "Order: " + type, new String[] {
            "<gold>Cost: " + cost + " " + currencyName,
            "<red>Click to remove order"
        }, Material.PAPER);
    }
}
