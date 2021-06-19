package me.mherzaqaryan.report.menuSystem.menus;


import me.mherzaqaryan.report.ReportPlugin;
import me.mherzaqaryan.report.menuSystem.Menu;
import me.mherzaqaryan.report.menuSystem.PlayerMenuUtility;
import me.mherzaqaryan.report.reportSystem.Report;
import me.mherzaqaryan.report.reportSystem.ReportState;
import me.mherzaqaryan.report.utility.ProxyUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ManageReportMenu extends Menu {

    private final Report report = ReportPlugin.getInstance().getSQLData().getReportById(playerMenuUtility.getReportId());

    public ManageReportMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "&7Report #" + playerMenuUtility.getReportId();
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == 36) {
            if (!playerMenuUtility.isWatching()) {
                new ReportsMenu(playerMenuUtility, playerMenuUtility.getReportState()).open();
            } else {
                player.closeInventory();
            }
        }else if (e.getSlot() == 19) {
            player.closeInventory();
            ProxyUtil.runCommand(player, "spectate " + report.getReporterName());
        }else if (e.getSlot() == 20) {
            player.closeInventory();
            ProxyUtil.runCommand(player, "spectate " + report.getReportedName());
        }
        else if (e.getSlot() == 24) {
            if (report.getReportState().equals(ReportState.WAITING)) {
                new ConfirmMenu(ReportPlugin.getPlayerMenuUtility(player), false).open();
            }
        }
        else if (e.getSlot() == 25) {
            if (!report.getReportState().equals(ReportState.CLOSED)) {
                new ConfirmMenu(ReportPlugin.getPlayerMenuUtility(player), true).open();
            }
        }
    }

    @Override
    public void handleMenu(InventoryCloseEvent e) {

    }

    @Override
    public void setMenuItems() {

        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, LIGHT_GRAY_GLASS_PANE);
        }
        for (int i = 37; i < 45; i++) {
            inventory.setItem(i, LIGHT_GRAY_GLASS_PANE);
        }

        String[] reporterLore = {
            "",
            "&eName: &7" + report.getReporterName(),
            "&eUUID:",
            "&7" + report.getReporterUuid().toString(),
            "",
            "&7Click to teleport to player"
        };

        String[] reportedLore = {
            "",
            "&eName: &7" + report.getReportedName(),
            "&eUUID:",
            "&7" + report.getReportedUuid().toString(),
            "",
            "&7Click to teleport to player"
        };

        String[] reportLore = {
            "",
            "&aReport ID: &c" + report.getReportId(),
            "&aReport Type: &c" + report.getReportType(),
            "&aCheating Type: &c" + report.getCheatingType(),
            "&aReport Server: &c" + report.getReportServer(),
            "&aReport State: &c" + report.getReportStateName(),
            "&eReport Date: &a" + report.getReportDate(),
            "&eIn Progress Date: &a" + (report.getReportState().equals(ReportState.CLOSED) ? report.getClosedDate()  :  report.getInProgressDate()),
            "&eClosed Date: &a" + report.getClosedDate(),
        };

        String[] inProgressLore = {
            "",
            "&7Click here to mark this report",
            "&7as in progress.",
            "",
            "&cIf you mark this report as in progress",
            "&cyou cant change it back anymore."
        };

        String[] alreadyInProgressLore = {
            "",
            "&cThis report already marked as in",
            "&cprogress"
        };

        String[] closedReport = {
            "",
            "&cThis report is closed."
        };

        String[] closeLore = {
            "",
            "&7Click here to mark this report",
            "&7as closed",
            "",
            "&cIf you mark this report as closed",
            "&cyou cant open it back anymore."
        };


        inventory.setItem(19, makeHead(report.getReporterName(), "&aReporter Information", reporterLore));
        inventory.setItem(20, makeHead(report.getReportedName(), "&cReported Information", reportedLore));

        inventory.setItem(22, makeItem(Material.PAPER, "&cReport Information", reportLore));

        if (report.getReportState().equals(ReportState.IN_PROGRESS)) {
            inventory.setItem(24, makeItem(Material.STAINED_GLASS_PANE, 1, 14, "&eMark As In Progress", alreadyInProgressLore));
            inventory.setItem(25, makeItem(Material.STAINED_CLAY, 1, 14, "&cMark As Closed", closeLore));
        }else if (report.getReportState().equals(ReportState.CLOSED)) {
            inventory.setItem(24, makeItem(Material.STAINED_GLASS_PANE, 1, 14, "&eMark As In Progress", closedReport));
            inventory.setItem(25, makeItem(Material.STAINED_GLASS_PANE, 1, 14, "&cMark As Closed", closedReport));
        }else {
            inventory.setItem(24, makeItem(Material.STAINED_CLAY, 1, 5, "&eMark As In Progress", inProgressLore));
            inventory.setItem(25, makeItem(Material.STAINED_CLAY, 1, 14, "&cMark As Closed", closeLore));
        }

        inventory.setItem(36, !playerMenuUtility.isWatching() ? makeItem(Material.ARROW, "&cBack") : makeItem(Material.BARRIER, "&cClose"));

    }

    public String getStatus(String player) {
        if (isOnline(player)) {
            return "&aOnline";
        }else {
            return "&cOffline";
        }
    }


    public boolean isOnline(String player) {
        boolean isOnline = false;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(player)) {
                isOnline = true;
                break;
            }
        }
        return isOnline;
    }

}
