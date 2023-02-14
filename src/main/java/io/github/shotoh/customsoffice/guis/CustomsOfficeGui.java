package io.github.shotoh.customsoffice.guis;

import io.github.shotoh.customsoffice.CustomsOffice;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class CustomsOfficeGui implements InventoryHolder {
    protected final CustomsOffice plugin;
    protected final Player player;

    protected CustomsOfficeGui(CustomsOffice plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public abstract @NotNull
    Inventory getInventory();

    public void onInventoryClickEvent(InventoryClickEvent event) {
        //
    }

    public void onInventoryDragEvent(InventoryDragEvent event) {
        //
    }

    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        //
    }
}
