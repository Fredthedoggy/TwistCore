package me.fredthedoggy.twistcore.gui;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.fredthedoggy.twistcore.TwistCoreManager;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerSelectorGUI extends ScrollingGUI {
    public static void openGui(TwistCoreManager manager, HumanEntity player) {
        List<Player> players = player.getServer().getOnlinePlayers().stream().map(p -> (Player) p).toList();
        int totalRows = (int) Math.ceil((double) players.size() / 7);
        int rows = Math.min(totalRows, 4);
        PaginatedGui gui = Gui.paginated().rows(rows + 2).pageSize(rows * 7).title(mm("Player Selector")).create();
        backgroundSetup(gui, totalRows > 4);
        for (Player p : players) {
            gui.addItem(getPlayerItem(p, gui));
        }
        gui.open(player);
    }

    private static GuiItem getPlayerItem(Player player, PaginatedGui gui) {
        return ItemBuilder.skull().owner(player).name(mm(player.getName())).lore(mm("<gray>Click to Select"))
                .asGuiItem((event -> {
                    TwistCoreManager.getInstance().setVariable("runner", player.getUniqueId().toString());
                    event.setCancelled(true);
                    gui.close(event.getWhoClicked());
                }));
    }
}
