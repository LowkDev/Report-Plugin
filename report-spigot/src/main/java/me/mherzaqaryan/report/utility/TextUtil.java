package me.mherzaqaryan.report.utility;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {

    public static String colorize(String s) {
        if (s == null) return null;
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> colorize(List<String> s) {
        for (int i = 0; i < s.size(); i++) {
            s.set(i, colorize(s.get(i)));
        }
        return s;
    }

    public static String strip(String s) {
        return ChatColor.stripColor(s);
    }

    public static List<String> replace(List<String> list, String oldChar, String newChar) {
        List<String> newList = new ArrayList<>();
        for (String s : list) newList.add(s.replace(oldChar, newChar));
        return newList;
    }

    public static String toUppercaseFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

}
