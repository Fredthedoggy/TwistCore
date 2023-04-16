package me.fredthedoggy.twistcore.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class ScrollingGUI {

    public static Component mm(String string) {
        return Component.empty().decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE)
                .append(MiniMessage.miniMessage().deserialize(string));
    }

    static void backgroundSetup(PaginatedGui gui, Boolean paged) {
        gui.setDefaultClickAction(event1 -> event1.setCancelled(true));
        GuiItem lightFillerItem = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE)
                .name(mm("<gray>")).asGuiItem();
        GuiItem fillerItem = ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE)
                .name(mm("<gray>")).asGuiItem();
        gui.getFiller().fillBorder(fillerItem);
        if (paged) {
            gui.setItem(gui.getRows(), 3, ItemBuilder.from(Material.SPECTRAL_ARROW)
                    .name(mm("<gray>Previous"))
                    .asGuiItem(event1 -> gui.previous()));
            gui.setItem(gui.getRows(), 7, ItemBuilder.from(Material.SPECTRAL_ARROW)
                    .name(mm("<gray>Next"))
                    .asGuiItem(event1 -> gui.next()));
        }
    }

    static void updateItem(PaginatedGui gui, InventoryClickEvent event, GuiItem item) {
        gui.updatePageItem(event.getSlot(), item);
    }
}
