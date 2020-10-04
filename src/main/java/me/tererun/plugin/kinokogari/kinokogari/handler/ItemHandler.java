package me.tererun.plugin.kinokogari.kinokogari.handler;

import com.destroystokyo.paper.Namespaced;
import me.tererun.plugin.kinokogari.kinokogari.KinokoGari;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ItemHandler {
    public static List<ItemStack> getInitialEquipments() {
        List<ItemStack> itemStackList = new ArrayList<>();
        itemStackList.add(0, getFoldingKnife().clone());
        itemStackList.add(1, getBar().clone());
        itemStackList.add(2, getGlass().clone());
        itemStackList.add(3, new ItemStack(Material.BREAD, 64));
        return itemStackList;
    }

    public static ItemStack getFoldingKnife() {
        ItemStack itemStack = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§aフォールディングナイフ");
        itemMeta.setLore(Arrays.asList("§eキノコ§fを採る為の道具"));
        itemMeta.setUnbreakable(true);
        List<Namespaced> destroys = new ArrayList<>();
        destroys.add(new NamespacedKey(KinokoGari.plugin, "red_mushroom"));
        destroys.add(new NamespacedKey(KinokoGari.plugin, "brown_mushroom"));
        Set<Material> materials = new HashSet<>();
        materials.add(Material.RED_MUSHROOM);
        materials.add(Material.BROWN_MUSHROOM);
        itemMeta.setCanDestroy(materials);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getBar() {
        ItemStack itemStack = new ItemStack(Material.NETHERITE_HOE, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§cバールのようなもの");
        itemMeta.setLore(Arrays.asList("§eガラス§fを割る為の道具"));
        itemMeta.setUnbreakable(true);
        Set<Material> materials = new HashSet<>();
        materials.add(Material.GLASS);
        itemMeta.setCanDestroy(materials);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getGlass() {
        ItemStack itemStack = new ItemStack(Material.GLASS, 64);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§fガラス");
        itemMeta.setUnbreakable(true);
        Set<Material> materials = new HashSet<>();
        materials.add(Material.GLASS);
        materials.add(Material.DIRT);
        materials.add(Material.STONE);
        materials.add(Material.BEDROCK);
        materials.add(Material.MYCELIUM);
        materials.add(Material.SPRUCE_WOOD);
        materials.add(Material.SPRUCE_LEAVES);
        materials.add(Material.STONE_BRICKS);
        itemMeta.setCanPlaceOn(materials);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
