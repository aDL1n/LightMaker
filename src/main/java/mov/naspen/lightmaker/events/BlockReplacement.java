package mov.naspen.lightmaker.events;

import mov.naspen.lightmaker.LightMaker;
import org.bukkit.Material;
import org.bukkit.block.data.type.Light;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockReplacement implements Listener {

    private final LightMaker plugin;

    public BlockReplacement(LightMaker plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void BlockPlaceEvent(BlockPlaceEvent e) {
        if(e.isCancelled()) return;
        if(e.getPlayer().getGameMode() == org.bukkit.GameMode.CREATIVE) return;
        if(e.getBlockReplacedState().getType() == Material.LIGHT){
           e.getBlock().getWorld().dropItemNaturally(
                   e.getBlock().getLocation(),
                   plugin.getLightManager().makeLight(((Light) e.getBlockReplacedState().getBlockData()).getLevel())
           );
        }
    }
}
