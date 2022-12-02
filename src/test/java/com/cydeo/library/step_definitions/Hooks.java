package com.cydeo.library.step_definitions;

import com.cydeo.library.utilities.ConfigurationReader;
import com.cydeo.library.utilities.DB_Util;
import com.cydeo.library.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Hooks {

    class Credentials {
        String username;
        String password;

        Credentials(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @Before
    public void setUp(){
//        System.out.println("this is coming from BEFORE");
        Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().get(ConfigurationReader.getProperty("url1"));}


    @After
    public void teardownScenario(Scenario scenario){
        // We will implement taking screenshot in this method
        //System.out.println("It will be closing browser using cucumber @After each scenario");

        if(scenario.isFailed()){

            byte [] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png",scenario.getName());
        }
        Driver.closeDriver();
    }


    List<Credentials> credentialUsers = new LinkedList<>();

    // @Before (order = 2)
    public void loginExcelReadSetup() {
        File file = new File(ConfigurationReader.getProperty("fileName"));

        FileInputStream fileInputStream;
        XSSFWorkbook workbook;
        XSSFSheet sheet = null;

        try {
            fileInputStream = new FileInputStream(file);

            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet("Credentials");
        } catch (IOException e) {

        }
        int numberOfCredentials = sheet.getPhysicalNumberOfRows();

        for (int i = 1; i < numberOfCredentials; i++) {
            String username = String.valueOf(sheet.getRow(i).getCell(0));
            String password = String.valueOf(sheet.getRow(i).getCell(1));
            credentialUsers.add(new Credentials(username, password));
        }
    }

    // @BeforeStep
//    public void loginEachUser() {
//        Credentials user = credentialUsers.remove(0);
//        new LoginPage().loginToPage(user.username, user.password);
//    }


    //@Before (value = "@login", order=2)
    public void setupForLogin(){
        // If you want any code to run before any specific feature/scenario,
        // you can use value= "@tagname" to determine this
        System.out.println("====this will only apply to scenarios with @login tag");
    }

    @Before("@db")
    public void setUpDB(){
        DB_Util.createConnection();
        System.out.println("Connecting to DB");
    }

    @After("@db")
    public void destroyDB(){
        DB_Util.destroy();
        System.out.println("destroy connection" );
    }

    //@BeforeStep
    public void setupScenarioStep(){
        System.out.println("--------> applying setup using @BeforeStep");
    }

    //@AfterStep
    public void afterStep(){
        System.out.println("--------> applying tearDown using @AfterStep");
    }
}
