package io.github.shotoh.customsoffice.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.core.CustomsOfficeData;
import io.github.shotoh.customsoffice.core.NonNativeAnimal;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CitizensListener implements Listener {
    private CustomsOffice plugin;

    public CitizensListener(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCitizensEnableEvent(CitizensEnableEvent event) {
        CustomsOfficeData data = plugin.getCustomsOfficeData();
        int npcID = plugin.getConfig().getInt("npc-id");
        if (npcID != -1) {
            NPC npc = CitizensAPI.getNPCRegistry().getById(npcID);
            if (npc != null) {
                data.setAnimalNPC(npc);
                plugin.getLogger().info("NPC ID: " + npc.getId() + " has been registered");
                RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(npc.getStoredLocation().getWorld()));
                if (regionManager != null) {
                    for (NonNativeAnimal nonNativeAnimal : data.getNonNativeAnimals()) {
                        String regionID = nonNativeAnimal.getRegionID();
                        if (regionID != null) {
                            ProtectedRegion region = regionManager.getRegion(regionID);
                            if (region != null) {
                                data.getRegionHashMap().put(nonNativeAnimal.getType(), region);
                            } else {
                                plugin.getLogger().warning("Region " + regionID + " does not exist for type " + nonNativeAnimal.getType());
                            }
                        } else {
                            plugin.getLogger().warning("Region data for " + nonNativeAnimal.getType() + " has not been set");
                        }
                    }
                } else {
                    plugin.getLogger().severe("WorldGuard data was unable to be loaded");
                }
            } else {
                plugin.getLogger().severe("An invalid NPC ID has been found in the config");
                plugin.getLogger().severe("All NPC interactions pertaining to this plugin will not work");
                plugin.getLogger().severe("Additionally, the animal event will not occur until a NPC ID has been set and the server has been restarted");
            }
        } else {
            plugin.getLogger().severe("An invalid NPC ID has been found in the config");
            plugin.getLogger().severe("All NPC interactions pertaining to this plugin will not work");
            plugin.getLogger().severe("Additionally, the animal event will not occur until a NPC ID has been set and the server has been restarted");
        }
        long animalEventTime = plugin.getConfig().getLong("animal-event-time");
        long timeBetweenEvent = plugin.getConfig().getLong("time-between-event");
        if (timeBetweenEvent == 0) {
            timeBetweenEvent = 432000000;
        } else {
            timeBetweenEvent *= 1000;
        }
        if (animalEventTime == 0) {
            long newEventTime = System.currentTimeMillis() + timeBetweenEvent;
            plugin.getConfig().set("animal-event-time", newEventTime);
            plugin.getLogger().info("Previous animal event not found");
            plugin.getLogger().info("Setting new event date to: " + new SimpleDateFormat().format(new Date(newEventTime)));
            long nextEventTimeTicks = (newEventTime - System.currentTimeMillis()) / 1000 * 20;
            plugin.setTask(new BukkitRunnable() {
                @Override
                public void run() {
                    plugin.triggerAnimalEvent();
                }
            }.runTaskLater(plugin, nextEventTimeTicks));
        } else {
            if (animalEventTime < System.currentTimeMillis()) {
                plugin.triggerAnimalEvent();
            } else {
                long nextEventTimeTicks = (animalEventTime - System.currentTimeMillis()) / 1000 * 20;
                plugin.setTask(new BukkitRunnable() {
                    @Override
                    public void run() {
                        plugin.triggerAnimalEvent();
                    }
                }.runTaskLater(plugin, nextEventTimeTicks));
            }
        }
        plugin.saveConfig();
    }
}
