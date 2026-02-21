# Руководство по сборке проекта (Apache Ant)

## Требования

- **Java** 8 или выше
- **Apache Ant** 1.10+
- **Apache Ivy** (для загрузки зависимостей)
- **ant-contrib** (для trycatch, replaceregexp и др.)
- Для отдельных целей: **SVN**, **Git**, **scp**

## Быстрый старт

1. Загрузить зависимости (выполнить один раз):
   ```bash
   ant -f build-resolve.xml resolve
   ```

2. Собрать проект:
   ```bash
   ant build
   ```

3. Запустить тесты:
   ```bash
   ant test
   ```

## Цели (targets)

### Базовые

| Цель | Описание | Команда |
|------|----------|---------|
| **compile** | Компиляция исходного кода | `ant compile` |
| **build** | Компиляция + упаковка в JAR | `ant build` |
| **clean** | Удаление скомпилированных классов и временных файлов | `ant clean` |
| **test** | Запуск JUnit-тестов (после build) | `ant test` |

### Дополнительные

| Цель | Описание | Команда |
|------|----------|---------|
| **music** | Воспроизведение музыки после сборки | `ant music` |
| **scp** | Копирование JAR на сервер по SCP | `ant scp` |
| **native2ascii** | Преобразование файлов локализации в ASCII | `ant native2ascii` |
| **doc** | MD5/SHA-1 в MANIFEST + генерация Javadoc | `ant doc` |
| **xml** | Валидация всех XML-файлов | `ant xml` |
| **env** | Сборка и запуск в альтернативном окружении | `ant env` |
| **alt** | Альтернативная версия с заменой имён, упаковка в JAR | `ant alt` |

### Интеграция с репозиториями

| Цель | Описание | Команда |
|------|----------|---------|
| **history** | При ошибке compile — загрузка предыдущей SVN-ревизии | `ant history` |
| **report** | Сохранение JUnit XML, git add и commit | `ant report` |
| **diff** | SVN commit, если изменения не касаются исключённых классов | `ant diff` |
| **team** | 2 предыдущие SVN-ревизии, сборка, zip | `ant team` |

## Файл параметров `build.properties`

Основные параметры задаются в `build.properties`. Перед запуском проверьте:

- `main.class` — главный класс (beans.HandlerBean)
- `version` — версия проекта (1.0)
- `scp.host`, `scp.remote.dir`, `scp.port` — для цели **scp**
- `music.file` — путь к аудиофайлу для **music**
- `svn.url` — URL SVN-репозитория
- `diff.excluded.classes` — классы, при изменении которых **diff** не делает commit

### Платформа (Windows / Unix)

Для Linux и macOS раскомментируйте в `build.properties`:

```properties
#shell = /bin/sh
#shell.arg = -c
```

И закомментируйте:

```properties
#shell = cmd
#shell.arg = /c
```

## Подготовка окружения

### 1. Установка Apache Ant

**macOS (Homebrew):**
```bash
brew install ant
```

**Ubuntu/Debian:**
```bash
sudo apt install ant
```

**Windows:** скачайте с [ant.apache.org](https://ant.apache.org/), добавьте в PATH.

### 2. Загрузка зависимостей

**Важно:** Сначала загрузите зависимости без основного сценария (ant-contrib поставляется через Ivy):

```bash
ant -f build-resolve.xml resolve
```

После этого используйте стандартный build:

```bash
ant build
```

Ivy загрузит JAR-зависимости (JUnit, ant-contrib и др.) в каталог `lib/`.

### 3. Проверка сборки

```bash
ant clean
ant build
```

Результат: `out/lab3.jar`.

## Примеры использования

### Полная сборка с тестами и документацией

```bash
ant clean resolve build test doc
```

### Сборка альтернативной версии

```bash
ant alt
```

Будет создан `out/lab3-alt.jar` с заменой имён (Hit→Point, checkHit→checkPoint и т.п.).

### SVN: сборка предыдущих ревизий

```bash
ant team
```

Создаётся `team/team-versions.zip` с JAR двух предыдущих ревизий.

### Валидация XML

```bash
ant xml
```

Проверяются все XML-файлы в проекте.

## Структура проекта

```
lab3/
├── build.xml          # Сценарий Ant
├── build.properties   # Параметры сборки
├── ivy.xml            # Зависимости Ivy
├── lib/               # Загруженные JAR
├── src/main/java/     # Исходный код
├── src/main/resources/# Ресурсы и локализация
├── src/test/          # Тесты
├── target/            # Скомпилированные классы
├── out/               # Готовые JAR
├── report/            # Отчёты JUnit
└── doc/               # Javadoc
```

## Устранение неполадок

### `Cannot find ant-contrib`

Загрузите зависимости: `ant -f build-resolve.xml resolve`. Убедитесь, что в `lib/` есть `ant-contrib*.jar`.

### `compile` падает

Проверьте, что зависимости загружены: `ant resolve`.

### `scp` не выполняется

Убедитесь, что настроены `scp.host`, `scp.remote.dir`, `scp.port` в `build.properties` и команда `scp` доступна в PATH.

### `history`, `team`, `diff` не работают

Для этих целей нужна рабочая копия в SVN и установленный `svn` в PATH.
