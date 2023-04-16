package me.fredthedoggy.twistcore;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RemoteModule {
    Class<? extends JavaPlugin> moduleClass;
    JavaPlugin module;

    public RemoteModule(Class<? extends JavaPlugin> moduleClass, JavaPlugin module) {
        this.moduleClass = moduleClass;
        this.module = module;
    }

    public ItemStack getIcon() {
        ItemStack icon = null;
        try {
            Method getIcon = moduleClass.getMethod("getIcon");
            icon = (ItemStack) getIcon.invoke(module);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return icon;
    }

    public void onModuleEnable() {
        try {
            Method onModuleEnable = moduleClass.getMethod("onModuleEnable");
            onModuleEnable.invoke(module);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void onModuleDisable() {
        try {
            Method onModuleDisable = moduleClass.getMethod("onModuleDisable");
            onModuleDisable.invoke(module);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public final boolean isModuleEnabled() {
        Boolean moduleEnabled = false;
        try {
            Method isModuleEnabled = moduleClass.getMethod("isModuleEnabled");
            moduleEnabled = (boolean) isModuleEnabled.invoke(module);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return moduleEnabled;
    }

    public void setModuleEnabled(boolean enabled) {
        try {
            Method setModuleEnabled = moduleClass.getMethod("setModuleEnabled", boolean.class);
            setModuleEnabled.invoke(module, enabled);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public final void enableModule() {
        if (!isModuleEnabled()) {
            onModuleEnable();
            setModuleEnabled(true);
        }
    }

    public final void disableModule() {
        if (isModuleEnabled()) {
            onModuleDisable();
            setModuleEnabled(false);
        }
    }

    public final void reloadModule() {
        onModuleDisable();
        onModuleEnable();
    }

    public final PluginDescriptionFile getDescription() {
        PluginDescriptionFile description = null;
        try {
            Method getDescription = moduleClass.getMethod("getDescription");
            description = (PluginDescriptionFile) getDescription.invoke(module);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return description;
    }
}
