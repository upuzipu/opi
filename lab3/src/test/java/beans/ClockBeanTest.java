package beans;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockBeanTest {
    private ClockBean clockBean;
    private DateTimeFormatter dateFormat;
    private LocalDateTime dateTime;

    @BeforeEach
    public void setUp() {
        this.clockBean = new ClockBean();
        this.dateTime = LocalDateTime.now();
        this.dateFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    @Test
    @DisplayName("Идентичное время без обновления")
    public void clockBeanWithoutUpdate() {
        Assertions.assertEquals(clockBean.getDateTime(), dateTime.format(dateFormat));
    }

    @Test
    @DisplayName("Идентичное время с обновлением")
    public void clockBeanWithUpdateTrue() throws InterruptedException {
        Thread.sleep(1000);

        clockBean.updateTime();
        dateTime = LocalDateTime.now();

        Assertions.assertEquals(clockBean.getDateTime(), dateTime.format(dateFormat));
    }

    @Test
    @DisplayName("Несовпадающее время с обновлением")
    public void clockBeanWithUpdateFalse() throws InterruptedException {
        Thread.sleep(1000);

        dateTime = LocalDateTime.now();

        Assertions.assertNotEquals(clockBean.getDateTime(), dateTime.format(dateFormat));
    }
}
