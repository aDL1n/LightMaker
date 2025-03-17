package mov.naspen.lightmaker;

import mov.naspen.lightmaker.events.*;
import mov.naspen.lightmaker.util.HandWatcher;
import mov.naspen.lightmaker.util.LightManager;
import mov.naspen.lightmaker.util.Highlighter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class LightMaker extends JavaPlugin {

    private Highlighter projector;
    private Recipe lightRecipe;
    private HandWatcher handWatcher;
    private LightManager lightManager;

    private final List<NamespacedKey> recipeList = new ArrayList<>();
    public int watchPeriod;

    @Override
    public void onEnable() {

        lightRecipe = new Recipe(this);
        projector = new Highlighter(this);
        handWatcher = new HandWatcher(this);
        lightManager = new LightManager();

        getServer().getPluginManager().registerEvents(new PlayerInteraction(this), this);
        getServer().getPluginManager().registerEvents(new BlockReplacement(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlacement(this), this);

        lightManager.makeLights();
        this.saveDefaultConfig();

        //If setting is true, register player log-in event.
        if (this.getConfig().getBoolean("grant_recipes_on_login"))
            getServer().getPluginManager().registerEvents(new PlayerLogin(this), this);

        //allows for the creation of custom recipes by disabling the default base ones entirely
        if(this.getConfig().getBoolean("enable_base_recipes"))
            lightRecipe.addRecipes();

        if(this.getConfig().getBoolean("enable_level_recipes"))
            lightRecipe.addLevelRecipe();

        if(this.getConfig().getBoolean("stop-entity-spawns-at-0"))
            getServer().getPluginManager().registerEvents(new CreatureSpawnEventListener(this), this);


        watchPeriod = this.getConfig().getInt("watch-period-in-ticks") != 0
                ? this.getConfig().getInt("watch-period-in-ticks")
                : 10
        ;

        handWatcher.startWatching();

        this.getLogger().log(Level.INFO,"Lights can now be MADE by *your* hands!");
    }

    @Override
    public void onDisable() {
    }

    @NotNull
    public List<NamespacedKey> getRecipeList() {
        return recipeList;
    }

    public Highlighter getProjector() {
        return projector;
    }

    public Recipe getLightRecipe() {
        return lightRecipe;
    }

    public LightManager getLightManager() {
        return lightManager;
    }

    public HandWatcher getHandWatcher() {
        return handWatcher;
    }
}
