package mov.naspen.lightmaker.util;

import mov.naspen.lightmaker.LightMaker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class HandWatcher {

    private final LightMaker plugin;

    public HandWatcher(LightMaker plugin) {
        this.plugin = plugin;
    }

    public void startWatching(){
        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if(plugin.getLightManager().isLight(p.getInventory().getItemInMainHand())
                            || plugin.getLightManager().isLight(p.getInventory().getItemInOffHand())
                    ){
                        plugin.getProjector().add(p);
                    }else{
                        plugin.getProjector().remove(p);
                    }
                }
            }
        }.runTaskTimerAsynchronously(this.plugin, 0L, this.plugin.watchPeriod);
    }
}

