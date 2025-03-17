package mov.naspen.lightmaker.events;

import mov.naspen.lightmaker.LightMaker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

    private final LightMaker plugin;

    public PlayerLogin(LightMaker plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void PlayerLoginEvent(PlayerLoginEvent e) {
        //load player with recipes when the player logs in.
        e.getPlayer().discoverRecipes(plugin.getRecipeList());
    }
}
