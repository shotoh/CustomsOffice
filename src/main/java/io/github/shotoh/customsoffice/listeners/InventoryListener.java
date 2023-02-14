package io.github.shotoh.customsoffice.listeners;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.guis.CustomsOfficeGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryListener implements Listener {
    private final CustomsOffice plugin;

    public InventoryListener(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof CustomsOfficeGui customsOfficeGui) {
            customsOfficeGui.onInventoryClickEvent(event);
        }
    }

    @EventHandler
    public void onInventoryDragEvent(InventoryDragEvent event) {
        if (event.getInventory().getHolder() instanceof CustomsOfficeGui customsOfficeGui) {
            customsOfficeGui.onInventoryDragEvent(event);
        }
    }

    @EventHandler
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof CustomsOfficeGui customsOfficeGui) {
            customsOfficeGui.onInventoryCloseEvent(event);
        }
    }
}
