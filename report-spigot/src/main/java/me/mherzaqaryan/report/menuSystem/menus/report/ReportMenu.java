package me.mherzaqaryan.report.menuSystem.menus.report;

import me.mherzaqaryan.report.menuSystem.Menu;
import me.mherzaqaryan.report.menuSystem.PlayerMenuUtility;
import me.mherzaqaryan.report.utility.ProxyUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class ReportMenu extends Menu {

    private String targetPlayer;
    public ReportMenu(PlayerMenuUtility playerMenuUtility, String targetPlayer) {
        super(playerMenuUtility);
        this.targetPlayer = targetPlayer;
    }

    @Override
    public String getMenuName() {
        return "Report: " + targetPlayer;
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (e.getSlot() == 11) {
            player.closeInventory();
            new CheatTypeMenu(playerMenuUtility, targetPlayer).open();
        }
        else if (e.getSlot() == 13) {
            player.closeInventory();
            report(player, "cross_teaming");
        }else if (e.getSlot() == 15) {
            player.closeInventory();
            report(player, "sweating");
        }else if (e.getSlot() == 31) {
            player.closeInventory();
        }
    }

    @Override
    public void handleMenu(InventoryCloseEvent e) {

    }

    @Override
    public void setMenuItems() {
        inventory.setItem(11, makeItem(Material.REDSTONE_COMPARATOR, "&aCheating", "&7Click here to report for ","&7cheating."));
        inventory.setItem(13, makeItem(Material.STRING, "&aCross Teaming", "&7Click here to report for ","&7cross teaming."));
        inventory.setItem(15, makeItem(Material.HOPPER, "&aSweating", "&7Click here to report for ","&7sweating."));
        inventory.setItem(31, makeItem(Material.ARROW, "&cCancel", "&7Click here to cancel this report."));
    }

    public void report(Player player, String type) {
        ProxyUtil.runCommand(player, "report "+targetPlayer+" "+type);
    }

}
