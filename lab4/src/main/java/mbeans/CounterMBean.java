package mbeans;

public interface CounterMBean {
    /** Общее число установленных пользователем точек */
    int getTotalPoints();

    /** Число точек, попадающих в область */
    int getHitCount();

    /** Процентное отношение промахов к общему числу кликов (0–100) */
    double getMissPercentage();

    /** Форматированная строка процента промахов для отображения */
    String getMissPercentageFormatted();

    /** Вызывается при каждой новой точке (Hit или Miss) */
    void recordHit(String isHit);

    /** Инициализация счётчиков при загрузке из БД */
    void initFromStorage(int totalPoints, int hitCount);
}
