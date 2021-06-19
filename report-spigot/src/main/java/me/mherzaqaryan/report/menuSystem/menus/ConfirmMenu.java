package me.mherzaqaryan.report.menuSystem.menus;

import me.mherzaqaryan.report.ReportPlugin;
import me.mherzaqaryan.report.menuSystem.Menu;
import me.mherzaqaryan.report.menuSystem.PlayerMenuUtility;
import me.mherzaqaryan.report.reportSystem.ReportState;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ConfirmMenu extends Menu {

    private boolean isClose;

    public ConfirmMenu(PlayerMenuUtility playerMenuUtility, boolean isClose) {
        super(playerMenuUtility);
        this.isClose = isClose;
    }

    @Override
    public String getMenuName() {
        return "Are You Sure ?";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == 11) {
            ReportPlugin.getPlayerMenuUtility(player).setWatching(false);
            new ManageReportMenu(ReportPlugin.getPlayerMenuUtility(player)).open();
        }else if (e.getSlot() == 15) {
            ReportPlugin.getInstance().getSQLData().changeReportState(playerMenuUtility.getReportId(), isClose ? ReportState.CLOSED : ReportState.IN_PROGRESS);
            ReportPlugin.getPlayerMenuUtility(player).setWatching(false);
            new ManageReportMenu(ReportPlugin.getPlayerMenuUtility(player)).open();
        }
    }

    @Override
    public void handleMenu(InventoryCloseEvent e) {

    }

    @Override
    public void setMenuItems() {
        setFillerGlass();
        inventory.setItem(11, makeItem(Material.STAINED_CLAY, 1,  14, "&cNo", "","&7Click here to deny"));
        inventory.setItem(15, makeItem(Material.STAINED_CLAY, 1,  5, "&aYes", "","&7Click here to accept"));
    }

}
