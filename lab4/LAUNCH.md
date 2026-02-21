# Лабораторная работа №4 — запуск и мониторинг

## Требования

- Java 8 (или выше)
- Maven 3.x
- PostgreSQL (если используется БД из lab3; настройте `persistence.xml` и источник данных под свой сервер)

---

## 1. Запуск приложения с включённым JMX

Чтобы подключаться к приложению через **JConsole** и **VisualVM**, JVM нужно запускать с параметрами JMX.

### Вариант A: Maven Jetty с JMX

В каталоге проекта выполните:

```bash
mvn clean compile
```

Затем запустите Jetty с JMX (порт 9010 — для локальных подключений):

```bash
export MAVEN_OPTS="-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
mvn jetty:run
```

Приложение будет доступно по адресу: **http://localhost:8080/lab3** (или по порту из конфигурации Jetty в `pom.xml`).

### Вариант B: Запуск из IDE (Idea / Eclipse / NetBeans)

1. Создайте конфигурацию запуска для Maven goal `jetty:run` (или для своего сервера приложений).
2. В настройках запуска добавьте VM options (Program arguments / JVM options):

```text
-Dcom.sun.management.jmxremote
-Dcom.sun.management.jmxremote.port=9010
-Dcom.sun.management.jmxremote.local.only=false
-Dcom.sun.management.jmxremote.authenticate=false
-Dcom.sun.management.jmxremote.ssl=false
```

3. Запустите приложение и дождитесь старта сервера.

---

## 2. JConsole — мониторинг MBean и сведения о JVM

### Подключение

1. Запустите приложение с JMX (см. выше).
2. Откройте **JConsole** (входит в JDK):
   - macOS/Linux: `jconsole`
   - Windows: `jconsole.exe` или из меню JDK.
3. Во вкладке **Local Processes** выберите процесс вашего Java-приложения (например, `jetty:run`) и нажмите **Connect**.
   - Либо во вкладке **Remote Process** укажите: `localhost:9010` и нажмите **Connect**.

### Снятие показаний MBean (задание 2)

1. Откройте вкладку **MBeans**.
2. В дереве слева найдите узел **lab4.mbeans** → **PointsCounter** → **counter**.
3. В **Attributes** будут доступны:
   - **TotalPoints** — общее число установленных пользователем точек;
   - **HitCount** — число точек, попадающих в область;
   - **MissPercentage** — процентное отношение промахов к общему числу кликов;
   - **MissPercentageFormatted** — то же в виде строки для отображения.
4. Нажмите **Refresh** для обновления значений после добавления точек в веб-интерфейсе.

### Наименование и версия JVM (задание 2)

1. В JConsole откройте вкладку **VM Summary**.
2. Там указаны:
   - **VM Name** — наименование JVM;
   - **VM Version** — версия JVM;
   - **Vendor** — поставщик виртуальной машины;
   - **VM Build** — номер сборки (и другая информация о системе).

---

## 3. VisualVM — графики MBean и профилирование

### Подключение

1. Скачайте и установите [VisualVM](https://visualvm.github.io/) (или используйте встроенный в некоторые JDK: `jvisualvm`).
2. Запустите приложение с JMX на порту 9010.
3. В VisualVM в левой панели выберите ваше приложение (Local → процесс Java).
4. Если приложение запущено через Maven и не видно в списке — добавьте удалённое подключение: **File** → **Add JMX Connection**, укажите `localhost:9010`.

### График показаний MBean (задание 3)

1. Откройте вкладку **MBeans**.
2. Перейдите в **lab4.mbeans** → **PointsCounter** → **counter**.
3. В правой части для атрибутов (например, **TotalPoints**, **HitCount**, **MissPercentage**) можно включить отображение графика во времени: используйте кнопку/опцию **Add to chart** (или аналог в вашей версии VisualVM). Добавляйте точки в приложении и наблюдайте изменение графиков.

### Имя потока с наибольшей загрузкой CPU (задание 3)

1. В VisualVM откройте вкладку **Sampler** (или **Profiler**).
2. Включите профилирование по **CPU**.
3. Выполните типичные действия в приложении (добавление точек, обновление страницы).
4. Остановите профилирование и просмотрите список потоков/методов по удельному времени CPU — поток с наибольшим процентом времени и будет ответом на задание.

---

## 4. Локализация проблем производительности (задание 4)

- **VisualVM**: вкладка **Sampler** или **Profiler** → CPU / Memory. Анализ «горячих» методов и потоков.
- **Профилировщик IDE** (Idea / Eclipse / NetBeans): запуск приложения под профилировщиком (CPU, память), снятие снимков и анализ вызовов.
- В отчёте укажите: описание проблемы, способ устранения и пошаговый алгоритм с скриншотами (как воспроизвести поиск и локализацию проблемы).

---

## Краткая справка по реализованным MBean

| Назначение | MBean | Атрибуты / Поведение |
|------------|--------|----------------------|
| Число точек и попаданий, оповещение при кратности 10 | **PointsCounter** (`lab4.mbeans:type=PointsCounter,name=counter`) | `TotalPoints`, `HitCount`; при каждом новом клике вызывается `recordHit(...)`; при `TotalPoints % 10 == 0` отправляется JMX-уведомление. |
| Процент промахов | Тот же MBean | `MissPercentage`, `MissPercentageFormatted` — процент промахов к общему числу кликов. |

Оповещения при кратности 10 можно просматривать во вкладке **Notifications** у соответствующего MBean в JConsole/VisualVM (подписка на уведомления).
