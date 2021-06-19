package me.mherzaqaryan.report.utility;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class JsonUtil {

    public static TextComponent simple(String name) {
        return new TextComponent(TextUtil.colorize(name));
    }

    public static TextComponent hover(String name, String hoverText) {
        TextComponent text = new TextComponent(TextUtil.colorize(name));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize(hoverText)).create()));
        return text;
    }

    public static TextComponent commandSuggest(String name, String hoverText, String cmd) {
        TextComponent text = new TextComponent(TextUtil.colorize(name));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize(hoverText)).create()));
        text.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, cmd));
        return text;
    }

    public static TextComponent commandRun(String name, String hoverText, String cmd) {
        TextComponent text = new TextComponent(TextUtil.colorize(name));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize(hoverText)).create()));
        text.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd));
        return text;
    }

    public static TextComponent openURL(String name, String hoverText, String url) {
        TextComponent text = new TextComponent(TextUtil.colorize(name));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize(hoverText)).create()));
        text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return text;
    }

    public static TextComponent convertString(String string) {
        String[] args = string.split("; ");
        args[0] = args[0] + "\n";
        if (args.length == 4) {
            if (args[3].equalsIgnoreCase("rcmd")) {
                return commandRun(args[0], args[1], args[2]);
            }else if (args[3].equalsIgnoreCase("scmd")) {
                return commandSuggest(args[0], args[1], args[2]);
            }else if (args[3].equalsIgnoreCase("link")) {
                return openURL(args[0], args[1], args[2]);
            }
        }else if (args.length == 2) {
            return hover(args[0], args[1]);
        }else{
            return simple(args[0]);
        }
        return null;
    }

}
