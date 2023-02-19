package io.github.shotoh.customsoffice.utils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;

import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    public static void sendMessage(CommandSender sender, String... strings) {
        for (String string : strings) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<!i>" + string));
        }
    }

    public static void playSound(Player player, @Subst("entity.experience_orb.pickup") String id, float volume, float pitch) {
        player.playSound(Sound.sound(Key.key(id), Sound.Source.MASTER, volume, pitch), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
    }

    public static Location getRandomLocation(Location startLoc, Location endLoc) {
        double x1 = startLoc.getX();
        double y1 = startLoc.getY();
        double z1 = startLoc.getZ();
        double x2 = endLoc.getX();
        double y2 = endLoc.getY();
        double z2 = endLoc.getZ();
        double x = x1 == x2 ? x1 : ThreadLocalRandom.current().nextDouble(Math.min(x1, x2), Math.max(x1, x2));
        double y = y1 == y2 ? y1 : ThreadLocalRandom.current().nextDouble(Math.min(y1, y2), Math.max(y1, y2));
        double z = z1 == z2 ? z1 : ThreadLocalRandom.current().nextDouble(Math.min(z1, z2), Math.max(z1, z2));
        return new Location(startLoc.getWorld(), x, y, z);
    }
}
