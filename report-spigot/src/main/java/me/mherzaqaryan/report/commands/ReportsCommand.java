package me.mherzaqaryan.report.commands;

import me.mherzaqaryan.report.ReportPlugin;
import me.mherzaqaryan.report.menuSystem.menus.ManageReportMenu;
import me.mherzaqaryan.report.menuSystem.menus.ReportsMenu;
import me.mherzaqaryan.report.reportSystem.ReportState;
import me.mherzaqaryan.report.utility.TextUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (player.hasPermission("Reports.Admin")) {

            if (args.length == 0 || args.length == 1) {

                if (args.length == 0 || args[0].equalsIgnoreCase("Waiting")) {
                    new ReportsMenu(ReportPlugin.getPlayerMenuUtility(player), ReportState.WAITING).open();
                } else if (args[0].equalsIgnoreCase("inProgress")) {
                    new ReportsMenu(ReportPlugin.getPlayerMenuUtility(player), ReportState.IN_PROGRESS).open();
                } else if (args[0].equalsIgnoreCase("Closed")) {
                    new ReportsMenu(ReportPlugin.getPlayerMenuUtility(player), ReportState.CLOSED).open();
                } else {
                    return true;
                }

            } else if (args.length == 2) {
                int reportId;
                try {
                    reportId = Integer.parseInt(args[1]);
                    if (ReportPlugin.getInstance().getSQLData().reportExists(reportId)) {
                        if (args[0].equalsIgnoreCase("Watch")) {
                            ReportPlugin.getPlayerMenuUtility(player).setReportId(reportId);
                            ReportPlugin.getPlayerMenuUtility(player).setWatching(true);
                            new ManageReportMenu(ReportPlugin.getPlayerMenuUtility(player)).open();
                            return true;
                        } else if (args[0].equalsIgnoreCase("Close")) {
                            if (!ReportPlugin.getInstance().getSQLData().getReportById(reportId).getReportState().equals(ReportState.CLOSED)) {
                                ReportPlugin.getInstance().getSQLData().changeReportState(reportId, ReportState.CLOSED);
                                player.sendMessage(TextUtil.colorize("&aYou have successfully marked this report as closed, to see all closed reports type &7/reports closed"));
                            } else {
                                player.sendMessage(TextUtil.colorize("&cThis report already closed, if you want to watch type &7/reports view " + reportId));
                            }
                            return true;
                        } else if (args[0].equalsIgnoreCase("InProgress")) {
                            if (ReportPlugin.getInstance().getSQLData().getReportById(reportId).getReportState().equals(ReportState.CLOSED)) {
                                player.sendMessage(TextUtil.colorize("&cThis report already closed, if you want to watch it type &7/reports view " + reportId));
                            } else if (ReportPlugin.getInstance().getSQLData().getReportById(reportId).getReportState().equals(ReportState.IN_PROGRESS)) {
                                player.sendMessage(TextUtil.colorize("&cThis report already marked as in progress, if you want to close it type &7/report close " + reportId));
                            } else {
                                ReportPlugin.getInstance().getSQLData().changeReportState(reportId, ReportState.IN_PROGRESS);
                                player.sendMessage(TextUtil.colorize("&aYou have successfully marked this report as in progress, if you want to see all in progressed reports type &7/reports inprogress"));
                            }
                        } else {
                            player.sendMessage(TextUtil.colorize("&cSub-command doesn't exist, available sub-commands: watch (To watch the report), close (To close the report), (To mark the report in progress) inprogress"));
                        }
                    } else {
                        player.sendMessage(TextUtil.colorize("&cThere are no report with that id, if you want to check all reports type /reports"));
                        return true;
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(TextUtil.colorize("&cReport id must be integer"));
                    return true;
                }
            }

        }
        return true;
    }

}
