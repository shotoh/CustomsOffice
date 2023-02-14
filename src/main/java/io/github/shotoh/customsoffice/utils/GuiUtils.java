package io.github.shotoh.customsoffice.utils;

import io.github.shotoh.customsoffice.CustomsOffice;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GuiUtils {
    public static ItemStack getGuiGlass(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, "gui.glass", "", null, Material.GRAY_STAINED_GLASS_PANE);
    }

    public static ItemStack getGuiClose(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, "gui.close", "<red>Close", null, Material.BARRIER);
    }

    public static ItemStack getGuiError(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, "gui.error", "<red>Error", null, Material.BARRIER);
    }

    public static ItemStack getPreviousArrow(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, "gui.previous", "Previous", null, Material.ARROW);
    }

    public static ItemStack getNextArrow(CustomsOffice plugin) {
        return ItemUtils.createMenuItem(plugin, "gui.next", "Next", null, Material.ARROW);
    }

    public static boolean notBorder(int slot) {
        return slot % 9 > 0 && slot % 9 < 8 && slot > 8 && slot < 45;
    }

    public static int convertSlotToIndex(int slot) {
        return switch (slot) {
            case 10, 11, 12, 13, 14, 15, 16 -> slot - 10;
            case 19, 20, 21, 22, 23, 24, 25 -> slot - 12;
            case 28, 29, 30, 31, 32, 33, 34 -> slot - 14;
            case 37, 38, 39, 40, 41, 42, 43 -> slot - 16;
            default -> -1;
        };
    }
}
