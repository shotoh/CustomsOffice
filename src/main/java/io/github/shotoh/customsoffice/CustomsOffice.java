package io.github.shotoh.customsoffice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.shotoh.customsoffice.core.CustomsOfficeCommand;
import io.github.shotoh.customsoffice.core.CustomsOfficeData;
import io.github.shotoh.customsoffice.core.CustomsOfficeKeys;
import io.github.shotoh.customsoffice.listeners.InventoryListener;
import io.github.shotoh.customsoffice.listeners.PlayerListener;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomsOffice extends JavaPlugin {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private CustomsOfficeKeys customsOfficeKeys = new CustomsOfficeKeys(this);
    private CustomsOfficeData customsOfficeData = new CustomsOfficeData(this);

    @Override
    public void onEnable() {
        this.getCommand("customs").setExecutor(new CustomsOfficeCommand(this));
        this.saveDefaultConfig();
        customsOfficeData.load();
        registerEvents(new InventoryListener(this));
        registerEvents(new PlayerListener(this));
    }

    @Override
    public void onDisable() {
        customsOfficeData.save();
    }

    public Gson getGson() {
        return gson;
    }

    public MiniMessage getMiniMessage() {
        return miniMessage;
    }

    public CustomsOfficeKeys getCustomsOfficeKeys() {
        return customsOfficeKeys;
    }

    public CustomsOfficeData getCustomsOfficeData() {
        return customsOfficeData;
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }
}
