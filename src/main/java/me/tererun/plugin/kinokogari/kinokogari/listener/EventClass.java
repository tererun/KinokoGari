package me.tererun.plugin.kinokogari.kinokogari.listener;

import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import me.tererun.plugin.kinokogari.kinokogari.game.GameStatus;
import me.tererun.plugin.kinokogari.kinokogari.handler.PlayerHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EventClass implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        if (KinokoGari.gameManager.getGameStatus().equals(GameStatus.PLAYING)) {
            if (!KinokoGari.gameManager.kinokoPoints.containsKey(player.getUniqueId())) KinokoGari.gameManager.kinokoPoints.put(player.getUniqueId(), 0);
            PlayerHandler.playerTeleportRandomField(player);
            PlayerHandler.setInitialEquipments(player);
        } else {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (KinokoGari.gameManager.getGameStatus().equals(GameStatus.PLAYING)) {
            e.setRespawnLocation(PlayerHandler.getTeleportRandomField());
        } else {
            player.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (e.getBlockPlaced().getType().equals(Material.GLASS)) {
            for (int i=0; i<player.getInventory().getSize(); i++) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (itemStack != null) {
                    if (itemStack.getType().equals(Material.GLASS)) {
                        itemStack.setAmount(64);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent e) {
        if (KinokoGari.gameManager.getGameStatus().equals(GameStatus.PLAYING)) {
            if (e.getEntity().getType().equals(EntityType.PLAYER)) {
                Player player = (Player) e.getEntity();
                UUID uuid = e.getEntity().getUniqueId();
                if (e.getItem().getItemStack().getType().toString().endsWith("MUSHROOM")) {
                    for (int i=0; i<e.getItem().getItemStack().getAmount(); i++) {
                        if (e.getItem().getItemStack().getType().equals(Material.RED_MUSHROOM)) {
                            KinokoGari.gameManager.kinokoPoints.put(uuid, KinokoGari.gameManager.kinokoPoints.get(uuid) + 3);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
                        } else if (e.getItem().getItemStack().getType().equals(Material.BROWN_MUSHROOM)) {
                            KinokoGari.gameManager.kinokoPoints.put(uuid, KinokoGari.gameManager.kinokoPoints.get(uuid) + 1);
                            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 0.5F);
                        }
                    }
                }
                player.getInventory().remove(Material.BROWN_MUSHROOM);
                player.getInventory().remove(Material.RED_MUSHROOM);
            }
        }
    }

}
