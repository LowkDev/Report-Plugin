package me.mherzaqaryan.report.sql;

import me.mherzaqaryan.report.reportSystem.Report;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLData {

    private final MySQL mySQL;

    public SQLData(MySQL mySQL) {
        this.mySQL = mySQL;
        createTable();
    }

    public void createTable() {
        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS reports_data (" +
                    "ReportId INT(100) AUTO_INCREMENT," +
                    "ReporterUUID VARCHAR(64)," +
                    "ReporterName VARCHAR(32)," +
                    "ReportedUUID VARCHAR(64)," +
                    "ReportedName VARCHAR(32)," +
                    "ReportType VARCHAR(16)," +
                    "CheatingType VARCHAR(16)," +
                    "ReportState VARCHAR(16)," +
                    "ReportServer VARCHAR(16)," +
                    "ReportDate VARCHAR(64)," +
                    "InProgressDate VARCHAR(64)," +
                    "ClosedDate VARCHAR(64)," +
                    "PRIMARY KEY (ReportId))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addReport(Report report) {

        String cheatingType = null;
        String reporterUuid = report.getReporterUuid().toString();
        String reporterName = report.getReporterName();
        String reportedUuid = report.getReportedUuid().toString();
        String reportedName = report.getReportedName();
        String reportType = report.getReportType().toString();
        String reportServer = report.getReportServer();

        if (report.isCheating()) {
            cheatingType = report.getCheatingType().toString();
        }

        String reportDate = report.getReportDate();
        String reportState = report.getReportState().toString();

        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("INSERT INTO reports_data (" +
                    "ReporterUUID," +
                    "ReporterName," +
                    "ReportedUUID," +
                    "ReportedName," +
                    "ReportType," +
                    "CheatingType," +
                    "ReportState," +
                    "ReportServer," +
                    "ReportDate," +
                    "InProgressDate," +
                    "ClosedDate" +
                    ") VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, reporterUuid);
            ps.setString(2, reporterName);
            ps.setString(3, reportedUuid);
            ps.setString(4, reportedName);
            ps.setString(5, reportType);
            ps.setString(6, cheatingType);
            ps.setString(7, reportState);
            ps.setString(8, reportServer);
            ps.setString(9, reportDate);
            ps.setString(10, null);
            ps.setString(11, null);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer getLastId() {
        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("SELECT MAX(ReportId) FROM reports_data");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Integer getNextId() {
        return getLastId()+1;
    }

}
