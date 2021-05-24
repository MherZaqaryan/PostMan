package me.MrIronMan.postman.menu.menus;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.menu.Menu;
import me.MrIronMan.postman.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainMenu extends Menu {

    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "> &7Control Panel";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == 10) {
            new ComposeMenu(PostMan.getPlayerMenuUtility(player)).open();
        }else if (e.getSlot() == 13) {
            new PlayersMenu(PostMan.getPlayerMenuUtility(player)).open();
        }else if (e.getSlot() == 16) {
            new MailsMenu(PostMan.getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(10, makeItem(Material.PAINTING, "&c&lCompose"));
        inventory.setItem(13, makeItem(Material.CHEST, "&c&lVerified Players"));
        inventory.setItem(16,makeItem(Material.ENDER_CHEST, "&c&lMail Files"));
    }
}
