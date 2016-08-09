package healthe;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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

    @Resource(name = "derby/ics491")

    private DataSource ds;

//    This method sets up the datasource and returns a data source
    public static DataSource getDS() throws SQLException {
        PGPoolingDataSource ds = new PGPoolingDataSource();

        ds.setDatabaseName("postgres");
        ds.setUser("josephli");
        ds.setServerName("localhost");
        ds.setPortNumber(5432);
//        ds.setSsl(true);
//        ds.setSslfactory(classname);

        return ds;
    }

    /**
     * This method sends an SleepTimeBean to the database
     *
     * @param hours is number of hours slept
     * @param entered_date is the date of record
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public void addSleepTime(String name, LocalDate entered_date, Double hours) throws SQLException, NamingException {
        try {
            ds = getDS();
            if (ds == null) {
                throw new SQLException("Can't get data source");
            }
            try ( //get database connection
                    Connection con = ds.getConnection()) {
                if (con == null) {
                    throw new SQLException("Can't get database connection");
                }

                String s = "INSERT INTO \"SleepTime\" (name, entered_date, hours) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(s);
                Date date = Date.valueOf(entered_date);
                ps.setString(1, name);
                ps.setObject(2, date);
                ps.setDouble(3, hours);

                ps.executeUpdate();
//                ps.closeOnCompletion();
                System.err.println("Values inserted");
            }
        } catch (Exception SQLException) {
            System.err.println(SQLException);
            dropTable();
            createTable();
        }

    }

    public void dropTable() throws SQLException {
        Connection con = ds.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        Statement s = con.createStatement();
        try {
            String s1 = ("DROP TABLE \"SleepTime\"");
            s.executeUpdate(s1);
            System.err.println("Table dropped");
        } catch (Exception SQL_Exception) {
            System.err.println("Error, table not dropped.");
        }
        s.close();
        con.close();
    }

    public void createTable() throws SQLException {
        Connection con = ds.getConnection();
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }
        Statement s = con.createStatement();
        try {
            String s2 = ("CREATE TABLE \"SleepTime\" (\n"
                    + "	name text not null, \n"
                    + "	entered_date date not null, \n"
                    + "	hours NUMERIC not null );");
            s.executeUpdate(s2);
            System.err.println("Table created");
        } catch (Exception SQL_Exception) {
            System.err.println(SQL_Exception);
            System.err.println("Error, table not created.");
        }
    }

//    this method retrieves the data from the database in the form of a list
    public List<SleepTime> getSleepTimeList(String name) throws SQLException, NamingException {
        System.err.println("Get SleepTime list");
        List<SleepTime> list = new ArrayList<SleepTime>();
        PreparedStatement stmt = null;
        try {
            ds = getDS();
            if (ds == null) {
                throw new SQLException("Can't get data source");
            }

            try ( //connect to database
                    Connection con = ds.getConnection()) {
                if (con == null) {
                    throw new SQLException("Can't get database connection");
                }

                String query = "SELECT * FROM \"SleepTime\" WHERE name = ? AND entered_date > current_date - interval '7' day ORDER BY entered_date ASC LIMIT 7";

                stmt = con.prepareStatement(query);
                stmt.setString(1, name);
                //get data from database
                ResultSet result = stmt.executeQuery();

                int g = 0;
                while (result.next() && g <= 6) {
                    
                    Date date = (java.sql.Date) result.getObject("entered_date");
                    Double hours = result.getDouble("hours");
                    SleepTime sleep = new SleepTime(result.getString("name"), date, result.getDouble("hours"));
                    list.add(sleep);
                    g++;
                }
            }
        } catch (Exception SQLException) {
            System.err.println(SQLException);
//            dropTable();
//            createTable();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        System.err.println("List returned");

        return list;
    }
}
