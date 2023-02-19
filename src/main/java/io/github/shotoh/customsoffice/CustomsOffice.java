package io.github.shotoh.customsoffice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.shotoh.customsoffice.core.*;
import io.github.shotoh.customsoffice.guis.CustomsOfficeGui;
import io.github.shotoh.customsoffice.listeners.CitizensListener;
import io.github.shotoh.customsoffice.listeners.InventoryListener;
import io.github.shotoh.customsoffice.listeners.PlayerListener;
import io.github.shotoh.customsoffice.utils.Utils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.HeightMap;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CustomsOffice extends JavaPlugin {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final MiniMessage miniMessage = MiniMessage.miniMessage();
    private CustomsOfficeKeys customsOfficeKeys = new CustomsOfficeKeys(this);
    private CustomsOfficeData customsOfficeData = new CustomsOfficeData(this);

    private BukkitTask task = null;

    @Override
    public void onEnable() {
        this.getCommand("customs").setExecutor(new CustomsOfficeCommand(this));
        this.getCommand("customs").setTabCompleter(new CustomsTabCompleter(this));

        this.saveDefaultConfig();

        registerEvents(new InventoryListener(this));
        registerEvents(new PlayerListener(this));
        registerEvents(new CitizensListener(this));

        customsOfficeData.load();
    }

    @Override
    public void onDisable() {
        customsOfficeData.save();
        this.saveConfig();
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

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    private void registerEvents(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    public void triggerAnimalEvent() {
        if (task != null) {
            task.cancel();
        }
        long timeBetweenEvent = this.getConfig().getLong("time-between-event");
        if (timeBetweenEvent == 0) {
            timeBetweenEvent = 432000000;
        } else {
            timeBetweenEvent *= 1000;
        }
        long newEventTime = System.currentTimeMillis() + timeBetweenEvent;
        this.getConfig().set("animal-event-time", newEventTime);
        World world = customsOfficeData.getAnimalNPC().getStoredLocation().getWorld();
        for (PurchaseOrder order : customsOfficeData.getPurchaseOrders()) {
            ProtectedRegion region = customsOfficeData.getRegionHashMap().get(order.getType());
            if (region != null) {
                Location loc = Utils.getRandomLocation(BukkitAdapter.adapt(world, region.getMinimumPoint()), BukkitAdapter.adapt(world, region.getMaximumPoint()));
                loc.setY(region.getMinimumPoint().getY());
                Entity entity = world.spawnEntity(loc, order.getType(), true);
                PersistentDataContainer con = entity.getPersistentDataContainer();
                con.set(customsOfficeKeys.getOwnerKey(), PersistentDataType.STRING, order.getPlayerUUID().toString());
            }
        }
        customsOfficeData.getPurchaseOrders().clear();
        Bukkit.broadcast(miniMessage.deserialize("<!i><aqua>The Customs Office has been restocked! Claim your purchase orders now!"));
        for (NonNativeAnimal animal : customsOfficeData.getNonNativeAnimals()) {
            animal.setRemainingQuantity(animal.getMaxAmount());
        }
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof CustomsOfficeGui) {
                player.closeInventory();
            }
        }
        long nextEventTimeTicks = (newEventTime - System.currentTimeMillis()) / 1000 * 20;
        task = new BukkitRunnable() {
            @Override
            public void run() {
                triggerAnimalEvent();
            }
        }.runTaskLater(this, nextEventTimeTicks);
    }
}
