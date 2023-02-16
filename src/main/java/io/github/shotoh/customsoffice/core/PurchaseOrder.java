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

    public PurchaseOrder(UUID playerUUID, EntityType type, int cost) {
        this.playerUUID = playerUUID;
        this.type = type;
        this.cost = cost;
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

    public ItemStack getOrderDetails(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, null, "Order: " + type, new String[] {
            "<gold>Cost: " + cost,
            "<red>Click to remove order"
        }, Material.PAPER);
    }
}
