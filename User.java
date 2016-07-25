package healthe;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Date;
import javax.naming.NamingException;


/**
 *
 * @author team alpha
 */
public class User implements Serializable {
    Integer hours;
	 Date aDate;
	 String name;
	 
    public String getName() {
        return name;
    }
	 
	 public void setName(String name) {
		  this.name = name;
	 }
    public Integer getHour() {
        return hours;
    }

    public void setHours(Integer xHours) {
        this.hours = xHours;
    }
	 
	 public void setDate(Integer year, Integer month, Integer day) {
		 this.aDate.setYear(year);
		 this.aDate.setDate(day);
		 this.aDate.setMonth(month);
	 }
    
    /**
     * Creates a new instance of User
     * @throws java.sql.SQLException
     * @throws javax.naming.NamingException
     */
    public User() throws SQLException, NamingException{
        System.out.println("New user registered!");
    }
    
}
