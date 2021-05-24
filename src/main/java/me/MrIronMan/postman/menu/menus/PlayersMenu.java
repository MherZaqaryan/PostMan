package me.MrIronMan.postman.menu.menus;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.listeners.PlayerChatEvent;
import me.MrIronMan.postman.menu.PaginatedMenu;
import me.MrIronMan.postman.menu.PlayerMenuUtility;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayersMenu extends PaginatedMenu {

    public PlayersMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }
    private final List<String> players = PostMan.getInstance().getSQLData().getPlayers();

    private String page() {
        assert players != null;
        int p = (int) Math.ceil(players.size() / (float) maxItemsPerPage);
        return  (page + 1) + "/" + (p == 0 ? 1 : p);
    }

    @Override
    public String getMenuName() {
        return "> &7Players Menu " + page();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        PlayerMenuUtility pmu = PostMan.getPlayerMenuUtility(player);
        if (e.getSlot() == 18) {
            if (page != 0) {
                page = page - 1;
                super.open();
            }
        }
        else if (e.getSlot() == 26) {
            assert players != null;
            if (!((index + 1) >= players.size())) {
                page = page + 1;
                super.open();
            }
        }
        else if (e.getSlot() == 48) {
            PlayerChatEvent.mailsSearchMap.remove(player.getUniqueId());
            PlayerChatEvent.playersSearchMap.put(player.getUniqueId(), null);
            player.closeInventory();
            player.sendMessage(TextUtil.colorize(PluginData.SEARCH_PLAYER));
        }
        else if (e.getSlot() == 50) {
            player.closeInventory();
            pmu.setComposeMode(false);
        }
        else if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
            if (pmu.getComposeMode()) {
                String playerName = TextUtil.strip(e.getCurrentItem().getItemMeta().getDisplayName());
                pmu.setPlayerToSend(playerName);
                new MailsMenu(pmu).open();
            }
        }
        else if (e.getSlot() == 45) {
            new MainMenu(pmu).open();
        }
    }

    public String playerEmail(String player) {
        if (PostMan.getInstance().getSQLData().getEmail(player) == null) return "Not Set";
        return PostMan.getInstance().getSQLData().getEmail(player);
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        if (!(players != null && !players.isEmpty())) return;
        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= players.size()) break;
            if (players.get(index) == null) continue;
            String player = players.get(index);
            List<String> lore = new ArrayList<>();
            lore.add("");
            lore.add("&6Email:");
            lore.add("&7" + playerEmail(player));
            if (playerMenuUtility.getPlayerToFind() != null && player.toLowerCase().contains(playerMenuUtility.getPlayerToFind().toLowerCase()) && PostMan.getInstance().getSQLData().isVerified(player)) {
                inventory.addItem(makeHead(player, "&e&l" + player, lore));
            }else if (playerMenuUtility.getPlayerToFind() == null && PostMan.getInstance().getSQLData().isVerified(player)) {
                inventory.addItem(makeHead(player, "&e&l" + player, lore));
            }
        }
    }

}
