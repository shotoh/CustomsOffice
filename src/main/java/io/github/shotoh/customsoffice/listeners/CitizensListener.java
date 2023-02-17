package io.github.shotoh.customsoffice.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.shotoh.customsoffice.CustomsOffice;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizensListener implements Listener {
    private CustomsOffice plugin;

    public CitizensListener(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCitizensEnableEvent(CitizensEnableEvent event) {
        int npcID = plugin.getConfig().getInt("npc-id");
        NPC npc = CitizensAPI.getNPCRegistry().getById(npcID);
        if (npc != null) {
            plugin.getCustomsOfficeData().setNpc(npc);
            plugin.getLogger().info("NPC ID: " + npc.getId() + " has been registered");
            RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(npc.getStoredLocation().getWorld()));
            if (regionManager != null) {
                //
            } else {
                plugin.getLogger().severe("WorldGuard data was unable to be loaded");
            }
        } else {
            plugin.getLogger().severe("An invalid NPC ID has been found in the config");
            plugin.getLogger().severe("All NPC interactions pertaining to this plugin will not work");
        }
    }
}
