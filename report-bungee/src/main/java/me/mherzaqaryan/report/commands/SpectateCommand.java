package me.mherzaqaryan.report.commands;

import me.mherzaqaryan.report.utility.TextUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import me.mherzaqaryan.report.utility.PlayerUtil;
import me.mherzaqaryan.report.utility.SpigotUtil;

import java.util.HashMap;

public class SpectateCommand extends Command {

    public HashMap<ProxiedPlayer, ServerInfo> spectateServerMap = new HashMap<>();
    private String playerName = null;
    public SpectateCommand() {
        super("spectate", "Lowk.Spectate", "spec");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;

        if (args.length == 0) {
            TextUtil.sendMessage(player, "&cUsage: &7/spectate <Player>");
        }else {
            if (args[0].equalsIgnoreCase("leave")) {
                if (spectateServerMap.containsKey(player)) {
                    if (!spectateServerMap.get(player).equals(player.getServer().getInfo())) {
                        player.connect(spectateServerMap.get(player));
                    }
                    spectateServerMap.remove(player);
                }else {
                    TextUtil.sendMessage(player, "&cYou are not in spectator mode!");
                }
                return;
            }
            this.playerName = args[0];
            if (PlayerUtil.isOnline(playerName)) {
                TextUtil.sendMessage(player, "&aTeleporting you to " + playerName + ", please wait...");
                ProxiedPlayer pp = ProxyServer.getInstance().getPlayer(playerName);
                ServerInfo playerServer = pp.getServer().getInfo();
                ServerInfo modServer = player.getServer().getInfo();
                if (!modServer.getName().equalsIgnoreCase(playerServer.getName())) {
                    player.connect(playerServer);
                }
                spectateServerMap.put(player, modServer);
                SpigotUtil.teleport(player, pp.getName());
            }
        }
    }

}
