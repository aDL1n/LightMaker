package mov.naspen.lightmaker.events;

import mov.naspen.lightmaker.LightMaker;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.*;

public class PlayerInteraction implements Listener {

    private final LightMaker plugin;

    public PlayerInteraction(LightMaker plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PlayerInteractEvent(PlayerInteractEvent e) {
        if (e.useInteractedBlock() == Event.Result.DENY){ return;}
        if (e.getItem() != null && e.getClickedBlock() != null && plugin.getLightManager().isLight(e.getItem())) {
            //get Block
            Block b = e.getClickedBlock();
            //check block type
            if (b.getType() == Material.LIGHT) {
                //get block level
                int l = ((Levelled) b.getBlockData()).getLevel();
                //break
                if (e.getAction() == Action.LEFT_CLICK_BLOCK) {

                    BlockBreakEvent be = new BlockBreakEvent(b, e.getPlayer());
                    be.callEvent();
                    if(!be.isCancelled()){
                        b.breakNaturally();
                        List<Item> items = new ArrayList<>();
                        items.add(b.getWorld().dropItemNaturally(b.getLocation(), plugin.getLightManager().getLightsList().get(l)));
                        if(!(new BlockDropItemEvent(b, b.getState(), e.getPlayer(), items).callEvent())){
                            for(Item i : items){
                                i.remove();
                            }
                        }
                    }
                    //add
                } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK && !e.getPlayer().isSneaking()) {
                    //if Player is sneaking don't match the light level. If they are use the vanilla level
                    //get hand level
                    int handLevel = plugin.getLightManager().getLightLevel(e.getItem().asOne());
                    e.setCancelled(true);
                    BlockData data = b.getBlockData();
                    ((Levelled) data).setLevel(handLevel);
                    b.setBlockData(data);
            }
            }
        }
    }
}
