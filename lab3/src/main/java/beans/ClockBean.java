package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for handle time
 */
@ManagedBean
@SessionScoped
public class ClockBean implements Serializable {
    private LocalDateTime dateTime;
    private final DateTimeFormatter dateFormat;

    /**
     * Initializing variables
     */
    public ClockBean() {
        this.dateTime = LocalDateTime.now();
        this.dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    /**
     * get formatted time
     * @return formatted time
     */
    public String getDateTime() {
        return this.dateTime.format(dateFormat);
    }

    /**
     * set time
     * @param dateTime dateTime
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * update time
     */
    public void updateTime(){
        this.dateTime = LocalDateTime.now();
    }
}
