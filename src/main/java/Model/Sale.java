package Model;

import javafx.event.ActionEvent;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//sales department include create dataBase and the require functions
public class Sale extends AModel {
    private Connection conn;

    public Sale(){
        conn = SqlConnection.ConnectorSales();
        String query = "create table if not exists Sales (vacationId INTEGER ,userNameSale TEXT, userNameBuyer TEXT, datePurches TEXT,price TEXT)";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //insert to the data base new sale request from current user logged in of the vacation id
    public void create(ArrayList<String> allVacationDetails){
        Connection connection=SqlConnection.ConnectorVacation();
        String saleName="";
        String price="";
        String vacationID=allVacationDetails.get(0);
        String currentUser=allVacationDetails.get(1);
        String queryForGetDetailsVacation="SELECT * FROM Vacations WHERE vacationId="+ vacationID +"";
        //String queryForGetDetailsVacation="select count(*) as found from Vacations where vacationId=\\\"\"" +details.get(0) + "\"\\\" ";
        try {
            PreparedStatement pst = connection.prepareStatement(queryForGetDetailsVacation);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    saleName = rs.getString(1);
                    price=rs.getString(6);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LocalDate today = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String currentDate=today.format(dtf);
        Connection connectionSale=SqlConnection.ConnectorSales();
    //public void create(int vacationId, String userNameSale,String userNameBuyer,String Date,String creditCardNumber,String price){
        String query = "INSERT INTO Sales(vacationId, userNameSale, userNameBuyer, datePurches, price, requestForApproval, vacationPermit,sendApprovmentToBuyer) VALUES (?,?,?,?, ?,? ,?,?)";
        try {
            PreparedStatement pst = connectionSale.prepareStatement(query);
            pst.setString(1,vacationID);
            pst.setString(2,saleName);
            pst.setString(3,currentUser);
            pst.setString(4,currentDate);
            //pst.setString(5,details.get(1));
            pst.setString(5,price);
            pst.setInt(6,1);
            pst.setInt(7,0);
            pst.setInt(8,0);
            pst.execute();
            connectionSale.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //update current sale if is not a column of vacationId
    public void update(ArrayList<String> details){
    //public void update(String columnName, String vacationId, String newValue){
        if(!details.get(0).equals("vacationId")){
            String query = "update Sales set " + details.get(0) + "=" + "\"" + details.get(1) + "\" where vacationId=\"" + details.get(2) + "\"";
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //bring all the sales found in the sale's dataBase
    public void search(String details){
    //public void search(){
        String query = "select * from Sales";
        String Found = "";
        try {
            Statement pst = conn.createStatement();
            try (ResultSet rs = pst.executeQuery(query)) {
                ResultSet nrs = pst.executeQuery(query);
                for (int i=0;i<=6;i++)
                    Found = Found + nrs.getString(i);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete sale from the database, it is possible only if the buyer ask that
    public void delete(ArrayList<String> details){
        //public void delete(String userNameBuyer ,int vacationId){
        String query = "delete from Sales where( userNameBuyer=\"" + details.get(0) + "\"" +"and vacationId=\"" + details.get(1) + "\"";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> FoundRequetForPurchase(String currentUser){
        ArrayList<String[]> allPurchaseRequests = new ArrayList<>();
        String[] request;
        int i = 0;
        String query = "SELECT * FROM Sales WHERE requestForApproval='1' AND vacationPermit='0' AND userNameSale='" + currentUser + "'";
        try {
            Connection connectionSale=SqlConnection.ConnectorSales();
            Statement pst = connectionSale.createStatement();
            try (ResultSet rs = pst.executeQuery(query)) {
                while (rs.next()) {
                    request = new String[2];
                    request[0] = rs.getString(1);
                    request[1] =rs.getString(3);
                    allPurchaseRequests.add(i,request);
                    i++;
                }
            }
            connectionSale.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allPurchaseRequests;
    }

    public void declinePurchase(String[] details){
        String currentUser = details[0];
        String vacationIdForPurchase = details[1];
        String buyerUserName = details[2];
        String queryUpdate = "UPDATE Sales SET requestForApproval='0' WHERE vacationid = '" +vacationIdForPurchase + "' AND userNameBuyer = '" + buyerUserName + "' AND userNameSale='" + currentUser + "'";
        try {
            Connection connection = SqlConnection.ConnectorSales();
            PreparedStatement pst = connection.prepareStatement(queryUpdate);
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSaleDbRequestForvVacationPermit(String[] detailsToUpdate) {
        String currentUser = detailsToUpdate[0];
        String vacationForSale = detailsToUpdate[1];
        String buyer = detailsToUpdate[2];
        String queryUpdate = "UPDATE Sales SET vacationPermit='1' WHERE vacationid = '" +vacationForSale + "' AND userNameBuyer = '" + buyer + "' AND userNameSale='" + currentUser + "'";
        try {
            Connection connection = SqlConnection.ConnectorSales();
            PreparedStatement pst = connection.prepareStatement(queryUpdate);
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //returns array list with all confirmations of purchase of current user
    public ArrayList<String[]> queryApprove(String currentUser){
        ArrayList<String[]> allConfirmations = new ArrayList<>();
        String[] record;
        String queryApprove = "SELECT *  FROM Sales WHERE vacationPermit='1' AND userNameBuyer='" + currentUser + "' AND sendApprovmentToBuyer='0'";
        try {
            Connection connection = SqlConnection.ConnectorSales();
            Statement pst = connection.createStatement();
            try (ResultSet rs = pst.executeQuery(queryApprove)) {
                while (rs.next()) {
                    record = new String[3];
                    record[0] = rs.getString(1);
                    record[1] = rs.getString(2);
                    record[2] = rs.getString(4);
                    allConfirmations.add(record);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allConfirmations;
    }

    public void updateSaleDbRequestForSendApprovmentToBuyer(String currentUser) {
        String queryUpdate = "UPDATE Sales SET sendApprovmentToBuyer='1' WHERE userNameBuyer='" + currentUser + "'";
        try {
            Connection connection = SqlConnection.ConnectorSales();
            PreparedStatement pst1 = connection.prepareStatement(queryUpdate);
            pst1.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSaleDbRequestForApproval(String currentUser) {
            String queryUpdate = "UPDATE Sales SET requestForApproval='0' WHERE userNameSale='" + currentUser + "'";
            try {
                Connection connection = SqlConnection.ConnectorSales();
                PreparedStatement pst2 = connection.prepareStatement(queryUpdate);
                pst2.execute();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }


    //returns all user purchase declines
    public ArrayList<String[]> getAllPurchaseDeclines(String currentUser) {
        ArrayList<String[]> allDeclines = new ArrayList<>();
        String[] record;
        String queryApprove = "SELECT *  FROM Sales WHERE requestForApproval = '0' AND vacationPermit='0' AND userNameBuyer='" + currentUser + "' AND sendApprovmentToBuyer='0'";
        try {
            Connection connection = SqlConnection.ConnectorSales();
            Statement pst = connection.createStatement();
            try (ResultSet rs = pst.executeQuery(queryApprove)) {
                while (rs.next()) {
                    record = new String[3];
                    record[0] = rs.getString(1);
                    record[1] = rs.getString(2);
                    record[2] = rs.getString(4);
                    allDeclines.add(record);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allDeclines;
    }

    public void updateDeclinePurchaseSentToBuyer(String currentUser) {
        String queryUpdate = "UPDATE Sales SET sendApprovmentToBuyer='1' WHERE vacationPermit='0' AND requestForApproval='0' AND userNameBuyer='" + currentUser + "'";
        try {
            Connection connection = SqlConnection.ConnectorSales();
            PreparedStatement pst1 = connection.prepareStatement(queryUpdate);
            pst1.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}