package me.tererun.plugin.kinokogari.kinokogari.command;

import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import me.tererun.plugin.kinokogari.kinokogari.game.GameStatus;
import me.tererun.plugin.kinokogari.kinokogari.handler.PlayerHandler;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandClass implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("kinokogari")) {
            if (sender.hasPermission("kinokogari")) {
                if (args.length == 0) {
                    sender.sendMessage("§7=====§f[§6§lキノコ狩り§f]§7=====");
                    sender.sendMessage("§a/kinokogari§f: このヘルプを表示");
                    sender.sendMessage("§a/kinokogari start§f: ゲームを開始");
                    sender.sendMessage("§a/kinokogari stop§f: ゲームを停止");
                    sender.sendMessage("§a/kinokogari time [値]§f: ゲーム時間を変更");
                    return true;
                } else {
                    if (args[0].equalsIgnoreCase("start")) {
                        KinokoGari.gameManager.prepareGame();
                        return true;
                    } else if (args[0].equalsIgnoreCase("stop")) {
                        if (!KinokoGari.gameManager.getGameStatus().equals(GameStatus.PLAYING)) {
                            sender.sendMessage(PlayerHandler.prefix + "§4ゲーム中でないとストップ出来ません");
                            return true;
                        }
                        KinokoGari.gameManager.stopGame();
                        sender.sendMessage(PlayerHandler.prefix + "§7ゲームを停止します...");
                        return true;
                    } else if (args[0].equalsIgnoreCase("time")) {
                        if (args.length == 2) {
                            if (!NumberUtils.isDigits(args[1])) {
                                sender.sendMessage(PlayerHandler.prefix + "§4値が正常ではありません");
                                return true;
                            }
                            int time = Integer.parseInt(args[1]);
                            KinokoGari.gameManager.time = time;
                            sender.sendMessage(PlayerHandler.prefix + "§aゲーム時間を " + time + " 秒にしました");
                            return true;
                        } else {
                            sender.sendMessage(PlayerHandler.prefix + "§4コマンドが間違っています");
                            return true;
                        }
                    } else {
                        sender.sendMessage(PlayerHandler.prefix + "§4コマンドが存在しません");
                        return true;
                    }
                }
            } else {
                sender.sendMessage(PlayerHandler.prefix + "§4権限がありません");
                return true;
            }
        }
        return false;
    }
}
