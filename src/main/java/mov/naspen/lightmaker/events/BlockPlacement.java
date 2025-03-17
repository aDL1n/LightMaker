package mov.naspen.lightmaker.events;

import mov.naspen.lightmaker.LightMaker;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlacement implements Listener {

    private final LightMaker plugin;

    public BlockPlacement(LightMaker plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void BlockPlaceEvent(BlockPlaceEvent e) {
        if(e.getBlock().getType() == Material.LIGHT && !e.isCancelled()){
            e.getPlayer().spawnParticle(Particle.BLOCK_MARKER, e.getBlock().getLocation().toCenterLocation(), 1, e.getBlock().getBlockData());
        }
    }

}
