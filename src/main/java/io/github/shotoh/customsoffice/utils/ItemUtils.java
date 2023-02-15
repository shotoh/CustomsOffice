package io.github.shotoh.customsoffice.utils;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.core.CustomsOfficeKeys;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;

public class ItemUtils {
    public static ItemStack createMenuItem(CustomsOffice plugin, String id, String name, String[] lore, Material material) {
        MiniMessage mm = plugin.getMiniMessage();
        CustomsOfficeKeys keys = plugin.getCustomsOfficeKeys();

        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();

        if (id != null) {
            PersistentDataContainer con = im.getPersistentDataContainer();
            con.set(keys.getIdKey(), PersistentDataType.STRING, id);
        }

        im.displayName(mm.deserialize("<!i><green>" + name));
        if (lore != null) {
            im.lore(Arrays.stream(lore).map(s -> mm.deserialize("<!i><gray>" + s)).toList());
        }

        im.setUnbreakable(true);
        im.addItemFlags(
            ItemFlag.HIDE_UNBREAKABLE,
            ItemFlag.HIDE_ATTRIBUTES,
            ItemFlag.HIDE_DESTROYS,
            ItemFlag.HIDE_DYE,
            ItemFlag.HIDE_ENCHANTS,
            ItemFlag.HIDE_PLACED_ON,
            ItemFlag.HIDE_ITEM_SPECIFICS
        );

        is.setItemMeta(im);
        return is;
    }

    public static boolean isItem(CustomsOffice plugin, ItemStack is, String id) {
        if (is != null && is.getItemMeta() != null) {
            PersistentDataContainer con = is.getItemMeta().getPersistentDataContainer();
            NamespacedKey idKey = plugin.getCustomsOfficeKeys().getIdKey();
            if (con.has(idKey, PersistentDataType.STRING)) {
                String conKey = con.get(idKey, PersistentDataType.STRING);
                return conKey != null && conKey.equalsIgnoreCase(id);
            }
        }
        return false;
    }
}
