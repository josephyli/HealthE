package healthe;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    String name;
    Integer hours;
    Date aDate;
    boolean success;
    boolean notEnough;
    
    private DataSource ds;
    List<User> userNameList;
    
    private SleepTime sleepTime;
        
    public SleepTime getSleepTime() {
        return sleepTime;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public void setSleepTime(SleepTime sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    public void setUserName(String a) {
        
        this.name = a;
    }
    
    public String getUserName() {
        return name;
    }

    
    public static DataSource getdS() throws SQLException {
        PGPoolingDataSource ds = new PGPoolingDataSource();

        ds.setDatabaseName("users");
        ds.setUser("ics491");
        ds.setServerName("localhost");
        ds.setPortNumber(5432);

        return ds;
    }
    
    public static DataSource getDS() throws SQLException {
        PGPoolingDataSource ds = new PGPoolingDataSource();

        ds.setDatabaseName("teamalpha");
        ds.setUser("teamalpha");
        ds.setServerName("localhost");
        ds.setPortNumber(5432);

        return ds;
    }
    
    public List<User> getuserNameList() throws SQLException, NamingException{
        System.out.println("Get user list");
        userNameList = new ArrayList<User>();
        PreparedStatement stmt = null;
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");

            //connect to database 
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String query = "SELECT * FROM USER ORDER BY name ASC";
            
            stmt = con.prepareStatement(query);

            //get data from database
            ResultSet result = stmt.executeQuery();
            
//            while(result.next()){
//                User user;
//                user = new User(result.getString("name"), 
//                        result.getInt("strength"),
//                        result.getInt("brains"),
//                        result.getInt("worth"),
//                        result.getInt("num_of_classes"));
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
     * Creates a new instance of UserNumberBean
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public UserBean() throws SQLException, NamingException{
        name = "";
        
    }

    public void invalidate() throws SQLException, NamingException {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
//        session.invalidate();

    }
    
    public String logout() throws SQLException, NamingException {
        saveUser();
        
        return "index";
    }
    
    public String altLogout() throws SQLException, NamingException {
        saveUser();
        return "";
    }
    public String login() throws SQLException, NamingException {
        boolean nameFound = false;
        
        if (name != null) {
            for (int i = 0; i < userNameList.size(); i++) {
                User userx = userNameList.get(i);
                if(userx.getName().equals(name)) {
                    System.out.println(userx.getName());
                    name = userx.getName();
                    nameFound = true;
                }
            }
        }
        if (nameFound == false) {
        
            newUser();
        }
        return "play";
    }

    private void saveUser() throws SQLException, NamingException{
        
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");
            //get database connection
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String s = "UPDATE user "
                    + "SET hours = ? "
                    + "WHERE name=?;";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setInt(1, hours);
            ps.setString(2, name);            
            
            ps.executeUpdate();
            System.out.println("Values updated");
            con.close();
        }
        catch(Exception SQLException) {
            System.out.println("SQL error occurred");
        }
    }
    
    private void newUser() throws SQLException, NamingException{
        
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");
            //get database connection
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String s = "INSERT INTO user(name) "
                    + "values(?);";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setString(1, name);
            
            
            ps.executeUpdate();
            System.out.println("New user created");
            con.close();
        }
        catch(Exception SQLException) {
            System.out.println("SQL error occurred");
        }
    }

    
    public String addData() throws SQLException, NamingException {
        // sleep data
        saveUser();
        return "play";
    }
    
}
