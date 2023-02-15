package io.github.shotoh.customsoffice.guis;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.GuiUtils;
import io.github.shotoh.customsoffice.utils.ItemUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class ConfirmPurchaseOrderGui extends CustomsOfficeGui {
    public ConfirmPurchaseOrderGui(CustomsOffice plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public @NotNull Inventory getInventory() {
        MiniMessage mm = plugin.getMiniMessage();
        Inventory inv = Bukkit.createInventory(this, 54, mm.deserialize("Confirm Purchase Order"));
        for (int i = 0; i < 54; i ++) {
            if (i == 13) {
                // TODO set map with info and confirm thing
            } else if (i == 29) {
                inv.setItem(i, ItemUtils.createMenuItem(plugin, null, "Confirm", null, Material.EMERALD));
            } else if (i == 33) {
                inv.setItem(i, ItemUtils.createMenuItem(plugin, null, "<red>Deny", null, Material.REDSTONE));
            } else {
                inv.setItem(i, GuiUtils.getGuiGlass(plugin));
            }
        }
        return inv;
    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof ConfirmPurchaseOrderGui) {
            event.setCancelled(true);
            Inventory inv = event.getClickedInventory();
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            if (inv != player.getInventory()) {
                if (slot == 29) {
                    // TODO create purchase order
                } else if (slot == 33) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
        }
    }
}
