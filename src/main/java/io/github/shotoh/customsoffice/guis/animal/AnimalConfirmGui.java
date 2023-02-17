package io.github.shotoh.customsoffice.guis.animal;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.core.NonNativeAnimal;
import io.github.shotoh.customsoffice.core.PurchaseOrder;
import io.github.shotoh.customsoffice.guis.CustomsOfficeGui;
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

public class AnimalConfirmGui extends CustomsOfficeGui {
    private NonNativeAnimal nonNativeAnimal;

    public AnimalConfirmGui(CustomsOffice plugin, Player player, NonNativeAnimal nonNativeAnimal) {
        super(plugin, player);
        this.nonNativeAnimal = nonNativeAnimal;
    }

    @Override
    public @NotNull Inventory getInventory() {
        MiniMessage mm = plugin.getMiniMessage();
        Inventory inv = Bukkit.createInventory(this, 54, mm.deserialize("Confirm Purchase Order"));
        for (int i = 0; i < 54; i ++) {
            if (i == 13) {
                inv.setItem(i, ItemUtils.createMenuItem(plugin, null, "Purchase Information", new String[] {
                    "Type: " + nonNativeAnimal.getType().toString(),
                    nonNativeAnimal.getRemainingQuantity() + " left in stock",
                    "<gold>Cost: " + nonNativeAnimal.getCost()
                }, Material.MAP));
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
        if (event.getInventory().getHolder() instanceof AnimalConfirmGui) {
            event.setCancelled(true);
            Inventory inv = event.getClickedInventory();
            Player player = (Player) event.getWhoClicked();
            int slot = event.getSlot();
            if (inv != player.getInventory()) {
                if (slot == 29) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            if (nonNativeAnimal.getRemainingQuantity() > 0) {
                                plugin.getCustomsOfficeData().getPurchaseOrders().add(new PurchaseOrder(player.getUniqueId(), nonNativeAnimal.getType(), nonNativeAnimal.getCost()));
                                nonNativeAnimal.setRemainingQuantity(nonNativeAnimal.getRemainingQuantity() - 1);
                                Utils.playSound(player, "entity.player.levelup", 1f, 1f);
                            } else {
                                player.sendMessage(plugin.getMiniMessage().deserialize("<red>An error has occurred and the transaction could not be made!"));
                            }
                        }
                    }.runTaskLater(plugin, 1);
                } else if (slot == 33) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.closeInventory();
                            Utils.playSound(player, "entity.villager.no", 1f, 1f);
                        }
                    }.runTaskLater(plugin, 1);
                }
            }
        }
    }
}
