package healthe;

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

// This class sends and receives SleepTime to and from the database
public class SleepTimeBean implements Serializable {
    @Resource(name="derby/ics491")
    
    private DataSource ds;
    
//    This method sets up the datasource and returns a data source
    public static DataSource getDS() throws SQLException {
        PGPoolingDataSource ds = new PGPoolingDataSource();

        ds.setDatabaseName("ics491");
        ds.setUser("DBUSER");
        ds.setPassword("ics491"); // need to encrypt this
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        ds.setSsl(true);
//        ds.setSslfactory(classname);

        return ds;
    }
            
    /** This method sends an SleepTimeBean to the database
     *
     * @param hours is number of hours slept
     * @param aDate is the date of record
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public void addSleepTime(String name, int hours, Date aDate) throws SQLException, NamingException{
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");
            //get database connection
            Connection con = ds.getConnection();
            if(con==null)
                throw new SQLException("Can't get database connection");

            String s = "INSERT INTO SleepTime(name,adate,hours) "
                    + "values(?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(s); 
            ps.setString(1, name);
            ps.setDate(2, aDate);
            ps.setInt(3, hours);
            
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
               String s1 = ("DROP TABLE SleepTime");
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
            String s2 = ("Create table SleepTime\n(" +
                        "user TEXT not null primary key,\n" +
                        "date DATE not null,\n" +
                        "hours NUMERIC not null\n" +
                        ")");
            s.executeUpdate(s2);
            System.out.println("Table created");
        }
        catch (Exception SQL_Exception){
            System.out.println("Error, table not created.");
        }
    }
    
    
    public List<SleepTime> getRecentSleepTime(String name) throws SQLException, NamingException{
        return getSleepTimeList(name);
    }
//    this method retrieves the data from the database in the form of a list
    private List<SleepTime> getSleepTimeList(String name) throws SQLException, NamingException{
        System.out.println("Get SleepTime list");
        List<SleepTime> list = new ArrayList<SleepTime>();
        PreparedStatement stmt = null;
        try {
            ds = getDS();
            if(ds==null)
                throw new SQLException("Can't get data source");

            try ( //connect to database
                    Connection con = ds.getConnection()) {
                if(con==null)
                    throw new SQLException("Can't get database connection");
                
                String query = "SELECT * FROM SleepTime WHERE ? ORDER BY aDate DESC LIMIT 7";
                
                stmt = con.prepareStatement(query);
                stmt.setString(1, name);
                //get data from database
                ResultSet result = stmt.executeQuery();
                
                int g = 0;
                while(result.next() && g < 6){
                    SleepTime sleep = new SleepTime(result.getString("user"), 
                            result.getDate("adate"),
                            result.getDouble("hours"));
                    
                    list.add(sleep);
                    g++;
                }
            }
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
