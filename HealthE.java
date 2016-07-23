package healthE;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author team alpha
 */

// This class sends and receives HEALTHE to and from the database
public class HealthE implements Serializable {
    @Resource(name="derby/ics491")
    
    private DataSource ds;
    
//    This method sets up the datasource and returns a data source
    public static DataSource getDS() throws SQLException {
        ClientDataSource ds = new ClientDataSource();

        ds.setDatabaseName("ics491");
        ds.setUser("DBUSER");
        ds.setPassword("ics491"); // need to encrypt this
        ds.setServerName("localhost");
        ds.setPortNumber(1527);

        return ds;
    }
            
    /** This method sends an HealthE to the database
     *
     * @param hours is number of hours slept
     * @param aDate is the date of record
     * @param now is the timestamp
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public void sendHealthE(int hours, Date aDate) throws SQLException, NamingException{
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");
            //get database connection
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String s = "INSERT INTO HealthE(adate,hours) "
                    + "values(?, ?)";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setInt(1, aDate);
            ps.setInt(2, hours);
            
            ps.executeUpdate();
            System.out.println("Values inserted");
            con.close();
        }
        catch(Exception SQLException) {
            dropTable();
            createTable();
        }
    }
    
    public void dropTable() throws SQLException {
        Connection con = ds.getConnection();
        if(con==null)
            throw new SQLException("Can't get database connection");
        Statement s = con.createStatement();
           try {
               String s1 = ("DROP TABLE HEALTHE");
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
            String s2 = ("CREATE TABLE HEALTHE ("
                + "adate Date not null primary key, "
                + "hours integer)");
            s.executeUpdate(s2);
            System.out.println("Table created");
        }
        catch (Exception SQL_Exception){
            System.out.println("Error, table not created.");
        }
    }
    
//    this method retrieves the data from the database in the form of a list
    public List<HealthE> getHealthEList() throws SQLException, NamingException{
        System.out.println("Get HealthE list");
        List<HealthE> list = new ArrayList<HealthE>();
        PreparedStatement stmt = null;
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");

            //connect to database 
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String query = "SELECT * FROM HealthE ORDER BY aDate DESC";
            
            stmt = con.prepareStatement(query);

            //get data from database
            ResultSet result = stmt.executeQuery();
            
            int g = 0;
            while(result.next() && g < 20){
                HealthE healthe = new HealthE(result.getDate("adate"), 
                                                result.getInt("hours"));

                list.add(healthe);
                g++;
            }
            con.close();
        }
        catch (Exception SQLException) {
            dropTable();
            createTable();
        }
        finally {
            if (stmt!=null)
                stmt.close();
        }
        System.out.println("List returned");
            
        return list;
    }
}
