package me.mherzaqaryan.report.commands;

import me.mherzaqaryan.report.reportSystem.CheatingType;
import me.mherzaqaryan.report.reportSystem.Report;
import me.mherzaqaryan.report.reportSystem.ReportType;
import me.mherzaqaryan.report.utility.TextUtil;
import me.mherzaqaryan.report.ReportPlugin;
import me.mherzaqaryan.report.data.ConfigUtil;
import me.mherzaqaryan.report.events.PlayerReportEvent;
import me.mherzaqaryan.report.utility.JsonUtil;
import me.mherzaqaryan.report.utility.PlayerUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class ReportCommand extends Command implements TabExecutor {

    public ReportCommand() {
        super("report");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) sender;
            if (args.length > 0) {
                String playerName = args[0];
//                if (player.getName().equalsIgnoreCase(playerName)) {
//                    TextUtil.sendMessage(player, ConfigUtil.SELF_REPORT);
//                    return;
//                }
                if (!PlayerUtil.isOnline(playerName)) {
                    TextUtil.sendMessage(player, ConfigUtil.PLAYER_OFFLINE.replace("%player%", playerName));
                    return;
                }
                ProxiedPlayer suspect = ProxyServer.getInstance().getPlayer(playerName);
                if (args.length == 1) {
                    sendReportMessage(player, suspect);
                }else if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("hacking")) {
                        sendCheatingTypeMessage(player, suspect);
                    }else if (args[1].equalsIgnoreCase("crossTeaming")) {
                        report(player, suspect, ReportType.CROSS_TEAMING);
                    }else if (args[1].equalsIgnoreCase("sweating")) {
                        report(player, suspect, ReportType.SWEATING);
                    }
                }else if (args.length == 3) {
                    report(player, suspect, CheatingType.getEnum(args[2]));
                }
            }else {
                TextUtil.sendMessage(player, ConfigUtil.REPORT_USAGE);
            }
        }
    }

    private void sendCheatingTypeMessage(ProxiedPlayer player, ProxiedPlayer suspect) {
        String sus = suspect.getName();
        TextComponent[] message = {
                JsonUtil.simple("&7&l&m------------------------"),
                JsonUtil.simple(" &eReporting &c&l" + suspect.getName() + " &eFor:"),
                JsonUtil.simple(""),
                JsonUtil.commandRun(" &c▬ &a&lKillaura", "&7Click to report!", "/report "+ sus +" Hacking Killaura"),
                JsonUtil.commandRun(" &c▬ &a&lFlight", "&7Click to report!", "/report "+ sus +" Hacking Flight"),
                JsonUtil.commandRun(" &c▬ &a&lScaffold", "&7Click to report!", "/report "+ sus +" Hacking Scaffold"),
                JsonUtil.commandRun(" &c▬ &a&lReaching", "&7Click to report!", "/report "+ sus +" Hacking Reaching"),
                JsonUtil.commandRun(" &c▬ &a&lSpeed", "&7Click to report!", "/report "+ sus +" Hacking Speed"),
                JsonUtil.commandRun(" &c▬ &a&lAnti-Knockback", "&7Click to report!", "/report "+ sus +" Hacking Antikb"),
                JsonUtil.commandRun(" &c▬ &a&lOther", "&7Click to report!", "/report "+ sus +" Hacking Other"),
                JsonUtil.simple(""),
                JsonUtil.simple("&7&l&m------------------------")
        };
        TextUtil.sendMessage(player, message);

    }

    private void sendReportMessage(ProxiedPlayer player, ProxiedPlayer suspect) {
        String sus = suspect.getName();
        TextComponent[] message = {
                JsonUtil.simple("&7&l&m------------------------"),
                JsonUtil.simple(" &eReporting &c&l" + suspect.getName() + " &eFor:"),
                JsonUtil.simple(""),
                JsonUtil.commandRun(" &c▬ &a&lHacking", "&7Click to choose hack type!", "/report "+sus+" Hacking"),
                JsonUtil.commandRun(" &c▬ &a&lTeaming", "&7Click to report for cross teaming!", "/report "+sus+" CrossTeaming"),
                JsonUtil.commandRun(" &c▬ &a&lSweating", "&7Click to report for sweating!", "/report "+sus+" Sweating"),
                JsonUtil.simple(""),
                JsonUtil.simple("&7&l&m------------------------")
        };
        TextUtil.sendMessage(player, message);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        ProxiedPlayer player = (ProxiedPlayer) sender;
        List<String> players = new ArrayList<>();
        ServerInfo server = player.getServer().getInfo();
        for (ProxiedPlayer pp : server.getPlayers()) {
            players.add(pp.getName());
        }
        return players;
    }

    public void report(ProxiedPlayer player, ProxiedPlayer suspect, ReportType reportType) {
        PlayerReportEvent event = new PlayerReportEvent(player, ReportPlugin.getInstance().getSQLData().getNextId());
        ReportPlugin.getInstance().getProxy().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            Report report = new Report(player.getUniqueId(), suspect.getUniqueId(), reportType);
            ReportPlugin.getInstance().getSQLData().addReport(report);
        }
    }

    public void report(ProxiedPlayer player, ProxiedPlayer suspect, CheatingType cheatingType) {
        PlayerReportEvent event = new PlayerReportEvent(player, ReportPlugin.getInstance().getSQLData().getNextId());
        ReportPlugin.getInstance().getProxy().getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            Report report = new Report(player.getUniqueId(), suspect.getUniqueId(), ReportType.CHEATING, cheatingType);
            ReportPlugin.getInstance().getSQLData().addReport(report);
        }
    }

}

