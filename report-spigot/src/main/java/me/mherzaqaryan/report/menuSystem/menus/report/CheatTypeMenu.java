package me.mherzaqaryan.report.menuSystem.menus.report;

import me.mherzaqaryan.report.menuSystem.Menu;
import me.mherzaqaryan.report.menuSystem.PlayerMenuUtility;
import me.mherzaqaryan.report.utility.ProxyUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CheatTypeMenu extends Menu {

    private String targetPlayer;

    public CheatTypeMenu(PlayerMenuUtility playerMenuUtility, String targetPlayer) {
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
        if (e.getSlot() == 10) {
            player.closeInventory();
            report(player, "killaura");
        }else if (e.getSlot() == 11) {
            player.closeInventory();
            report(player, "fly");
        }else if (e.getSlot() == 12) {
            player.closeInventory();
            report(player, "scaffold");
        }else if (e.getSlot() == 13) {
            player.closeInventory();
            report(player, "reach");
        }else if (e.getSlot() == 14) {
            player.closeInventory();
            report(player, "speed");
        }else if (e.getSlot() == 15) {
            player.closeInventory();
            report(player, "antikb");
        }else if (e.getSlot() == 16) {
            player.closeInventory();
            report(player, "other");
        }else if (e.getSlot() == 31) {
            player.closeInventory();
        }
    }

    @Override
    public void handleMenu(InventoryCloseEvent e) {
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, makeItem(Material.DIAMOND_SWORD, "&aKillaura", "&7Click here to report","&7for using killaura."));
        inventory.setItem(11, makeItem(Material.FEATHER, "&aFly", "&7Click here to report","&7for using fly hack."));
        inventory.setItem(12, makeItem(Material.WOOL, 1, 14, "&aScaffold", "&7Click here to report","&7for using scaffold hack."));
        inventory.setItem(13, makeItem(Material.BOW, "&aReach", "&7Click here to report","&7for using reach hack."));
        inventory.setItem(14, makeItem(Material.RABBIT_FOOT, "&aSpeed", "&7Click here to report","&7for using speed hack."));
        inventory.setItem(15, makeItem(Material.BARRIER, "&aAnti-knockback", "&7Click here to report","&7for using anti-knockback","&7hack."));
        inventory.setItem(16, makeItem(Material.WATER_LILY, "&aOther", "&7Click here to report","&7for using other type of hack."));

        inventory.setItem(31, makeItem(Material.ARROW, "&cCancel", "&7Click here to cancel this report."));
    }

    public void report(Player player, String type) {
        ProxyUtil.runCommand(player, "report "+targetPlayer+" cheating "+type);
    }

}
