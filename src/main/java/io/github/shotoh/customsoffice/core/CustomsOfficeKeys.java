package io.github.shotoh.customsoffice.core;

import io.github.shotoh.customsoffice.CustomsOffice;
import org.bukkit.NamespacedKey;

public class CustomsOfficeKeys {
    private final CustomsOffice plugin;

    private final NamespacedKey idKey;
    private final NamespacedKey ownerKey;

    public CustomsOfficeKeys(CustomsOffice plugin) {
        this.plugin = plugin;
        this.idKey = new NamespacedKey(plugin, "id");
        this.ownerKey = new NamespacedKey(plugin, "owner");
    }

    public NamespacedKey getIdKey() {
        return idKey;
    }

    public NamespacedKey getOwnerKey() {
        return ownerKey;
    }
}
