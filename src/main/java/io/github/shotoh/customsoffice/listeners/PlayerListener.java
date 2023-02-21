package io.github.shotoh.customsoffice.listeners;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.guis.animal.AnimalNPCGui;
import io.github.shotoh.customsoffice.utils.Utils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class PlayerListener implements Listener {
    private final CustomsOffice plugin;

    public PlayerListener(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (CitizensAPI.getNPCRegistry().isNPC(event.getRightClicked())) {
            NPC clickedNPC = CitizensAPI.getNPCRegistry().getNPC(event.getRightClicked());
            NPC customsNPC = plugin.getCustomsOfficeData().getAnimalNPC();
            if (clickedNPC != null && customsNPC != null && clickedNPC.getId() == customsNPC.getId()) {
                event.getPlayer().openInventory(new AnimalNPCGui(plugin, event.getPlayer()).getInventory());
            }
        } else {
            PersistentDataContainer con = event.getRightClicked().getPersistentDataContainer();
            if (con.has(plugin.getCustomsOfficeKeys().getOwnerKey())) {
                String ownerID = con.get(plugin.getCustomsOfficeKeys().getOwnerKey(), PersistentDataType.STRING);
                if (ownerID != null) {
                    try {
                        UUID ownerUUID = UUID.fromString(ownerID);
                        if (!event.getPlayer().getUniqueId().equals(ownerUUID)) {
                            event.setCancelled(true);
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(ownerUUID);
                            Utils.sendMessage(event.getPlayer(), "<red>This animal does not belong to you! It belongs to " + offlinePlayer.getName() + "!");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
