package me.tererun.plugin.kinokogari.kinokogari.scheduler;

import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import me.tererun.plugin.kinokogari.kinokogari.game.GameStatus;
import me.tererun.plugin.kinokogari.kinokogari.handler.MushHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class GameScheduler extends BukkitRunnable {

    @Override
    public void run() {
        if (KinokoGari.gameManager.time <= 0) KinokoGari.gameManager.stopGame();
        if (KinokoGari.gameManager.getGameStatus().equals(GameStatus.STOP)) {
            this.cancel();
            return;
        }

        if (KinokoGari.gameManager.time % 10 == 0) {
            MushHandler.setMush();
        }

        KinokoGari.gameManager.time--;
    }


}
