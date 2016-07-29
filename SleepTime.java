package healthe;

import java.sql.Date;

/**
 *
 * @author team alpha
 */

public class SleepTime {
    private Double hours;
    private Date aDate;
    private String name;

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }
    
    public Date aDate(){
        return aDate;
    }

    public SleepTime(String name, Date aDate, Double hours) {
        this.name = name;
        this.aDate = aDate;
        this.hours = hours;
    }
    
}
