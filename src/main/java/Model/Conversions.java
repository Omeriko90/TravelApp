package Model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Conversions extends AModel {
    private Connection conn;

    public Conversions() {
        conn = SqlConnection.ConnectorConversions();
        String query = "create table if not exists Conversions (vacationIdSalesperson INTEGER , vacationIdBuyer INTEGER ,userNameSalesperson TEXT, userNameBuyer TEXT," +
                " purchaseDate TEXT,requestForApproval TEXT ,requestConfirmation TEXT, sendApproveToBuyer TEXT)";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(ArrayList<String> details) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currentDate = today.format(dtf);
        Connection connectorConversions = SqlConnection.ConnectorConversions();
        //public void create(int vacationId, String userNameSale,String userNameBuyer,String Date,String creditCardNumber,String price){
        String query = "INSERT INTO Conversions(vacationIdSalesperson, vacationIdBuyer, userNameSalesperson, userNameBuyer,purchaseDate, requestForApproval, requestConfirmation,sendApproveToBuyer) VALUES (?,?,?,?, ?,? ,?,?)";
        try {
            PreparedStatement pst = connectorConversions.prepareStatement(query);
            pst.setString(1, details.get(0));
            pst.setString(2, details.get(3));
            pst.setString(3, details.get(1));
            pst.setString(4, details.get(2));
            pst.setString(5, currentDate);
            pst.setInt(6, 1);
            pst.setInt(7, 0);
            pst.setInt(8, 0);
            pst.execute();
            connectorConversions.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void search(String details) {

    }

    @Override
    public void update(ArrayList<String> details) {

    }

    //in case that the sale person decline conversion
    @Override
    public void delete(ArrayList<String> details) {


    }

    //in case that saller decline conversion we change requestforapproval to 0 and request confirmation to 0
    public void declineConversion(String[] details) {
        String saleUserName = details[0];
        String salevacationID = details[1];
        String buyerVacationID = details[2];
        String queryUpdate = "UPDATE Conversions SET requestForApproval='0' WHERE vacationIdSalesperson = '" + salevacationID + "' AND vacationIdBuyer = '" + buyerVacationID + "' AND  userNameSalesperson='" + saleUserName + "'";
        try {
            Connection connection = SqlConnection.ConnectorConversions();
            PreparedStatement pst = connection.prepareStatement(queryUpdate);
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //getting all new requests for Conversions of current user
    public ArrayList<String[]> getAllRequetsForConversions(String currentUser) {
        ArrayList<String[]> allRequestsForConversions = new ArrayList<>();
        int i = 0;
        String query = "SELECT * FROM Conversions WHERE requestForApproval='1' AND requestConfirmation = '0' AND userNameSalesperson='" + currentUser + "'";
        try {
            Connection connectionSale = SqlConnection.ConnectorConversions();
            Statement pst = connectionSale.createStatement();
            try (ResultSet rs = pst.executeQuery(query)) {
                while (rs.next()) {
                    String[] request = new String[3];
                    request[0] = (rs.getString(1));
                    request[1] = (rs.getString(2));
                    request[2] = (rs.getString(4));
                    allRequestsForConversions.add(i, request);
                    i++;
                }
            }
            connectionSale.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRequestsForConversions;
    }

    //update requestconfirmation to 1 in conversions table
    public void updateRequestConfirmation(String[] details) {
        String currentUser = details[0];
        String vacationID = details[1];
        String vacationIdReplacedBy = details[2];

        String queryUpdate = "UPDATE Conversions SET requestConfirmation='1' WHERE vacationIdSalesperson = '" + vacationID + "' AND vacationIdBuyer = '" + vacationIdReplacedBy + "' AND  userNameSalesperson='" + currentUser + "'";
        try {
            Connection connection = SqlConnection.ConnectorConversions();
            PreparedStatement pst = connection.prepareStatement(queryUpdate);
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //returns array list with all confirmations For Conversions of current user
    public ArrayList<String[]> getAllConversionsApproved(String currentUser) {
        ArrayList<String[]> allConfirmations = new ArrayList<>();
        String[] record;
        String queryApprove = "SELECT *  FROM Conversions WHERE requestConfirmation='1' AND userNameBuyer='" + currentUser + "' AND sendApproveToBuyer='0'";
        try {
            Connection connection = SqlConnection.ConnectorConversions();
            Statement pst = connection.createStatement();
            try (ResultSet rs = pst.executeQuery(queryApprove)) {
                while (rs.next()) {
                    record = new String[4];
                    record[0] = rs.getString(1);
                    record[1] = rs.getString(2);
                    record[2] = rs.getString(3);
                    record[3] = rs.getString(5);
                    allConfirmations.add(record);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allConfirmations;
    }

    //update the send approve to buyer column to 1
    public void updateThatConfirmationSentToBuyer(String currentUser) {
        String queryUpdate = "UPDATE conversions SET sendApproveToBuyer='1' WHERE requestConfirmation='1' AND userNameBuyer='" + currentUser + "'";
        try {
            Connection connection = SqlConnection.ConnectorConversions();
            PreparedStatement pst1 = connection.prepareStatement(queryUpdate);
            pst1.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //returns all declines from db
    public ArrayList<String[]> getAllConversionsDeclined(String currentUser) {
        ArrayList<String[]> allNewDeclines = new ArrayList<>();
        String[] record;
        String queryApprove = "SELECT *  FROM Conversions WHERE requestConfirmation='0' AND requestForApproval = '0' AND  userNameBuyer='" + currentUser + "' AND sendApproveToBuyer='0'";
        try {
            Connection connection = SqlConnection.ConnectorConversions();
            Statement pst = connection.createStatement();
            try (ResultSet rs = pst.executeQuery(queryApprove)) {
                while (rs.next()) {
                    record = new String[4];
                    record[0] = rs.getString(1);
                    record[1] = rs.getString(2);
                    record[2] = rs.getString(3);
                    record[3] = rs.getString(5);
                    allNewDeclines.add(record);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allNewDeclines;
    }

    public void updateDeclineConversionsSentToBuyer(String currentUser) {
        String queryUpdate = "UPDATE conversions SET sendApproveToBuyer='1' WHERE requestConfirmation='0' AND requestForApproval='0' AND userNameBuyer='" + currentUser + "'";
        try {
            Connection connection = SqlConnection.ConnectorConversions();
            PreparedStatement pst1 = connection.prepareStatement(queryUpdate);
            pst1.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
