package mov.naspen.lightmaker.util;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Light;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LightManager {
    private final ArrayList<ItemStack> lightsList = new ArrayList<>();

    public void makeLights(){
        for(int i = 0; i < 16; i++){
            lightsList.add(makeLight(i));
        }
    }

    public ItemStack makeLight(int i){
        ItemStack light = new ItemStack(Material.LIGHT);
        ItemMeta lightMeta = light.getItemMeta();
        BlockData data = Material.LIGHT.createBlockData();
        ((Light) data).setLevel(i);
        ((BlockDataMeta) lightMeta).setBlockData(data);
        light.setItemMeta(lightMeta);

        return light;
    }

    public int getLightLevel(ItemStack light){
        ItemMeta lightMeta = light.getItemMeta();
        BlockData data = ((BlockDataMeta) lightMeta).getBlockData(Material.LIGHT);
        return ((Light) data).getLevel();
    }

    public boolean isLight(ItemStack itemStack){
        return itemStack.getType() == Material.LIGHT;
    }

    @NotNull
    public ArrayList<ItemStack> getLightsList() {
        return lightsList;
    }
}
