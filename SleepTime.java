package healthe;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author team alpha
 */

public class SleepTime {
    private Double hours = null;
    private Date aDate = null;
    private final String name;

    public Double getHours() {
        return hours;
    }

    public void setHours(Double xhours) {
        this.hours = xhours;
    }
    
    public Date getDate(){
        return aDate;
    }

    public SleepTime(String name, Date aDate, Double hours) {
        this.name = name;
        this.aDate = aDate;
        this.hours = hours;
    }
    
}
