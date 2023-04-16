package me.fredthedoggy.twistcore.commands;

import me.fredthedoggy.twistcore.TwistCoreManager;
import me.fredthedoggy.twistcore.gui.ModuleSelectorGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.HumanEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TwistCommand extends BukkitCommand {
    TwistCoreManager manager;

    public static void init(TwistCoreManager manager) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("twistcore", new TwistCommand(manager));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            final Method syncCommandsMethod = Bukkit.getServer().getClass().getDeclaredMethod("syncCommands");
            syncCommandsMethod.setAccessible(true);
            syncCommandsMethod.invoke(Bukkit.getServer());
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private TwistCommand(TwistCoreManager manager) {
        super("twist");
        this.manager = manager;
        this.description = "TwistCore command";
        this.usageMessage = "/twist";
        this.setPermission("twistcore.command");
        this.setAliases(Arrays.asList("twistcore", "twistc", "twists"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof HumanEntity) {
            ModuleSelectorGUI.openGui(manager, (HumanEntity) sender);
        }
        return true;
    }
}