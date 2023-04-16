package me.fredthedoggy.twistcore;

import me.fredthedoggy.twistcore.commands.TwistCommand;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.scheduler.BukkitRunnable;

public final class TwistCore {

    private static final String twistCoreProperty = "twistcore.manager";
    private static BukkitAudiences adventure;
    private static RemoteManager remoteManager;
    private static LocalModule localModule;

    public static TwistCore init(LocalModule localModule) {
        return new TwistCore(localModule);
    }

    private TwistCore(LocalModule localModule) {
        adventure = BukkitAudiences.create(localModule);
        TwistCore.localModule = localModule;

        if (getCurrentManager() == null) {
            System.setProperty(twistCoreProperty, getOwnManager());
            // After server starts, check if we are still the manager, and if so set up the manager
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (getCurrentManager().equals(getOwnManager())) {
                        TwistCoreManager manager = new TwistCoreManager();
                        TwistCommand.init(manager);
                    }
                }
            }.runTaskLater(localModule, 0);
        }

        // Register self to manager once all modules have been loaded, and a manager has been chosen
        new BukkitRunnable() {
            @Override
            public void run() {
                remoteManager = new RemoteManager();
                remoteManager.registerModule(localModule);
            }
        }.runTaskLater(localModule, 20);
    }

    public static void disable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    public static LocalModule localModule() {
        return localModule;
    }

    public static BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    public static RemoteManager remoteManager() {
        return remoteManager;
    }

    public static String getCurrentManager() {
        return System.getProperty(twistCoreProperty);
    }

    private static String getOwnManager() {
        return TwistCoreManager.class.getCanonicalName();
    }
}
