package me.mherzaqaryan.report.sql;

import me.mherzaqaryan.report.ReportPlugin;
import me.mherzaqaryan.report.reportSystem.CheatingType;
import me.mherzaqaryan.report.reportSystem.Report;
import me.mherzaqaryan.report.reportSystem.ReportState;
import me.mherzaqaryan.report.reportSystem.ReportType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SQLData {

    private final MySQL mySQL;

    public SQLData(MySQL mySQL) {
        this.mySQL = mySQL;
    }

    public List<Report> getReports() {
        List<Report> reports = new ArrayList<>();
        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("SELECT * FROM reports_data");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CheatingType cheatingType = null;
                int reportId = rs.getInt("ReportId");
                UUID reporterUuid = UUID.fromString(rs.getString("ReporterUUID"));
                String reporterName = rs.getString("ReporterName");
                UUID reportedUuid = UUID.fromString(rs.getString("ReportedUUID"));
                String reportedName = rs.getString("ReportedName");
                ReportType reportType = ReportType.valueOf(rs.getString("ReportType"));
                if (rs.getString("CheatingType") != null) {
                    cheatingType = CheatingType.valueOf(rs.getString("CheatingType"));
                }
                String reportServer = rs.getString("ReportServer");
                String reportDate = rs.getString("ReportDate");
                String inProgressDate = rs.getString("InProgressDate");
                ReportState reportState = ReportState.valueOf(rs.getString("ReportState"));
                String closedDate = rs.getString("ClosedDate");
                reports.add(new Report(reportId, reporterUuid, reporterName, reportedUuid, reportedName, reportType, cheatingType, reportState, reportServer, reportDate, inProgressDate, closedDate));
            }
            return reports;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Report getReportById(Integer id) {
        for (Report report : getReports()) {
            if (report.getReportId() == id) {
                return report;
            }
        }
        return null;
    }

    public boolean reportExists(Integer id) {
        boolean reportExists = false;
        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("SELECT * FROM reports_data WHERE ReportId=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reportExists = true;
            }
            return reportExists;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reportExists;
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

    public void changeReportState(Integer reportId, ReportState reportState) {

        try {

            PreparedStatement ps = mySQL.getConnection().prepareStatement("UPDATE reports_data SET ReportState=? WHERE ReportId=?");
            ps.setString(1, reportState.toString());
            ps.setInt(2, reportId);
            ps.executeUpdate();

            if (reportState.equals(ReportState.CLOSED)) {
                setClosedDate(reportId);
                if (!getReportById(reportId).getReportState().equals(ReportState.IN_PROGRESS)) {
                    setInProgressDate(reportId);
                }
            }else {
                setInProgressDate(reportId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void setClosedDate(Integer reportId) {
        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("UPDATE reports_data SET ClosedDate=? WHERE ReportId=?");
            ps.setString(1, ReportPlugin.getDtf().format(LocalDateTime.now()));
            ps.setInt(2, reportId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setInProgressDate(Integer reportId) {
        try {
            PreparedStatement ps = mySQL.getConnection().prepareStatement("UPDATE reports_data SET InProgressDate=? WHERE ReportId=?");
            ps.setString(1, ReportPlugin.getDtf().format(LocalDateTime.now()));
            ps.setInt(2, reportId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
