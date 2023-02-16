package io.github.shotoh.customsoffice.core;

import io.github.shotoh.customsoffice.CustomsOffice;
import io.github.shotoh.customsoffice.guis.NPCGui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CustomsOfficeCommand implements CommandExecutor {
    private CustomsOffice plugin;

    public CustomsOfficeCommand(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = ((Player) sender);
        if (args[0].equals("test1")) {
            player.openInventory(new NPCGui(plugin, player).getInventory());
        }
        return true;
    }
}
