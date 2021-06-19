package me.mherzaqaryan.report.commands;

import me.mherzaqaryan.report.ReportPlugin;
import me.mherzaqaryan.report.menuSystem.PlayerMenuUtility;
import me.mherzaqaryan.report.menuSystem.menus.report.CheatTypeMenu;
import me.mherzaqaryan.report.menuSystem.menus.report.ReportMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpenReportCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("cheatingType")) {
                new CheatTypeMenu(ReportPlugin.getPlayerMenuUtility(player), args[0]).open();
                return true;
            }
            else if (args[1].equalsIgnoreCase("reportType")) {
                PlayerMenuUtility playerMenuUtility = ReportPlugin.getPlayerMenuUtility(player);
                new ReportMenu(playerMenuUtility, args[0]).open();
            }
        }

        return true;
    }

}
