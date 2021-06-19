package me.mherzaqaryan.report.menuSystem;

import me.mherzaqaryan.report.reportSystem.ReportState;
import org.bukkit.entity.Player;

public class PlayerMenuUtility {

    private Player player;
    private Integer reportId;
    private ReportState reportState;
    private boolean isWatching;

    public PlayerMenuUtility(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public ReportState getReportState() {
        return reportState;
    }

    public void setReportState(ReportState reportState) {
        this.reportState = reportState;
    }

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatching(boolean watching) {
        isWatching = watching;
    }

}
