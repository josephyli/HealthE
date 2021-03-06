package healthe;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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

public class UserBean {
    
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

        ds.setDatabaseName("postgres");
        ds.setUser("josephli");
        ds.setServerName("localhost");
        ds.setPortNumber(5432);

        return ds;
    }
    
    public boolean userExistsAlready(String name) throws SQLException {
        PreparedStatement stmt = null;
        boolean nameFound = false;
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");

            try ( //connect to database
                    Connection con = ds.getConnection()) {
                if(con==null)
                    throw new SQLException("Can't get database connection");
                
                String query = "SELECT * FROM \"Users\" WHERE name = ?";
                stmt = con.prepareStatement(query);
                
                stmt.setString(1, name);
                
                ResultSet result = stmt.executeQuery();
                
                while(result.next()){
                    nameFound = true;
                    System.out.println("Found");
                    break;                    
                }
                
                result.close();
            }
        }
        catch (Exception SQLException) {
            System.err.println("SQL exception caught");
        }
        finally {
            if (stmt!=null)
                stmt.close();
        }
        
            
        return nameFound;
    }
    public String getHashedPassword(String name) throws SQLException, NamingException {
        PreparedStatement stmt = null;
        String storedPassword = null;
        boolean nameFound = false;
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");

            try ( //connect to database
                    Connection con = ds.getConnection()) {
                if(con==null)
                    throw new SQLException("Can't get database connection");
                
                String query = "SELECT password FROM \"Users\" WHERE name = ?";
                stmt = con.prepareStatement(query);
                
                stmt.setString(1, name);
                
                ResultSet result = stmt.executeQuery();
                
                while(result.next()){
                    storedPassword = result.getString("password");
                    nameFound = true;
                    System.out.println("Found");
                    break;                    
                }
                
                result.close();
            }
        }
        catch (Exception SQLException) {
            System.err.println("SQL exception caught");
        }
        finally {
            if (stmt!=null)
                stmt.close();
        }
        
            
        return storedPassword;
        
    }
    

    /**
     * Creates a new instance of UserBean
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public UserBean() throws SQLException, NamingException{
        
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

            String s = "UPDATE \"Users\" "
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
    
    void createNewUser(String name, String password) throws SQLException, NamingException{
        
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");
            //get database connection
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String s = "INSERT INTO \"Users\"(name, password) "
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
               String s1 = ("DROP TABLE \"Users\"");
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
            String s2 = ("CREATE TABLE \"Users\" ("
                + "name text not null primary key, "
                + "password text not null)");
            s.executeUpdate(s2);
            System.out.println("Table created");
        }
        catch (Exception SQL_Exception){
            System.out.println("Error, table not created.");
        }
        finally {
            con.close();
        }
    }
    
}
