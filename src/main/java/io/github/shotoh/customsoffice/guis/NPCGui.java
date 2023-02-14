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
                inv.setItem(i, ItemUtils.createMenuItem(plugin, "npcgui.create", "Create purchase order", new String[] {
                    "Create a purchase order to get",
                    "some non-native animals"
                }, Material.WRITABLE_BOOK));
            } else if (i == 24) {
                inv.setItem(i, ItemUtils.createMenuItem(plugin, "npcgui.manage", "Manage your orders", new String[]{
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
        //
    }
}
