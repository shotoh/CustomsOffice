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
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getRightClicked());
            if (npc.getUniqueId().equals(plugin.getCustomsOfficeData().getNpc().getUniqueId())) {
                event.getPlayer().openInventory(new NPCGui(plugin, event.getPlayer()).getInventory());
            }
        }
    }
}
