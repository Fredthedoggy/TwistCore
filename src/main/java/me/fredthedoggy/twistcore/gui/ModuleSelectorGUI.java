package me.fredthedoggy.twistcore.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.fredthedoggy.twistcore.RemoteModule;
import me.fredthedoggy.twistcore.TwistCore;
import me.fredthedoggy.twistcore.TwistCoreManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModuleSelectorGUI extends ScrollingGUI {
    public static void openGui(TwistCoreManager manager, HumanEntity player) {
        List<RemoteModule> modules = manager.getModules();
        int totalRows = (int) Math.ceil((double) modules.size() / 7);
        int rows = Math.min(totalRows, 4);
        PaginatedGui gui = Gui.paginated().rows(rows + 2).pageSize(rows * 7).title(mm("Twist Selector")).create();
        backgroundSetup(gui, totalRows > 4);
        for (RemoteModule module : modules) {
            gui.addItem(getModuleItem(module, gui));
        }
        if (TwistCoreManager.getInstance().getVariable("runner") == null) {
            TwistCoreManager.getInstance().setVariable("runner", player.getUniqueId().toString());
        }
        gui.setItem(gui.getRows(), 5, ItemBuilder.skull()
                .owner(Bukkit.getOfflinePlayer(UUID.fromString(TwistCoreManager.getInstance().getVariable("runner"))))
                .name(mm(player.getName())).lore(mm("<gray>Click to Select Speedrunner"))
                .asGuiItem((event -> {
                    event.setCancelled(true);
                    gui.close(event.getWhoClicked());
                    TwistCore.adventure().player((Player) event.getWhoClicked())
                            .sendMessage(mm("<green>Speedrunner set to <white>" + player.getName()));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            PlayerSelectorGUI.openGui(manager, player);
                        }
                    }.runTaskLater(TwistCore.localModule(), 4);
                })));
        gui.open(player);
    }

    private static GuiItem getModuleItem(RemoteModule module, PaginatedGui gui) {
        List<Component> lore = new ArrayList<>();
        PluginDescriptionFile description = module.getDescription();
        if (description.getDescription() != null) {
            for (String s : description.getDescription().split("\n")) {
                lore.add(mm("<gray>" + s));
            }
        }
        lore.add(Component.empty());
        if (description.getAuthors().size() > 0) {
            lore.add(mm("<gray>" + (description.getAuthors()
                    .size() > 1 ? "Authors" : "Author") + ": <white>" + String.join(", ", description.getAuthors())));
        }
        if (description.getWebsite() != null) {
            lore.add(mm("<gray>Website: <white>" + description.getWebsite()));
        }
        lore.add(mm("<gray>Version: <white>" + description.getVersion()));
        lore.add(Component.empty());
        lore.add(mm(module.isModuleEnabled() ? "<green>Enabled" : "<red>Disabled"));
        return ItemBuilder.from(module.getIcon()).name(mm(module.getDescription().getName())).lore(lore)
                .asGuiItem((event -> {
                    event.setCancelled(true);
                    if (module.isModuleEnabled()) {
                        module.disableModule();
                    } else {
                        module.enableModule();
                    }
                    updateItem(gui, event, getModuleItem(module, gui));
                }));
    }
}
