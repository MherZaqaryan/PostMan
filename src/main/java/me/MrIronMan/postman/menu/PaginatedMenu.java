package me.MrIronMan.postman.menu;

import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int maxItemsPerPage = 21;
    protected int index = 0;


    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void addMenuBorder(){
        inventory.setItem(18, makeItem(Material.ARROW, "&cPrevious Page"));
        inventory.setItem(26, makeItem(Material.ARROW, "&aNext Page"));
        inventory.setItem(48, makeItem(Material.SIGN, "&eSearch Player"));
        inventory.setItem(50, makeItem(Material.BARRIER, "&4Close Menu"));
        inventory.setItem(45, makeItem(Material.IRON_DOOR, "&eBack"));

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.GRAY_GLASS_PANE);
            }
        }
        inventory.setItem(17, super.GRAY_GLASS_PANE);
        inventory.setItem(27, super.GRAY_GLASS_PANE);
        inventory.setItem(35, super.GRAY_GLASS_PANE);

        for (int i = 37;i < 44;i++) {
            inventory.setItem(i, super.LIGHT_GRAY_GLASS_PANE);
        }

        inventory.setItem(36, super.BLACK_GLASS_PANE);
        inventory.setItem(46, super.BLACK_GLASS_PANE);
        inventory.setItem(47, super.BLACK_GLASS_PANE);
        inventory.setItem(49, super.BLACK_GLASS_PANE);
        inventory.setItem(51, super.BLACK_GLASS_PANE);
        inventory.setItem(52, super.BLACK_GLASS_PANE);
        inventory.setItem(53, super.BLACK_GLASS_PANE);
        inventory.setItem(44, super.BLACK_GLASS_PANE);
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}
