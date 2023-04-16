package me.fredthedoggy.twistcore;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class LocalModule extends JavaPlugin {

    public abstract ItemStack getIcon();

    public abstract void onModuleEnable();

    public abstract void onModuleDisable();

    private Boolean moduleEnabled = false;

    final public boolean isModuleEnabled() {
        return moduleEnabled;
    }

    public void setModuleEnabled(boolean enabled) {
        moduleEnabled = enabled;
    }

    final public void enableModule() {
        if (!isModuleEnabled()) {
            onModuleEnable();
            setModuleEnabled(true);
        }
    }

    final public void disableModule() {
        if (isModuleEnabled()) {
            onModuleDisable();
            setModuleEnabled(false);
        }
    }

    final public void reloadModule() {
        onModuleDisable();
        onModuleEnable();
    }
}
