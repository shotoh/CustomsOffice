package io.github.shotoh.customsoffice.utils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Player;
import org.intellij.lang.annotations.Subst;

public class Utils {
    public static void playSound(Player player, @Subst("entity.experience_orb.pickup") String id, float volume, float pitch) {
        player.playSound(Sound.sound(Key.key(id), Sound.Source.MASTER, volume, pitch), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
    }
}
