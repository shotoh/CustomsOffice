package io.github.shotoh.customsoffice.guis;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.GuiUtils;
import io.github.shotoh.customsoffice.utils.ItemUtils;
import io.github.shotoh.customsoffice.utils.Utils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class NPCGui extends CustomsOfficeGui {
    public NPCGui(CustomsOffice plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public @NotNull Inventory getInventory() {
        MiniMessage mm = plugin.getMiniMessage();
        Inventory inv = Bukkit.createInventory(this, 54, mm.deserialize("Customs Office"));
        for (int i = 0; i < 54; i++) {
            if (i == 20) {
                inv.setItem(i, ItemUtils.createMenuItem(plugin, null, "Create purchase order", new String[] {
                    "Create a purchase order to get",
                    "some non-native animals"
                }, Material.WRITABLE_BOOK));
            } else if (i == 24) {
                inv.setItem(i, ItemUtils.createMenuItem(plugin, null, "Manage your orders", new String[]{
                    "Manage your current purchase",
                    "orders"
                }, Material.HOPPER));
            } else if (i == 49) {
                inv.setItem(i, GuiUtils.getGuiClose(plugin));
            } else {
                inv.setItem(i, GuiUtils.getGuiGlass(plugin));
            }
        }
        return inv;
    }

    @Override
    public void onInventoryClickEvent(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof NPCGui) {
            event.setCancelled(true);
            Inventory inv = event.getClickedInventory();
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            if (inv != player.getInventory()) {
                if (slot == 20) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.openInventory(new CreatePurchaseOrderGui(plugin, player).getInventory());
                            Utils.playSound(player, "item.book.page_turn", 1f, 1f);
                        }
                    }.runTaskLater(plugin, 1);
                } else if (slot == 24) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.openInventory(new ManagePurchaseOrderGui(plugin, player).getInventory());
                            Utils.playSound(player, "item.spyglass.use", 1f, 2f);
                        }
                    }.runTaskLater(plugin, 1);
                } else if (slot == 49) {
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
