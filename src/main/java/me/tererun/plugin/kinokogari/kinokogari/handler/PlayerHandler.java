package me.tererun.plugin.kinokogari.kinokogari.handler;

import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class PlayerHandler {
    public static String prefix = "§7> §f";

    public static void sendTitleAllPlayers(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subTitle, fadeIn, stay, fadeOut);
        }
    }

    public static void playSoundAllPlayers(Sound sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), sound, volume, pitch);
        }
    }

    public static void playSoundWorld(Location location, Sound sound, float volume, float pitch) {
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    public static void spawnParticleAllPlayers(Particle particle, Location location, int count, double dx, double dy, double dz, double extra) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.spawnParticle(particle, location, count, dx, dy, dz, extra);
        }
    }

    public static void spawnParticleWorld(Particle particle, Location location, int count, double dx, double dy, double dz, double extra) {
        location.getWorld().spawnParticle(particle, location, count, dx, dy, dz, extra);
    }

    public static void playerTeleportRandomField(Player player) {
        Random random = new Random();
        Location location = KinokoGari.mushroomBlock.get(random.nextInt(KinokoGari.mushroomBlock.size())).getLocation().clone();
        player.teleport(location);
    }

    public static Location getTeleportRandomField() {
        Random random = new Random();
        Location location = KinokoGari.mushroomBlock.get(random.nextInt(KinokoGari.mushroomBlock.size())).getLocation().clone();
        return location;
    }

    public static void sendActionbarAllPlayers(String actionbar) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar(actionbar);
        }
    }

    public static void setInitialEquipments(Player player) {
        player.getInventory().clear();
        for (ItemStack itemStack : ItemHandler.getInitialEquipments()) {
            player.getInventory().setItem(ItemHandler.getInitialEquipments().indexOf(itemStack), itemStack);
        }
    }

}
