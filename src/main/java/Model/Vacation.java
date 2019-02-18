package Model;

import Controller.Controller;

import java.sql.*;
import java.util.ArrayList;

//vacation department include create dataBase and the require functions
public class Vacation extends AModel {
    //vacation id, we update every creating new vacation
    public static int vacationId = 1;
    private ArrayList<String[]> outPutSearch = null;
    private ArrayList<String[]> outAllVacationDetails = new ArrayList<>();
    private Connection connection;

    public void vacationID() {
        connection = SqlConnection.ConnectorVacation();
        String query = "SELECT MAX(vacationId) FROM Vacations;";
        String UserFound = "";
        try {
            Statement pst = connection.createStatement();
            try (ResultSet nrs = pst.executeQuery(query)) {
                UserFound = nrs.getString(1);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(UserFound != null) {
            vacationId = Integer.valueOf(UserFound) + 1;
        }else {
            vacationId = 1;
        }

    }

    public Vacation() {
        connection = SqlConnection.ConnectorVacation();
        //create new dataBase of vacations if it doesn't exists
        // SQL statement for creating a new table
        String query = "CREATE TABLE IF NOT EXISTS Vacations (\n"
                + "	userName text ,\n"
                + "	vacationId integer PRIMARY KEY,\n"
                + "	fromDate text,\n"
                + "	toDate text,\n"
                + "	destination text,\n"
                + "	price text,\n"
                + "	flight text,\n"
                + "	includeHotel text,\n"
                + "	includeBag text,\n"
                + "	numOfTicket text,\n"
                + "	typeSeler text,\n"
                + "	vacationKind text,\n"
                + "	hotelRate text\n"
                + "	fromCountry text\n"
                + ");";
        //String query = "create table Vacations (userName TEXT, vacationId INTEGER,fromDate TEXT,toDate TEXT,destination TEXT,price TEXT," +
        //  "flightCompany TEXT, includeHotel TEXT, includeBag TEXT,numOfTicket TEXT, hotelRate TEXT, vacationKind TEXT, typeSeler TEXT);";
        try {
            Statement stmt = connection.createStatement();
            stmt.execute(query);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vacationID();
    }

    //insert to the data base new vacation
    public void create(ArrayList<String> details) {
        connection = SqlConnection.ConnectorVacation();
        //public void create(String userName,String fromDate,String toDate,String destination,String price, String flightCompany, String includeHotel, String includeBags, String numberOfTicket, String HotelRate, String vacationKind){
        //String query = "insert into Vacations(userName, vacationId, fromDate, toDate, destination, price, flight, includeHotel, includeBag, numOfTicket, vacationKind, typeSeler, hotelRate) values(details.get(11),vacationId ,details.get(0),details.get(1),details.get(2),details.get(3), details.get(4),details.get(5), details.get(6), details.get(7) ,details.get(9), details.get(10), details.get(8))";
        String query = "insert into Vacations(USERNAME, VACATIONID, FROMDATE, TODATE, DESTINATION, PRICE, FLIGHT, INCLUDEHOTEL, INCLUDEBAG, NUMOFTICKET, VACATIONKIND, TYPESELER, HOTELRATE, FROMCOUNTRY ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, details.get(12));
            pst.setInt(2, vacationId);
            pst.setString(3, details.get(0));
            pst.setString(4, details.get(1));
            pst.setString(5, details.get(2));
            pst.setString(6, details.get(3));
            pst.setString(7, details.get(4));
            pst.setString(8, details.get(5));
            pst.setString(9, details.get(6));
            pst.setString(10, details.get(7));
            pst.setString(11, details.get(9));
            pst.setString(12, details.get(10));
            pst.setString(13, details.get(8));
            pst.setString(14, details.get(11));
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        vacationId++;
    }

    //update current vacation if is not a column of vacationId
    public void update(ArrayList<String> details) {
        //public void update(String columnName, String userName, String newValue){
        if (!details.get(0).equals("vacationId")) {
            String query = "update Vacations set " + details.get(0) + "=" + "\"" + details.get(1) + "\" where Username=\"" + details.get(2) + "\"";
            try {
                PreparedStatement pst = connection.prepareStatement(query);
                pst.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //delete vacation from the database
    //public void delete(String userName,int vacationId){
    public void delete(ArrayList<String> details) {
        Connection connection = SqlConnection.ConnectorVacation();
        String query = "delete from Vacations where vacationId='" + (String) details.get(0) + "'";

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.execute();
            connection.close();
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    //bring all the details from the db vacation
    public void search(String columnName) {
        Connection connection = SqlConnection.ConnectorVacation();
        outPutSearch = new ArrayList<>();
        int i = 0;
        String query = "select * from Vacations";
        try {
            Statement pst = connection.createStatement();
            try (ResultSet rs = pst.executeQuery(query)) {
                ResultSet nrs = pst.executeQuery(query);
                while (nrs.next()) {
                    String[] record = new String[13];
                    record[0] = nrs.getString("USERNAME");
                    record[1] = Integer.toString(nrs.getInt("VACATIONID"));
                    record[2] = nrs.getString("FROMDATE");
                    record[3] = nrs.getString("TODATE");
                    record[4] = nrs.getString("DESTINATION");
                    record[5] = nrs.getString("PRICE");
                    record[6] = nrs.getString("FLIGHT");
                    record[7] = nrs.getString("INCLUDEHOTEL");
                    record[8] = nrs.getString("INCLUDEBAG");
                    record[9] = nrs.getString("NUMOFTICKET");
                    record[10] = nrs.getString("TYPESELER");
                    record[11] = nrs.getString("VACATIONKIND");
                    record[12] = nrs.getString("HOTELRATE");
                    outPutSearch.add(i, record);
                    i++;
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String[]> getOutPutSearch() {
        return outPutSearch;
    }

    public ArrayList<String> getAllUserVacations(String userName) throws SQLException {
        Connection connection = SqlConnection.ConnectorVacation();
        ArrayList<String> allUserVacations = new ArrayList<>();
        ResultSet rs = null;
        String query = "select vacationId from Vacations where Username='" + userName + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (rs.next()) {
            allUserVacations.add(rs.getString("vacationId"));
        }
        connection.close();
        return allUserVacations;
    }

    //return all details of the wanted vacation id
    public String[] getVacationDetails(String userName, String vacationID) throws SQLException {
        Connection connection = SqlConnection.ConnectorVacation();
        String[] record;
        ResultSet rs = null;
        String query = "select * from Vacations where vacationId = '" + vacationID + "' AND Username='" + userName + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (rs.next()) {
            record = new String[10];
            record[0] = rs.getString("FROMDATE");
            record[1] = rs.getString("TODATE");
            record[2] = rs.getString("DESTINATION");
            record[3] = rs.getString("PRICE");
            record[4] = rs.getString("FLIGHT");
            record[5] = rs.getString("INCLUDEHOTEL");
            record[6] = rs.getString("INCLUDEBAG");
            record[7] = rs.getString("NUMOFTICKET");
            record[8] = rs.getString("VACATIONKIND");
            record[9] = rs.getString("HOTELRATE");
            connection.close();
            return record;
        } else {
            return null;
        }
    }

    //bring all the details from the current columnName and current choice
    public void searchAllRecords(String filterChoice, String filterChoiceForSearch) {
    }

    public void deleteAllUserVacations(String userName) {
        Connection connection = SqlConnection.ConnectorVacation();
        String query = "delete from Vacations where userName='" + userName + "'";

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.execute();
            connection.close();
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    public void deleteFromVacationAfterSale(String currentUser, String vacationID) {
        Connection connection = SqlConnection.ConnectorVacation();
        String queryDelete = "DELETE FROM  Vacations WHERE vacationId = '" + vacationID + "' AND userName='" + currentUser + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(queryDelete);
            pst.execute();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}