package io.github.shotoh.customsoffice.core;

import io.github.shotoh.customsoffice.CustomsOffice;
import org.bukkit.NamespacedKey;

public class CustomsOfficeKeys {
    private final CustomsOffice plugin;

    private final NamespacedKey idKey;

    public CustomsOfficeKeys(CustomsOffice plugin) {
        this.plugin = plugin;
        this.idKey = new NamespacedKey(plugin, "id");
    }

    public NamespacedKey getIdKey() {
        return idKey;
    }
}
