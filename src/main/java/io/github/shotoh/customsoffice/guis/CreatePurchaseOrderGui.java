package io.github.shotoh.customsoffice.guis;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.GuiUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class CreatePurchaseOrderGui extends CustomsOfficeGui {
    private int maxPages;
    private int page;

    public CreatePurchaseOrderGui(CustomsOffice plugin, Player player) {
        super(plugin, player);
        this.maxPages = plugin.getNonNativeAnimals().size() / 28 + 1;
        this.page = 0;
    }

    public CreatePurchaseOrderGui(CustomsOffice plugin, Player player, int page) {
        super(plugin, player);
        this.maxPages = plugin.getNonNativeAnimals().size() / 28 + 1;
        if (page >= 0 && page < maxPages) {
            this.page = page;
        } else {
            this.page = 0;
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        MiniMessage mm = plugin.getMiniMessage();
        Inventory inv = Bukkit.createInventory(this, 54, mm.deserialize("Create Purchase Order"));
        for (int i = 0; i < 54; i++) {
            if (GuiUtils.notBorder(i)) {
                int index = GuiUtils.convertSlotToIndex(i);
                if (index + (page * 28) < plugin.getNonNativeAnimals().size()) {
                    // TODO display price on spawn egg
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
        if (event.getInventory().getHolder() instanceof CreatePurchaseOrderGui) {
            event.setCancelled(true);
            Inventory inv = event.getClickedInventory();
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            if (inv != player.getInventory()) {
                if (GuiUtils.notBorder(slot)) {
                    // TODO open confirm order gui with details, cancel if not enough money?
                } else if (slot == 48) {
                    if (maxPages > 1 && page > 0) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.openInventory(new CreatePurchaseOrderGui(plugin, player, page - 1).getInventory());
                            }
                        }.runTaskLater(plugin, 1);
                    }
                } else if (slot == 49) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                        }
                    }.runTaskLater(plugin, 1);
                } else if (slot == 50) {
                    if (maxPages > 1 && page < maxPages - 1) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                player.openInventory(new CreatePurchaseOrderGui(plugin, player, page + 1).getInventory());
                            }
                        }.runTaskLater(plugin, 1);
                    }
                }
            }
        }
    }
}
