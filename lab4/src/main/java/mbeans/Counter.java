package mbeans;

import javax.faces.bean.ManagedBean;
import javax.management.*;
import java.io.Serializable;

/**
 * MBean: (1) общее число точек, число попаданий в область, оповещение при кратности 10;
 * (2) процент промахов от общего числа кликов.
 */
@ManagedBean(name = "counterBean")
public class Counter extends NotificationBroadcasterSupport implements Serializable, CounterMBean {
    private int totalPoints = 0;
    private int hitCount = 0;
    private int sequenceNumber = 0;

    @Override
    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public int getHitCount() {
        return hitCount;
    }

    @Override
    public double getMissPercentage() {
        if (totalPoints == 0) return 0.0;
        int misses = totalPoints - hitCount;
        return 100.0 * misses / totalPoints;
    }

    @Override
    public String getMissPercentageFormatted() {
        return String.format("%.1f", getMissPercentage());
    }

    @Override
    public void recordHit(String isHit) {
        totalPoints++;
        if ("Hit".equals(isHit)) {
            hitCount++;
        }

        if (totalPoints % 10 == 0) {
            Notification n = new Notification(
                    "points.multiple.of.10",
                    this,
                    sequenceNumber++,
                    "Количество установленных точек стало кратно 10: " + totalPoints
            );
            sendNotification(n);
        }
    }

    @Override
    public void initFromStorage(int totalPoints, int hitCount) {
        this.totalPoints = totalPoints;
        this.hitCount = hitCount;
    }
}
