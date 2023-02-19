package io.github.shotoh.customsoffice.core;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.utils.Utils;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CustomsOfficeCommand implements CommandExecutor {
    private CustomsOffice plugin;

    private boolean confirm = false;

    public CustomsOfficeCommand(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.isOp()) {
            if (args.length > 0) {
                switch (args[0]) {
                    case "event":
                        confirm = true;
                        Utils.sendMessage(sender, "<red>You are attempting to manually trigger the animal event", "<red>Type \"/customs confirm\" to confirm this action");
                        break;
                    case "confirm":
                        if (confirm) {
                            plugin.triggerAnimalEvent();
                            confirm = false;
                        }
                        break;
                    case "region":
                        confirm = false;
                        if (args.length > 2) {
                            for (NonNativeAnimal nonNativeAnimal : plugin.getCustomsOfficeData().getNonNativeAnimals()) {
                                if (nonNativeAnimal.getType().toString().equalsIgnoreCase(args[1])) {
                                    NPC npc = plugin.getCustomsOfficeData().getAnimalNPC();
                                    if (npc != null) {
                                        RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(npc.getStoredLocation().getWorld()));
                                        if (regionManager != null) {
                                            ProtectedRegion region = regionManager.getRegion(args[2]);
                                            if (region != null) {
                                                nonNativeAnimal.setRegionID(args[2]);
                                                plugin.getCustomsOfficeData().getRegionHashMap().remove(nonNativeAnimal.getType());
                                                plugin.getCustomsOfficeData().getRegionHashMap().put(nonNativeAnimal.getType(), region);
                                                Utils.sendMessage(sender, "<green>The region " + args[2] + " has been set for the entity type " + nonNativeAnimal.getType());
                                                return true;
                                            } else {
                                                Utils.sendMessage(sender, "<red>The region " + args[2] + " does not exist in this world");
                                                return true;
                                            }
                                        } else {
                                            Utils.sendMessage(sender, "<red>WorldGuard regions were unable to be loaded, it is required to find the specified region by their id");
                                            return true;
                                        }
                                    } else {
                                        Utils.sendMessage(sender, "<red>The Citizens NPC does not exist, it is required for this command to work (only regions in the same world as the npc will count)");
                                        return true;
                                    }
                                }
                            }
                            Utils.sendMessage(sender, "<red>" + args[1] + " is not a valid entity type");
                        } else {
                            Utils.sendMessage(sender, "<red>Invalid amount of arguments");
                        }
                        break;
                }
            }
        }
        return true;
    }
}
