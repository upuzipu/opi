package utils;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Hit entity
 */
@Entity(name = "hit")
public class Hit {
    private float x;
    private float y;
    private float R;
    private LocalDateTime currentTime;
    private boolean hit;
    private long workTime;
    private Long id;

    public Hit() {

    }

    public Hit(float x, float y, float R) {
        this.x = x;
        this.y = y;
        this.R = R;
    }

    public float getR() {
        return R;
    }

    public void setR(float R) {
        this.R = R;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public boolean getHit() {
        return hit;
    }

    public void setHit(boolean isHit) {
        this.hit = isHit;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }


    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public long getWorkTime() {
        return workTime;
    }

    public void setWorkTime(long workTime) {
        this.workTime = workTime;
    }

    @Override
    public String toString() {
        return "x = " + x +
                ", y = " + y +
                ", R = " + R +
                ", isHit = " + getHit();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * Check if hit in area
     * @return true if hit in area, false if it doesn't
     */
    public boolean checkHit() {
        float x = getX();
        float y = getY();
        float R = getR();
        if (x >= 0 && y >= 0) {
            return x <= R / 2 && y <= R;
        } else if (x < 0 && y >= 0) {
            return Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(R, 2);
        } else if (x <= 0 && y < 0) {
            return y >= -2 * x - R;
        }
        return false;
    }
}
