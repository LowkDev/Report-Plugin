package me.mherzaqaryan.report.utility;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerUtil {

    public static boolean isOnline(String player) {
        boolean isOnline = false;
        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            if (p.getName().equalsIgnoreCase(player)) {
                isOnline = true;
                break;
            }
        }
        return isOnline;
    }


}
