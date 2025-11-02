# YouTrack Automated Tests

## Описание

Этот проект представляет собой набор автоматизированных тестов для проверки функциональности веб-приложения YouTrack (доступно по адресу http://193.233.193.42:9091). Тесты разработаны на языке Java с использованием фреймворка Selenium WebDriver для взаимодействия с UI, TestNG для запуска и отчётов, а также паттерна Page Object Model (POM) для структурирования кода.

Основные сценарии тестирования включают:
- Авторизацию (позитивный и негативный кейсы).
- Создание задач (с вариациями данных через Data-Driven Testing).
- Редактирование задач.
- Удаление задач.

Тесты обеспечивают чистку данных (удаление созданных задач), захват скриншотов при ошибках и параллельное выполнение. Проект предназначен для локального запуска в IntelliJ IDEA с JDK 24 и браузером Google Chrome.

## Требования

- **Java**: JDK 24 (или выше, совместимо с Selenium).
- **IDE**: IntelliJ IDEA (рекомендуется для Maven интеграции).
- **Браузер**: Google Chrome (ChromeDriver загружается автоматически через WebDriverManager).
- **Зависимости**: Управляются через Maven (pom.xml).

## Установка

1. **Клонируйте репозиторий**:
   ```
   git clone https://github.com/your-username/youtrack-tests.git
   cd youtrack-tests
   ```

2. **Настройте конфигурацию**:
    - Создайте файл `local.properties` в корне проекта с содержимым:
      ```
      login=###
      password=###
      ```
    - Вместо "###" введите ваши данные.
    - Убедитесь, что `.gitignore` включает `local.properties` для безопасности (не коммитить credentials).

3. **Соберите проект**:
    - Откройте проект в IntelliJ IDEA.
    - Reload Maven (Maven → Reload All Maven Projects).
    - Если нужно, скачайте зависимости: `mvn clean install`.

4. **Проверьте окружение**:
    - JDK: Установите как project SDK (File → Project Structure → SDKs).
    - Chrome: Установлен (WebDriverManager подберёт ChromeDriver автоматически).

## Запуск тестов

1. **Через IntelliJ IDEA**:
    - Right-click на `testng.xml` (в src/test/resources) → Run as TestNG Suite.
    - Или индивидуально: Right-click на метод/класс в YouTrackTests.java → Run.

2. **Через Maven**:
   ```
   mvn clean test
   ```
    - Это запустит все тесты через testng.xml с параллелизмом (2 потока).

3. **Параметры запуска**:
    - Для headless mode (без UI): Раскомментируйте `options.addArguments("--headless");` в BaseTest.java.
    - Отчёты: После запуска в `target/surefire-reports` (index.html для деталей pass/fail).

4. **Ожидаемый вывод**:
    - Консоль: Сообщения вроде "Positive login test passed", issue IDs.
    - Скриншоты ошибок: В `target/screenshots/` (при fail).
    - Если тесты падают: Проверьте локаторы в POM (inspect UI в браузере).

## Структура проекта

- **src/test/java/base/BaseTest.java**: Базовый класс с инициализацией WebDriver (ThreadLocal для параллелизма), загрузкой properties, скриншотами.
- **src/test/java/pages/**: POM-классы для страниц (LoginPage, DashboardPage, IssuesPage, NewIssuePage, IssueDetailsPage).
- **src/test/java/tests/YouTrackTests.java**: Тестовые методы с asserts, Data-Driven (@DataProvider).
- **src/test/resources/testng.xml**: Конфигурация TestNG для параллелизма и groups.
- **pom.xml**: Зависимости (Selenium, TestNG, WebDriverManager, Commons IO).
- **local.properties**: Локальные credentials (игнорируется Git).
- **target/**: Build-артефакты, отчёты, скриншоты (игнорируется Git).

## Зависимости

- Selenium Java: 4.25.0
- TestNG: 7.10.2
- WebDriverManager: 5.9.2
- Apache Commons IO: 2.16.1

Полный список в `pom.xml`. Обновите версии при необходимости через `mvn versions:use-latest-versions`.

## Возможные проблемы и решения

- **Driver не найден**: WebDriverManager скачает автоматически, но проверьте интернет/прокси.
- **Локаторы не работают**: Inspect элементы в Chrome DevTools, обновите By в POM (data-test стабильны, но UI может измениться).
- **Параллелизм ошибки**: Убедитесь в ThreadLocal driver; увеличьте таймауты если flaky.
- **Русский UI**: Тексты в xpath на русском — если язык сменится, адаптируйте.

## Контрибьютинг

- Форкните репозиторий.
- Создайте branch: `git checkout -b feature/new-test`.
- Commit изменения: `git commit -m "Добавлен новый тест"`.
- Push: `git push origin feature/new-test`.
- Pull Request на main.

## Лицензия

MIT License. См. LICENSE файл (если добавлен). Проект для образовательных целей.