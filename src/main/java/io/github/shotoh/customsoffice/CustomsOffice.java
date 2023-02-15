package io.github.shotoh.customsoffice;

import io.github.shotoh.customsoffice.core.CustomsOfficeKeys;
import io.github.shotoh.customsoffice.core.NonNativeAnimal;
import io.github.shotoh.customsoffice.core.PurchaseOrder;
import io.github.shotoh.customsoffice.listeners.InventoryListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CustomsOffice extends JavaPlugin {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private CustomsOfficeKeys customsOfficeKeys = new CustomsOfficeKeys(this);
    private ArrayList<NonNativeAnimal> nonNativeAnimals = new ArrayList<>();
    private ArrayList<PurchaseOrder> purchaseOrders = new ArrayList<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        // TODO fix later (data)
        for (String string : this.getConfig().getStringList("non-native-animals")) {
            if (!string.contains(":")) {
                this.getLogger().warning(string + " is not a valid non-native-animals entry!");
                continue;
            }
            String typeString = string.substring(0, string.indexOf(":"));
            String integerString = string.substring(string.indexOf(":"));
            try {
                EntityType type = EntityType.valueOf(typeString);
                int maxOfType = Integer.parseInt(integerString);
                if (nonNativeAnimals.stream().noneMatch(nonNativeAnimal -> nonNativeAnimal.getType() == type)) {
                    nonNativeAnimals.add(new NonNativeAnimal(type, maxOfType, 20));
                } else {
                    this.getLogger().warning(type + " has already been initialized!");
                }
            } catch (NumberFormatException e) {
                this.getLogger().warning(integerString + " is not a valid integer!");
            } catch (IllegalArgumentException e) {
                this.getLogger().warning(typeString + " is not a valid entity type!");
            }
        }
        registerEvents(new InventoryListener(this));
    }

    @Override
    public void onDisable() {
        //
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public CustomsOfficeKeys getCustomsOfficeKeys() {
        return customsOfficeKeys;
    }

    public ArrayList<NonNativeAnimal> getNonNativeAnimals() {
        return nonNativeAnimals;
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
