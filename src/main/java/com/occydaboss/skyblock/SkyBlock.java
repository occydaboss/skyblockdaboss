package com.occydaboss.skyblock;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.occydaboss.skyblock.commands.*;
import com.occydaboss.skyblock.commands.tabcompleters.CommandSetLevelTabCompleter;
import com.occydaboss.skyblock.util.AddPrefix;
import com.occydaboss.skyblock.util.EmptyChunkGenerator;
import com.occydaboss.skyblock.util.Level;
import dev.jcsoftware.jscoreboards.JPerPlayerMethodBasedScoreboard;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Logger;

public final class SkyBlock extends JavaPlugin {
    public static MongoDatabase database;
    public static Logger logger;
    public static JPerPlayerMethodBasedScoreboard scoreboard;
    public static WorldBorderApi worldBorderApi;

    public static Economy economy = null;

    private static SkyBlock instance;

    @Override
    public void onEnable() {
        /*
        Spigot Stuff
         */

        instance = this;
        logger = this.getLogger();
        this.saveDefaultConfig();

        // Commands
        this.getCommand("island").setExecutor(new CommandIsland());
        this.getCommand("resetislands").setExecutor(new CommandResetIslands());
        this.getCommand("shop").setExecutor(new CommandShop());
        this.getCommand("setlevel").setExecutor(new CommandSetLevel());
        this.getCommand("levelup").setExecutor(new CommandLevelUp());
        // Tab Completers
        this.getCommand("setlevel").setTabCompleter(new CommandSetLevelTabCompleter());
        //Register Events
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);

        // Island
        if (Bukkit.getWorld("islands") == null) {
            this.getLogger().info("Creating Island World...");
            WorldCreator wc = new WorldCreator("islands");
            wc.generator(new EmptyChunkGenerator());
            wc.createWorld();
        }

        /*
        Other Initialisations
         */

        this.getLogger().info("Connecting to MongoDB...");
        ConnectionString connectionString = new ConnectionString(this.getConfig().getString("mongoURI"));
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        database = mongoClient.getDatabase("skyblock");

        scoreboard = new JPerPlayerMethodBasedScoreboard();

        if (!setupEconomy()) {
            logger.severe(AddPrefix.addPrefix("Disabled due to no Vault dependency found!"));
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        RegisteredServiceProvider<WorldBorderApi> worldBorderApiRegisteredServiceProvider = this.getServer().getServicesManager().getRegistration(WorldBorderApi.class);

        if (worldBorderApiRegisteredServiceProvider == null) {
            this.getLogger().info("API not found");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        worldBorderApi = worldBorderApiRegisteredServiceProvider.getProvider();

        this.getLogger().info("Loaded!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("GoodBye! OwO");
    }

    public static SkyBlock getInstance() {
        return instance;
    }

    public static void updateScoreboard(Player player) {
        SkyBlock.scoreboard.setLines(player, Arrays.asList(
                "Level: " + Level.getMainLevel(player),
                "Balance: " + economy.getBalance(player)
        ));
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }
}
