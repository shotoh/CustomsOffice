package io.github.shotoh.customsoffice.guis;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.GuiUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class CreatePurchaseOrderGui extends CustomsOfficeGui {
    private static HashMap<UUID, Integer> pages = new HashMap<>();

    public CreatePurchaseOrderGui(CustomsOffice plugin, Player player) {
        super(plugin, player);
        pages.putIfAbsent(player.getUniqueId(), 0);
    }

    @Override
    public @NotNull Inventory getInventory() {
        MiniMessage mm = plugin.getMiniMessage();
        Inventory inv = Bukkit.createInventory(this, 54, mm.deserialize("Create Purchase Order"));
        int page = pages.get(player.getUniqueId());
        int maxPages = plugin.getNonNativeAnimals().size() / 28 + 1;
        for (int i = 0; i < 54; i++) {
            if (GuiUtils.notBorder(i)) {
                int index = GuiUtils.convertSlotToIndex(i);
                if (index + (page * 28) < plugin.getNonNativeAnimals().size()) {
                    inv.setItem(i, plugin.getNonNativeAnimals().get(index).getSpawnEgg(plugin));
                }
            } else if (i == 48) {
                if (maxPages > 1 && page > 0) {
                    inv.setItem(i, GuiUtils.getPreviousArrow(plugin));
                } else {
                    inv.setItem(i, GuiUtils.getGuiGlass(plugin));
                }
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose(plugin));
            } else if (i == 50) {
                if (maxPages > 1 && page < maxPages - 1) {
                    inv.setItem(i, GuiUtils.getNextArrow(plugin));
                } else {
                    inv.setItem(i, GuiUtils.getGuiGlass(plugin));
                }
            }
        }
        return inv;
    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent event) {
        //
    }

    @Override
    public void onInventoryCloseEvent(InventoryCloseEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getPlayer();
        if (inv.getHolder() instanceof CreatePurchaseOrderGui) {
            if (event.getReason() != InventoryCloseEvent.Reason.OPEN_NEW) {
                pages.put(player.getUniqueId(), 0);
            }
        }
    }
}
