package me.tererun.plugin.kinokogari.kinokogari;

import me.tererun.plugin.kinokogari.kinokogari.command.CommandClass;
import me.tererun.plugin.kinokogari.kinokogari.game.GameManager;
import me.tererun.plugin.kinokogari.kinokogari.listener.EventClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class KinokoGari extends JavaPlugin {

    public static Plugin plugin;

    public static List<Block> mushroomBlock = new ArrayList<>();
    public static GameManager gameManager;
    public static int default_time = 600;

    @Override
    public void onEnable() {
        plugin = this;
        gameManager = new GameManager(default_time);
        getServer().getPluginManager().registerEvents(new EventClass(), this);
        getCommand("kinokogari").setExecutor(new CommandClass());
        setMushroomBlock();
    }

    private void setMushroomBlock() {
        for (int x=-100; x<101; x++) {
            for (int y=0; y<127; y++) {
                for (int z=-100; z<101; z++) {
                    Block block = Bukkit.getWorld("world").getBlockAt(x, y, z);
                    Block mushBlock = block.getLocation().clone().add(0, 1, 0).getBlock();
                    if (((block.getType().equals(Material.MYCELIUM)) || (block.getType().equals(Material.INFESTED_STONE)) || (block.getType().equals(Material.STONE_BRICKS)) || (block.getType().equals(Material.SPRUCE_WOOD))) && (mushBlock.getType().isAir())) {
                        mushroomBlock.add(mushBlock);
                    }
                }
            }
        }
    }

}
