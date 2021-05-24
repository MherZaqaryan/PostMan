package me.MrIronMan.postman.menu.menus;

import me.MrIronMan.postman.PostMan;
import me.MrIronMan.postman.listeners.PlayerChatEvent;
import me.MrIronMan.postman.menu.PaginatedMenu;
import me.MrIronMan.postman.menu.PlayerMenuUtility;
import me.MrIronMan.postman.configuration.PluginData;
import me.MrIronMan.postman.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MailsMenu extends PaginatedMenu {

    public List<String> mails = new ArrayList<>();

    public MailsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        File mailsFolder = new File(PostMan.getInstance().getDataFolder() + File.separator + "Mails");
        for (String s : Objects.requireNonNull(mailsFolder.list())) {
            if (s.contains(".html")) {
                StringBuilder sb = new StringBuilder();
                sb.append(s);
                sb.delete(s.length()-5,s.length());
                mails.add(sb.toString());
            }
        }
    }

    private String page() {
        assert mails != null;
        return (page + 1) + "/" + (int) Math.ceil(mails.size() / maxItemsPerPage + 1);
    }

    @Override
    public String getMenuName() {
        return "> &7Mails Menu " + page();
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
            assert mails != null;
            if (!((index + 1) >= mails.size())) {
                page = page + 1;
                super.open();
            }
        }
        else if (e.getSlot() == 48) {
            PlayerChatEvent.playersSearchMap.remove(player.getUniqueId());
            PlayerChatEvent.mailsSearchMap.put(player.getUniqueId(), null);
            player.closeInventory();
            player.sendMessage(TextUtil.colorize(PluginData.SEARCH_MAIL));
        }
        else if (e.getSlot() == 50) {
            player.closeInventory();
            pmu.setComposeMode(false);
        }
        else if (e.getSlot() == 45) {
            new MainMenu(pmu).open();
        }else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
            if (pmu.getComposeMode()) {
                String mailFile = TextUtil.strip(e.getCurrentItem().getItemMeta().getDisplayName());
                if (PostMan.getInstance().getFile(mailFile+".html")) {
                    player.closeInventory();
                    if (pmu.getPlayerToSend() == null) {
                        Bukkit.dispatchCommand(player, "postman compose all " + mailFile);
                    } else {
                        Bukkit.dispatchCommand(player, "postman compose " + PostMan.getPlayerMenuUtility(player).getPlayerToSend() + " " + mailFile);
                    }
                    pmu.setComposeMode(false);
                }
                else {
                    player.sendMessage(TextUtil.colorize(PluginData.FILE_NOT_FOUND.replace("%file%", mailFile+".html")));
                }
            }
         }
    }

    private String subject(String mail) {
        if (PostMan.getInstance().getSQLData().getSubject(mail+".html") == null) {
            return "Not Set";
        }else {
            return PostMan.getInstance().getSQLData().getSubject(mail + ".html");
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        if(mails != null && !mails.isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= mails.size()) break;
                if (mails.get(index) != null) {
                    String mail = mails.get(index);
                    List<String> lore = new ArrayList<>();
                    lore.add("");
                    lore.add("&6Subject:");
                    lore.add("&7" + subject(mail));
                    if (playerMenuUtility.getMailToFind() != null && mail.toLowerCase().contains(playerMenuUtility.getMailToFind().toLowerCase())) {
                        inventory.addItem(makeItem(Material.PAPER, "&e&l" + mail, lore));
                    }else if (playerMenuUtility.getMailToFind() == null) {
                        inventory.addItem(makeItem(Material.PAPER, "&e&l" + mail, lore));
                    }
                }
            }
        }
    }
}
