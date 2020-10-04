package me.tererun.plugin.kinokogari.kinokogari.scheduler;

import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionbarScheduler extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendActionBar("§f| §6§l残り時間§f: §l" + KinokoGari.gameManager.time + " §f| §b§lキノコポイント§f: §l" + KinokoGari.gameManager.kinokoPoints.get(player.getUniqueId()) + "P §f|");
        }
    }
}
