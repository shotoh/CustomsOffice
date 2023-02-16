package io.github.shotoh.customsoffice.listeners;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.guis.NPCGui;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerListener implements Listener {
    private final CustomsOffice plugin;

    public PlayerListener(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event) {
        if (CitizensAPI.getNPCRegistry().isNPC(event.getRightClicked())) {
            NPC clickedNPC = CitizensAPI.getNPCRegistry().getNPC(event.getRightClicked());
            NPC customsNPC = plugin.getCustomsOfficeData().getNpc();
            if (clickedNPC != null && customsNPC != null && clickedNPC.getId() == customsNPC.getId()) {
                event.getPlayer().openInventory(new NPCGui(plugin, event.getPlayer()).getInventory());
            }
        }
    }
}
