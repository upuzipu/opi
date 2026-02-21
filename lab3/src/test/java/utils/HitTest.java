package utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HitTest {

    @Test
    @DisplayName("Попадание точки в первую четверть")
    public void firstQuarterHit() {
        Hit hit = new Hit(1.5f, 0f, 3f);

        Assertions.assertTrue(hit.checkHit());
    }

    @Test
    @DisplayName("Промах точки в первую четверть")
    public void firstQuarterMiss() {
        Hit hit = new Hit(1.6f, 0f, 3f);

        Assertions.assertFalse(hit.checkHit());
    }

    @Test
    @DisplayName("Попадание точки во вторую четверть")
    public void secondQuarterHit() {
        Hit hit = new Hit(-2.0f, 0f, 3f);

        Assertions.assertTrue(hit.checkHit());
    }

    @Test
    @DisplayName("Промах точки во вторую четверть")
    public void secondQuarterMiss() {
        Hit hit = new Hit(-3.1f, 0f, 3f);

        Assertions.assertFalse(hit.checkHit());
    }

    @Test
    @DisplayName("Попадание точки в третью четверть")
    public void thirdQuarterHit() {
        Hit hit = new Hit(-1.0f, -1.0f, 3f);

        Assertions.assertTrue(hit.checkHit());
    }

    @Test
    @DisplayName("Промах точки в третью четверть")
    public void thirdQuarterMiss() {
        Hit hit = new Hit(-1.0f, -1.1f, 3f);

        Assertions.assertFalse(hit.checkHit());
    }

    @Test
    @DisplayName("Промах точки в четвёртую четверть")
    public void fourthQuarterMiss() {
        Hit hit = new Hit(1.0f, -1.0f, 3f);

        Assertions.assertFalse(hit.checkHit());
    }
}
