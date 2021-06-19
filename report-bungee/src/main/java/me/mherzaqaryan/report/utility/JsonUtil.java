package me.mherzaqaryan.report.utility;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class JsonUtil {

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

    public static TextComponent simpleHover(String name, String hoverText) {
        TextComponent text = new TextComponent(TextUtil.colorize(name));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.colorize(hoverText)).create()));
        return text;
    }

    public static TextComponent simple(String name) {
        return new TextComponent(TextUtil.colorize(name));
    }

}
