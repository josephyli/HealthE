package healthe;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;


/**
 *
 * @author team alpha
 */

public class UserBean implements Serializable {
    
    private DataSource ds;
    
    private SleepTime sleepTime;
        
    public SleepTime getSleepTime() {
        return sleepTime;
    }


    public void setSleepTime(SleepTime sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }
    
    private static DataSource getDS() throws SQLException {
        PGPoolingDataSource ds = new PGPoolingDataSource();

        ds.setDatabaseName("teamalpha");
        ds.setUser("teamalpha");
        ds.setServerName("localhost");
        ds.setPortNumber(5432);

        return ds;
    }
    
    private boolean checkUser(String name, String password) throws SQLException, NamingException {
        System.out.println("Get user list");
        List<User> userNameList = new ArrayList<User>();
        PreparedStatement stmt = null;
        boolean nameFound = false;
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");

            //connect to database 
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String query = "SELECT ? FROM users WHERE password == ?";
            String query1 = "SELECT * FROM \"Users\" WHERE name = 'test' AND password = 'test';";
            stmt = con.prepareStatement(query);

            stmt.setString(1, name);
            stmt.setString(2, password);
            
            ResultSet result = stmt.executeQuery();
            
            while(result.next()){
                if (result.getString("name")==name && result.getString("password")==password) {
                    nameFound = true;
                    break;
                }

            }
            con.close();
        }
        catch (Exception SQLException) {
            
        }
        finally {
            if (stmt!=null)
                stmt.close();
        }
        System.out.println("Not found");
            
        return nameFound;
        
    }
    
    private List<User> getUserList() throws SQLException, NamingException{
        System.out.println("Get user list");
        List<User>userNameList = new ArrayList<>();
        PreparedStatement stmt = null;
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");

            //connect to database 
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String query = "SELECT * FROM USERS ORDER BY name ASC";
            
            stmt = con.prepareStatement(query);

            //get data from database
            ResultSet result = stmt.executeQuery();
            
//            while(result.next()){
//                User user;
//                user = new User(......);
//
//                userNameList.add(user);
//            }
            con.close();
        }
        catch (Exception SQLException) {
            
        }
        finally {
            if (stmt!=null)
                stmt.close();
        }
        System.out.println("List returned");
            
        return userNameList;
    }
    /**
     * Creates a new instance of UserBean
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public UserBean() throws SQLException, NamingException{
        
    }
    
    public void login(String name, String password) throws SQLException, NamingException {
        if (checkUser(name, password)) {
            // log in success!
        } else {
            // error!
        }
        
        
    }

    private void updateUserPassword(String name, String password) throws SQLException, NamingException{
        
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");
            //get database connection
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String s = "UPDATE user "
                    + "SET password = ? "
                    + "WHERE name=?;";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setString(1, password);
            ps.setString(2, name);            
            
            ps.executeUpdate();
            System.out.println("Values updated");
            con.close();
        }
        catch(Exception SQLException) {
            System.out.println("SQL error occurred");
        }
    }
    
    private void newUser(String name, String password) throws SQLException, NamingException{
        
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");
            //get database connection
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String s = "INSERT INTO users(name, password) "
                    + "values(?, ?);";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setString(1, name);
            ps.setString(2, password); 
            // need to encrypt password

            ps.executeUpdate();
            System.out.println("New user created");
            con.close();
        }
        catch(Exception SQLException) {
            System.out.println("SQL error occurred");
        }
    }

    public void dropTable() throws SQLException {
        Connection con = ds.getConnection();
        if(con==null)
            throw new SQLException("Can't get database connection");
        Statement s = con.createStatement();
           try {
               String s1 = ("DROP TABLE User");
               s.executeUpdate(s1);
               System.out.println("Table dropped");
           }
           catch (Exception SQL_Exception){
               System.out.println("Error, table not dropped.");
           }
    }
    
    public void createTable() throws SQLException {
        Connection con = ds.getConnection();
        if(con==null)
            throw new SQLException("Can't get database connection");
        Statement s = con.createStatement();
        try {
            String s2 = ("CREATE TABLE User ("
                + "name text not null primary key, "
                + "password text not null)");
            s.executeUpdate(s2);
            System.out.println("Table created");
        }
        catch (Exception SQL_Exception){
            System.out.println("Error, table not created.");
        }
    }
    
}
