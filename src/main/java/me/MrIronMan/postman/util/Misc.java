package me.MrIronMan.postman.util;

import net.md_5.bungee.api.chat.*;

public class Misc {

    public static TextComponent clickMessage(String name, String hoverText, String string, ClickEvent.Action action) {
        TextComponent text = new TextComponent(TextUtil.colorize(name));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize(hoverText)).create()));
        text.setClickEvent(new ClickEvent(action, string));
        return text;
    }

    public static final TextComponent controlPanel = clickMessage("  &c▶ &7/PostMan Control &7- Open control panel", "&a/PostMan Control\n&8Open control panel", "/postman control", ClickEvent.Action.RUN_COMMAND);
    public static final TextComponent sendEmail = clickMessage("  &c▶ &7/PostMan Compose <Player/All> <FileName> &7- Create compose", "&a/PostMan Compose <Player/All> <FileName>\n&8Create compose about server update\n&8or something else and send to\n&8verified player(s)", "/PostMan Compose <Player/All> <FileName>", ClickEvent.Action.SUGGEST_COMMAND);
    public static final TextComponent unRegisterEmail = clickMessage("  &c▶ &7/PostMan Unregister <Player> &7- Unregister player", "&a/PostMan Unregister <Player>\n&8Unregister player email address\n&8so he/she need to verify again", "/PostMan Unregister <Player>", ClickEvent.Action.SUGGEST_COMMAND);
    public static final TextComponent setSubject = clickMessage("  &c▶ &7/PostMan SetSubject <File> <Subject> &7- Set subject", "&a/PostMan SetSubject <File> <Subject>\n&8Set subject for file", "/PostMan SetSubject <File> <Subject>", ClickEvent.Action.SUGGEST_COMMAND);


    public static TextComponent update() {
        TextComponent part1 = new TextComponent(TextUtil.colorize("%prefix% &7New update available, please download it from "));
        TextComponent url = new TextComponent(TextUtil.colorize("&aHere"));
        url.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize("&7Click here to get the link")).create()));
        url.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/88316"));
        part1.addExtra(url);
        return part1;
    }

}
