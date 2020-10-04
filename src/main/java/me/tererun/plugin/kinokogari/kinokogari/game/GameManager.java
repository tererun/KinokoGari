package me.tererun.plugin.kinokogari.kinokogari.game;

import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import me.tererun.plugin.kinokogari.kinokogari.handler.MushHandler;
import me.tererun.plugin.kinokogari.kinokogari.handler.PlayerHandler;
import me.tererun.plugin.kinokogari.kinokogari.scheduler.ActionbarScheduler;
import me.tererun.plugin.kinokogari.kinokogari.scheduler.GameScheduler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {
    public int time;
    private GameStatus gameStatus;
    public HashMap<UUID, Integer> kinokoPoints;

    public GameManager(int time) {
        this.time = time;
        gameStatus = GameStatus.STOP;
        kinokoPoints = new HashMap<>();
    }

    private int countDown = 3;
    public void prepareGame() {
        gameStatus = GameStatus.PREPAREING;
        Bukkit.broadcastMessage(PlayerHandler.prefix + "ゲームを開始します...");
        for (int i=0; i<3; i++) {
            MushHandler.setMush();
        }
        countDown = 3;
        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerHandler.playSoundAllPlayers(Sound.BLOCK_LANTERN_PLACE, 2F, 2F);
                if (countDown >= 3) {
                    PlayerHandler.sendTitleAllPlayers("§8《 §6§lゲーム開始まで §8》", "§7≡ §a3 §7≡", 5, 40, 5);
                } else if (countDown == 2) {
                    PlayerHandler.sendTitleAllPlayers("§8《 §6§lゲーム開始まで §8》", "§7= §e2 §7=", 5, 40, 5);
                } else if (countDown == 1) {
                    PlayerHandler.sendTitleAllPlayers("§8《 §6§lゲーム開始まで §8》", "§7- §c1 §7-", 5, 40, 5);
                } else if (countDown == 0) {
                    PlayerHandler.sendTitleAllPlayers("§8《 §6§lキノコ狩り §8》", "§7| §d開始 §7|", 5, 40, 5);
                    startGame();
                    cancel();
                    return;
                }
                countDown--;
            }
        }.runTaskTimer(KinokoGari.plugin, 40L, 20L);

    }

    public void startGame() {
        gameStatus = GameStatus.PLAYING;
        Bukkit.broadcastMessage(PlayerHandler.prefix + "ゲーム開始");
        new GameScheduler().runTaskTimer(KinokoGari.plugin, 0L, 20L);
        new ActionbarScheduler().runTaskTimer(KinokoGari.plugin, 0L, 1L);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.ADVENTURE);
            PlayerHandler.setInitialEquipments(player);
            PlayerHandler.playerTeleportRandomField(player);
            kinokoPoints.put(player.getUniqueId(), 0);
            PlayerHandler.playSoundAllPlayers(Sound.BLOCK_ANVIL_LAND, 2F, 0.5F);
        }

    }

    public void stopGame() {
        gameStatus = GameStatus.STOP;
        PlayerHandler.playSoundAllPlayers(Sound.BLOCK_ANVIL_LAND, 2F, 0.5F);
        PlayerHandler.sendTitleAllPlayers("§8《 §6§lキノコ狩り §8》", "§7| §b終了 §7|", 5, 40, 5);
        Bukkit.broadcastMessage(PlayerHandler.prefix + "ゲーム終了");
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
        }
        postGame();
    }

    public void postGame() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.getInventory().clear();
                    player.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    player.setGameMode(GameMode.ADVENTURE);
                }
                int lastPoint = 0;
                int lastRank = 1;
                for (Map.Entry<String, Integer> mushRank : MushHandler.calcMush()) {
                    if (mushRank.getValue().intValue() == lastPoint) {
                        lastRank--;
                    }

                    String colorCode = "";
                    switch (lastRank) {
                        case 1:
                            colorCode = "§6";
                            break;
                        case 2:
                            colorCode = "§7";
                            break;
                        case 3:
                            colorCode = "§c";
                            break;
                        default:
                            colorCode = "§8";
                            break;
                    }
                    Bukkit.broadcastMessage(colorCode + lastRank + "位 " + mushRank.getKey() + "§f: §e" + mushRank.getValue() + "P");
                    lastPoint = mushRank.getValue();
                    lastRank++;
                }
                KinokoGari.gameManager = new GameManager(KinokoGari.default_time);
            }
        }.runTaskLater(KinokoGari.plugin, 40L);
    }

    /*
    ======================
     SET
    ======================
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /*
    ======================
     GET
    ======================
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
