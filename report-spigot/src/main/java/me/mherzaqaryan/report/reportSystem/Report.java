package me.mherzaqaryan.report.reportSystem;


import me.mherzaqaryan.report.utility.TextUtil;

import java.util.UUID;

public class Report {

    int reportId;
    UUID reporterUuid;
    String reporterName;
    UUID reportedUuid;
    String reportedName;
    ReportType reportType;
    CheatingType cheatingType;
    ReportState reportState;
    String reportServer, reportDate, inProgressDate, closedDate;

    public Report(int reportId,
                  UUID reporterUuid,
                  String reporterName,
                  UUID reportedUuid,
                  String reportedName,
                  ReportType reportType,
                  CheatingType cheatingType,
                  ReportState reportState,
                  String reportServer,
                  String reportDate,
                  String inProgressDate,
                  String closedDate) {
        this.reportId = reportId;
        this.reporterUuid = reporterUuid;
        this.reporterName = reporterName;
        this.reportedUuid = reportedUuid;
        this.reportedName = reportedName;

        this.reportType = reportType;
        this.cheatingType = cheatingType;
        this.reportState = reportState;
        this.reportServer = reportServer;
        this.reportDate = reportDate;
        this.inProgressDate = inProgressDate;
        this.closedDate = closedDate;
    }

    public int getReportId() {
        return reportId;
    }

    public String getReportDate() {
        return reportDate;
    }

    public UUID getReportedUuid() {
        return reportedUuid;
    }

    public String getReportedName() {
        return reportedName;
    }

    public String getReporterName() {
        return reporterName;
    }

    public UUID getReporterUuid() {
        return reporterUuid;
    }

    public String getReportStateName() {
        if (reportState.equals(ReportState.WAITING)) {
            return "Waiting";
        }else if (reportState.equals(ReportState.IN_PROGRESS)) {
            return "In Progress";
        }else {
            return "Closed";
        }
    }

    public ReportState getReportState() {
        return this.reportState;
    }

    public String getReportType() {
        if (reportType.equals(ReportType.CHEATING)) {
            return "Cheating";
        }else if (reportType.equals(ReportType.CROSS_TEAMING)) {
            return "Cross Teaming";
        }else {
            return "Sweating";
        }
    }

    public String getCheatingType() {
        if (this.cheatingType == null) {
            return "None";
        }else {
            return TextUtil.toUppercaseFirstChar(this.cheatingType.toString());
        }
    }

    public String getClosedDate() {
        if (this.closedDate == null) {
            return "Not Closed";
        }else {
            return this.closedDate;
        }
    }

    public String getInProgressDate() {
        if (this.inProgressDate == null) {
            return "Not In Progress";
        }else {
            return this.inProgressDate;
        }
    }

    public String getReportServer() {
        return this.reportServer;
    }

}
