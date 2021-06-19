package me.mherzaqaryan.report.menuSystem.menus;

import me.mherzaqaryan.report.ReportPlugin;
import me.mherzaqaryan.report.menuSystem.PaginatedMenu;
import me.mherzaqaryan.report.menuSystem.PlayerMenuUtility;
import me.mherzaqaryan.report.reportSystem.Report;
import me.mherzaqaryan.report.reportSystem.ReportState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class ReportsMenu extends PaginatedMenu {

    private final List<Report> reports;
    private final HashMap<ItemStack, Integer> reportMap = new HashMap<>();

    public ReportsMenu(PlayerMenuUtility playerMenuUtility, ReportState reportState) {
        super(playerMenuUtility);
        this.reports = ReportPlugin.getInstance().getSQLData().getReports();
        reports.removeIf(report -> !report.getReportState().equals(reportState));
        playerMenuUtility.setReportState(reportState);
    }

    private String page() {
        return getPage();
    }

    @Override
    public String getMenuName() {
        return "&7Reports Menu " + page();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = playerMenuUtility.getPlayer();
        if (e.getSlot() == 49) {
            player.closeInventory();
        }
        else if (e.getSlot() == 18) {
            if (page != 0) {
                page = page - 1;
                super.open();
            }
        }
        else if (e.getSlot() == 26) {
            assert reports != null;
            if (!((index + 1) >= reports.size())) {
                page = page + 1;
                super.open();
            }
        }else if (e.getCurrentItem().getType().equals(Material.PAPER)) {
            playerMenuUtility.setReportId(getReportId(e.getCurrentItem()));
            ReportPlugin.getPlayerMenuUtility(player).setWatching(false);
            new ManageReportMenu(ReportPlugin.getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void handleMenu(InventoryCloseEvent e) {

    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        if(!reports.isEmpty()) {
            for (int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if (index >= reports.size()) break;
                if (reports.get(index) != null) {
                    Report report = reports.get(index);
                    String displayName = "&eReport #" + report.getReportId();
                    String[] lore = {
                            "",
                            "&aReporter Name: &7" + report.getReporterName(),
                            "&cReported Name: &7" + report.getReportedName(),
                            "",
                            "&eReport Type: &a" + report.getReportType(),
                            "&eCheating Type: &a" + report.getCheatingType(),
                            "&eReport State: &a" + report.getReportStateName(),
                            "&eReport Server: &a" + report.getReportServer(),
                            "&eReport Date: &a" + report.getReportDate(),
                            "&eIn Progress Date: &a" + (report.getReportState().equals(ReportState.CLOSED) ? report.getClosedDate() : report.getInProgressDate()),
                            "&eClosed Date: &a" + report.getClosedDate(),
                    };
                    ItemStack item = makeItem(Material.PAPER, displayName, lore);
                    reportMap.put(item, report.getReportId());
                    inventory.addItem(item);
                }
            }
        }

    }

    public Integer getReportId(ItemStack itemStack) {
        return reportMap.get(itemStack);
    }

    private String getPage() {
        assert reports != null;
        return (page + 1) + "/" + (int) Math.ceil(reports.size() / maxItemsPerPage + 1);
    }

}
