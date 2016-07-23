/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthE;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;


/**
 *
 * @author team alpha
 */
@ManagedBean(name = "UserBean")
@SessionScoped
public class UserBean implements Serializable {
    String name;
    Integer hours;
    Date aDate;
    boolean success;
    boolean notEnough;
    
    private DataSource ds;
    List<User> userNameList;
    
    @ManagedProperty(value="#{StuffBean}")
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
        
        this.userName = a;
    }
    
    public String getUserName() {
        return userName;
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

        ds.setDatabaseName("josephli");
        ds.setUser("josephli");
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

            String query = "SELECT * FROM PLAYER ORDER BY name ASC";
            
            stmt = con.prepareStatement(query);

            //get data from database
            ResultSet result = stmt.executeQuery();
            
            while(result.next()){
                User user = new User(result.getString("name"), 
                                                result.getInt("strength"),
                                                result.getInt("brains"),
                                                result.getInt("worth"),
                                                result.getInt("num_of_classes"));

                userNameList.add(user);
            }
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
        userName = "";
        strength = 1;
        brains = 1;
        worth = 10;
        num_of_classes = 0;
        graduated = false;
        System.out.println("Strength: " + strength);
        System.out.println("Brains: " + brains);
        System.out.println("Self-esteem: " + worth);
        System.out.println("Credits: " + num_of_classes);
    }

    public void invalidate() throws SQLException, NamingException {
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
//        session.invalidate();
        enemyBean.getRandomEnemy(num_of_classes);
    }
    
    public String logout() throws SQLException, NamingException {
        saveUser();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "index";
    }
    
    public String altLogout() throws SQLException, NamingException {
        saveUser();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        session.invalidate();
        return "";
    }
    public String login() throws SQLException, NamingException {
        boolean nameFound = false;
        success = false;
        notEnough = false;
        graduated = false;
        if (userName != null) {
            for (int i = 0; i < userNameList.size(); i++) {
                User userx = userNameList.get(i);
                if(userx.getUserName().equals(userName)) {
                    System.out.println(userx.getUserName());
                    userName = userx.getUserName();
                    strength = userx.getStrength();
                    brains = userx.getBrains();
                    worth = userx.getSelfEsteem();
                    num_of_classes = userx.getNum_of_classes();
                    nameFound = true;
                    if (num_of_classes >= 50) {
                        graduated = true;
                        enemyBean.getRandomEnemy(num_of_classes);
                    }
                    else {
                        enemyBean.getRandomEnemy(num_of_classes);
                    }
                }
            }
        }
        if (nameFound == false) {
            strength = 1;
            brains = 1;
            worth = 1;
            num_of_classes = 0;
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
                    + "SET strength = ?, brains = ?, worth = ?, num_of_classes = ? "
                    + "WHERE name=?;";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setInt(1, strength);
            ps.setInt(2, brains);
            ps.setInt(3, worth);
            ps.setInt(4, num_of_classes);
            ps.setString(5, userName);            
            
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

            String s = "INSERT INTO user(name, strength,brains,worth,num_of_classes) "
                    + "values(?, ?, ?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setString(1, userName);
            ps.setInt(2, strength);
            ps.setInt(3, brains);
            ps.setInt(4, worth);
            ps.setInt(5, num_of_classes);
            
            
            ps.executeUpdate();
            System.out.println("New user created");
            con.close();
        }
        catch(Exception SQLException) {
            System.out.println("SQL error occurred");
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isNotEnough() {
        return notEnough;
    }

    public void setNotEnough(boolean notEnough) {
        this.notEnough = notEnough;
    }
    
    public String play() throws SQLException, NamingException {
        saveUser();
        notEnough = false;
        success = false;
        return "play";
    }
    public void doStuff() throws SQLException, NamingException {
        success = false;
        notEnough = false;
        Stuff selectedStuff = stuff.getStuff(action);
        System.out.print("Action: " + action);
        String query = "SELECT * FROM STUFF WHERE NAME='" + action + "' ;";
        stuff.getStuff(query);
        if (worth >= stuff.worth) {
            setStrength(strength + stuff.strength);
            setBrains(brains + stuff.brains);
            setNum_of_classes(num_of_classes + stuff.num_of_classes);
            setWorth(worth - stuff.worth);
            System.out.print( action + " was purchased.");
            saveUser();
            success = true;
            if (num_of_classes >= 50) {
                graduated = true;
                enemyBean.getRandomEnemy(num_of_classes);
            }
        }
        else {
            notEnough = true;
        }
    }     
}
