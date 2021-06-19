package me.mherzaqaryan.report.listeners;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginMessageListener implements Listener {

    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) {
        if (e.getTag().equalsIgnoreCase("BungeeCord")) {
            if (e.getTag().equalsIgnoreCase("BungeeCord")) {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
                try {
                    String channel = in.readUTF();
                    if(channel.equals("RunCommand")){
                        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(e.getReceiver().toString());
                        String input = in.readUTF();
                        ProxyServer.getInstance().getPluginManager().dispatchCommand(player, input);
                        in.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }

}
