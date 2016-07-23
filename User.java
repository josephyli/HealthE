package healthE;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.sql.Date;
import javax.naming.NamingException;


/**
 *
 * @author team alpha
 */
@ManagedBean(name = "User")
@SessionScoped
public class User implements Serializable {
    Integer sleepTime;
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