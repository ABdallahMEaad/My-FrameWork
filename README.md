# my framework

![Java](https://img.shields.io/badge/Java-21-orange)
![Maven](https://img.shields.io/badge/Build-Maven-blue)
![Selenium](https://img.shields.io/badge/Selenium-4.49.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.11.0-red)
![Allure](https://img.shields.io/badge/Report-Allure-purple)

A reusable **UI test automation framework** built with Selenium, TestNG, Allure and Log4j2.
Clone it, point it at your application, and start writing tests.

## Features

- **Multi-browser support**: Chrome, Firefox and Edge, selected from a config file.
- **Page Object Model** with a ready-made `BasePage` (explicit waits built in).
- **Allure reporting** with epics, features and stories.
- **Failure evidence in Allure**: for every failed test, the report includes a screenshot and the log of that test only (with the stack trace).
- **Log4j2 logging** to the console and to `logs/application.log`.
- **Data utilities**: CSV, Excel (Apache POI), JSON (Gson) and properties file readers.
- **Centralised versions**: every dependency and plugin version lives in one place in `pom.xml`.

## Tech stack

| Tool | Version property in `pom.xml` |
|------|-------------------------------|
| Java | `java.version` |
| Selenium | `selenium.version` |
| TestNG | `testng.version` |
| Allure TestNG | `allure.version` |
| Apache POI | `poi.version` |
| Apache Commons CSV | `commons-csv.version` |
| Gson | `gson.version` |
| Log4j2 | `log4j.version` |
| Maven Compiler Plugin | `maven-compiler-plugin.version` |
| Maven Surefire Plugin | `maven-surefire-plugin.version` |

## Prerequisites

- JDK 21
- Maven 3.9+
- Google Chrome, Firefox or Edge (whichever you configure)
- [Allure Commandline](https://allurereport.org/docs/install/) to view the report

Browser drivers are downloaded automatically by Selenium Manager.

## Project structure

```
my-framework
├── pom.xml                         # versions section at the top
├── testng.xml                      # scans the TestCases package
└── src
    ├── main
    │   ├── java/Page/              # page objects (BasePage + your pages)
    │   └── resources/
    │       ├── config.properties   # url + browserName
    │       └── log4j2.properties
    └── test/java
        ├── DriverFactory/          # Base class + Chrome / Firefox / Edge drivers
        ├── TestCases/              # your tests go here
        └── Utils/                  # Config, CSV, Excel, JSON, screenshot, log capture
```

## Configuration

Edit `src/main/resources/config.properties`:

```properties
url=https://example.com
browserName=chrome
```

Supported values for `browserName`: `chrome`, `firefox`, `edge`.

## Running the tests

```bash
mvn clean test
```

Run only a specific TestNG group:

```bash
mvn clean test -Dgroups=smoke
```

## Allure report

```bash
allure serve allure-results
```

Or generate a static report:

```bash
allure generate allure-results --clean -o allure-report
```

### Log of failed tests

When a test fails, `Base.tearDown` attaches the following to the Allure report, before the browser is closed:

- `Failure screenshot - <test name>`
- `Test log - <test name>`: only the log lines of that test, including the stack trace.

To see it in action, set `enabled = true` on `SampleTest.demoFailureToSeeLogInAllure`, run the tests and open the failed test in the report.

## Writing a new test

1. Add your page objects under `src/main/java/Page/` and extend `BasePage`:

   ```java
   public class HomePage extends BasePage {

       private final By title = By.tagName("h1");

       public HomePage(WebDriver driver) {
           super(driver);
       }

       public String getTitleText() {
           return findElement(title).getText();
       }
   }
   ```

2. Add a test class under `src/test/java/TestCases/` that extends `Base`.
   The browser is opened and closed for you, and the URL from `config.properties` is already loaded:

   ```java
   @Epic("My App")
   @Feature("Home")
   public class HomeTest extends Base {

       @Story("Home page title")
       @Test(groups = "smoke")
       public void titleIsDisplayed() {
           HomePage homePage = new HomePage(driver);
           Assert.assertFalse(homePage.getTitleText().isEmpty());
       }
   }
   ```

   No change to `testng.xml` is needed: every class in the `TestCases` package is picked up automatically.

3. Delete `SampleTest` once you have your own tests.

## Updating versions

Open the `VERSIONS SECTION` at the top of `pom.xml` and change the number you need, for example:

```xml
<selenium.version>4.49.0</selenium.version>
```

Everything else in the `pom.xml` reads from these properties.

## Notes

- Test reports and logs (`allure-results/`, `allure-report/`, `logs/`) are git-ignored.
- `SampleTest` is only an example of how a test is written.


👨‍💻 Author

ABDALLAH AHMED MEAAD
QA / QC Engineer | Manual & Automation Testing

📍 Cairo, Egypt
📧 abdallahmead0@gmail.com
📱 +20 1022656255 / +20 1554770480

🔗 **LinkedIn:** [Abdallah MEaad](https://www.linkedin.com/in/abdallah-meaad/)

💻 **GitHub:** [ABdallahMEaad](https://github.com/ABdallahMEaad)

🌐 **Portfolio:** [Abdallah Meaad](https://abdallahmeaad.github.io/My_CV)
