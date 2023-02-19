package io.github.shotoh.customsoffice.core;

import io.github.shotoh.customsoffice.CustomsOffice;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomsTabCompleter implements TabCompleter {
    private final CustomsOffice plugin;

    public CustomsTabCompleter(CustomsOffice plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        if (sender.isOp()) {
            if (args.length == 1) {
                list.add("event");
                list.add("confirm");
                list.add("region");
            }
        }
        return list;
    }
}
