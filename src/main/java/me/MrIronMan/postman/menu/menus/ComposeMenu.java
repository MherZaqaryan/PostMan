package me.MrIronMan.postman.menu.menus;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.menu.Menu;
import me.MrIronMan.postman.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ComposeMenu extends Menu {

    public ComposeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "> &7Compose";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == 10) {
            new MainMenu(PostMan.getPlayerMenuUtility(player)).open();
        }else if (e.getSlot() == 14) {
            PostMan.getPlayerMenuUtility(player).setComposeMode(true);
            new PlayersMenu(PostMan.getPlayerMenuUtility(player)).open();
        }else if (e.getSlot() == 16) {
            PostMan.getPlayerMenuUtility(player).setComposeMode(true);
            PostMan.getPlayerMenuUtility(player).setPlayerToSend(null);
            new MailsMenu(PostMan.getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(14, makeItem(Material.DIAMOND, "&a&lSend To Player"));
        inventory.setItem(16, makeItem(Material.DIAMOND_BLOCK, "&a&lSend To Everyone"));
        inventory.setItem(10, makeItem(Material.REDSTONE, "&c&lBack"));
    }

}
