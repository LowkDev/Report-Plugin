package me.mherzaqaryan.report.utility;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class TextUtil {

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void sendMessage(ProxiedPlayer player, String msg) {
        player.sendMessage(TextComponent.fromLegacyText(colorize(msg)));
    }

    public static void sendMessage(ProxiedPlayer player, TextComponent[] msg) {
        for (TextComponent textComponent : msg) {
            player.sendMessage(textComponent);
        }
    }

    public static String getFormatedTime(int time) {
        int minutes = (int) (Math.floor((float) time / 60));
        int seconds = time % 60;
        String min = minutes+" minute";
        String sec = seconds+" second";
        if (minutes == 0) {
            return seconds > 1 ? sec+"s" : sec;
        }else if (seconds == 0) {
            return minutes > 1 ? min+"s" : min;
        }
        else if (minutes == 1 && seconds == 1) {
            return min+" "+sec;
        }else if (minutes == 1) {
            return min+" "+sec+"s";
        }else if (seconds == 1) {
            return min+"s "+sec;
        }
        else {
            return min+"s "+sec+"s";
        }
    }

}
