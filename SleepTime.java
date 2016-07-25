package healthe;

import java.sql.Date;

/**
 *
 * @author team alpha
 */

public class SleepTime {
    private Integer hours;
    private Date aDate;

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public SleepTime(Date aDate, Integer hours) {
        this.aDate = aDate;
        this.hours = hours;
    }
    
}
