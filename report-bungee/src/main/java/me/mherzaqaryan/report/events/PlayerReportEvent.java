package me.mherzaqaryan.report.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

public class PlayerReportEvent extends Event implements Cancellable {

    private ProxiedPlayer reporter;
    private Integer reportId;
    private boolean isCancelled;


    public PlayerReportEvent(ProxiedPlayer reporter, Integer reportId) {
        this.reporter = reporter;
        this.reportId = reportId;
    }

    public Integer getReportId() {
        return reportId;
    }

    public ProxiedPlayer getReporter() {
        return reporter;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.isCancelled = b;
    }

}
