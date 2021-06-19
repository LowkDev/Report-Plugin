package me.mherzaqaryan.report.reportSystem;

import net.md_5.bungee.api.ProxyServer;
import me.mherzaqaryan.report.ReportPlugin;

import java.time.LocalDateTime;
import java.util.UUID;

public class Report {

    private boolean isCheating = false;

    private final UUID reporterUuid;
    private final String reporterName;

    private final UUID reportedUuid;
    private final String reportedName;

    private String reportServer;

    private final String reportDate;

    private final ReportType reportType;
    private CheatingType cheatingType = null;
    private final ReportState reportState = ReportState.WAITING;

    public Report(UUID reporterUuid, UUID reportedUuid, ReportType reportType) {
        this.reporterUuid = reporterUuid;
        this.reporterName = ProxyServer.getInstance().getPlayer(reporterUuid).getName();
        this.reportedUuid = reportedUuid;
        this.reportedName = ProxyServer.getInstance().getPlayer(reportedUuid).getName();
        this.reportType = reportType;
        this.reportServer = ProxyServer.getInstance().getPlayer(reporterUuid).getServer().getInfo().getName();
        this.reportDate = ReportPlugin.getDtf().format(LocalDateTime.now());
    }

    public Report(UUID reporterUuid, UUID reportedUuid, ReportType reportType, CheatingType cheatingType) {
        this.reporterUuid = reporterUuid;
        this.reporterName = ProxyServer.getInstance().getPlayer(reporterUuid).getName();
        this.reportedUuid = reportedUuid;
        this.reportedName = ProxyServer.getInstance().getPlayer(reportedUuid).getName();
        this.reportType = reportType;
        this.reportDate = ReportPlugin.getDtf().format(LocalDateTime.now());
        this.isCheating = true;
        this.cheatingType = cheatingType;
        this.reportServer = ProxyServer.getInstance().getPlayer(reporterUuid).getServer().getInfo().getName();
    }

    public ReportType getReportType() {
        return reportType;
    }

    public UUID getReporterUuid() {
        return reporterUuid;
    }

    public String getReporterName() {
        return reporterName;
    }

    public UUID getReportedUuid() {
        return reportedUuid;
    }

    public String getReportedName() {
        return reportedName;
    }

    public String getReportDate() {
        return reportDate;
    }

    public CheatingType getCheatingType() {
        return cheatingType;
    }

    public ReportState getReportState() {
        return reportState;
    }

    public boolean isCheating() {
        return isCheating;
    }

    public String getReportServer() {
        return reportServer;
    }
}
