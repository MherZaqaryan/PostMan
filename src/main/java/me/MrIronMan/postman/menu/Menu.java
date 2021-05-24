package me.MrIronMan.postman.menu;

import me.MrIronMan.postman.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public abstract class Menu implements InventoryHolder {

    protected PlayerMenuUtility playerMenuUtility;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = makeItem(Material.STAINED_GLASS_PANE, " ");
    protected ItemStack GRAY_GLASS_PANE = makeItem(Material.STAINED_GLASS_PANE, 1, 7, " ");
    protected ItemStack LIGHT_GRAY_GLASS_PANE = makeItem(Material.STAINED_GLASS_PANE, 1, 8, " ");
    protected ItemStack BLACK_GLASS_PANE = makeItem(Material.STAINED_GLASS_PANE, 1, 15, " ");

    public Menu(PlayerMenuUtility playerMenuUtility) {
        this.playerMenuUtility = playerMenuUtility;
    }

    public abstract String getMenuName();
    public abstract int getSlots();
    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();
    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(), TextUtil.colorize(getMenuName()));
        this.setMenuItems();
        playerMenuUtility.getPlayer().openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setFillerGlass(){
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, LIGHT_GRAY_GLASS_PANE);
            }
        }
    }

    public ItemStack makeItem(Material material, String displayName, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(TextUtil.colorize(displayName));
        itemMeta.setLore(TextUtil.colorize(Arrays.asList(lore)));
        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack makeItem(Material material, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(TextUtil.colorize(displayName));
        itemMeta.setLore(TextUtil.colorize(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack makeItem(Material material, int amount, int data, String displayName, String... lore) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(TextUtil.colorize(displayName));
        itemMeta.setLore(TextUtil.colorize(Arrays.asList(lore)));
        item.setItemMeta(itemMeta);

        return item;
    }

    public ItemStack makeHead(String playerName, String displayName, List<String> lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1 , (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(playerName);
        meta.setDisplayName(TextUtil.colorize(displayName));
        meta.setLore(TextUtil.colorize(lore));
        item.setItemMeta(meta);
        return item;
    }


}
