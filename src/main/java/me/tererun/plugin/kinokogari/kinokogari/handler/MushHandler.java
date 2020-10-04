package me.tererun.plugin.kinokogari.kinokogari.handler;

import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import org.bukkit.*;
import org.bukkit.block.Block;

import java.util.*;

public class MushHandler {
    public static void setMush() {
        Random random = new Random();
        for (int i=0; i<6; i++) {
            int spawnMushType = random.nextInt(10);
            Material mush = Material.BROWN_MUSHROOM;
            if (spawnMushType == 0) {
                mush = Material.RED_MUSHROOM;
            }
            Block mushPlaceBlock = KinokoGari.mushroomBlock.get(random.nextInt(KinokoGari.mushroomBlock.size()));
            Location mushPlaceLoc = mushPlaceBlock.getLocation();
            mushPlaceBlock.setType(mush);
            PlayerHandler.spawnParticleWorld(Particle.VILLAGER_HAPPY, mushPlaceLoc.clone().add(0.5, 0.5 ,0.5), 50, 0.25, 0.25, 0.25, 0.1);
            PlayerHandler.playSoundWorld(mushPlaceLoc, Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 2);
        }
    }

    public static List<Map.Entry<String, Integer>> calcMush() {
        Map<String, Integer> mMap = new HashMap<>();
        for (UUID uuid : KinokoGari.gameManager.kinokoPoints.keySet()) {
            mMap.put(Bukkit.getOfflinePlayer(uuid).getName(), KinokoGari.gameManager.kinokoPoints.get(uuid));
            System.out.println("put: " + uuid + ", " + KinokoGari.gameManager.kinokoPoints.get(uuid));
        }

        List<Map.Entry<String, Integer>> list_entries = new ArrayList<>(mMap.entrySet());
        Collections.sort(list_entries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });
        return list_entries;
    }
}
