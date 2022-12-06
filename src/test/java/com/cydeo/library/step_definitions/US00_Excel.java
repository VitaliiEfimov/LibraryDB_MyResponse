package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BasePage;
import com.cydeo.library.pages.BookPage;
import com.cydeo.library.pages.LoginPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.ConfigurationReader;
import com.cydeo.library.utilities.DB_Util;
import com.cydeo.library.utilities.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class US00_Excel {

    LoginPage loginPage = new LoginPage();
    BasePage basePage = new BasePage();
    String bookName;
    String nameOfNotReturnedBook;
    String bDateOfNotReturnedBook;
    BookPage bookPage = new BookPage();

    @Given("I login as a student from Excel")
    public void i_login_as_a_student_from_excel() throws IOException {
        String path = "src/test/resources/files/library_credentials.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("library1");
        String username = String.valueOf(sheet.getRow(0).getCell(1));
        String password = String.valueOf(sheet.getRow(0).getCell(2));
        loginPage.login(username,password);

    }
    @When("I search book name called {string} from Excel")
    public void i_search_book_name_called_from_excel(String string) throws IOException {
        String path = "src/test/resources/files/library_books.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("library1");
        bookName = String.valueOf(sheet.getRow(0).getCell(0));


        bookPage.inputSearch.sendKeys(bookName);
        BrowserUtils.waitFor(1);
        BrowserUtils.selectByVisibleText(bookPage.selectShowRecords, "500");
        BrowserUtils.waitFor(1);

    }

    @Then("verify that book is shown in {string} page Excel")
    public void verify_that_book_is_shown_in_page_excel(String string) {
        List<WebElement> list = bookPage.listOfNotReturnedBook;
        basePage.navigateModule(string);
        BrowserUtils.waitForVisibility(bookPage.listOfNotReturnedBook.get(0), 10);
        for (int i = 0; i < list.size(); i++) {
            nameOfNotReturnedBook = list.get(list.size() - 5).getText();
            bDateOfNotReturnedBook = list.get(list.size() - 4).getText();
        }
        Assert.assertEquals(bookName, nameOfNotReturnedBook);

    }
    @Then("verify logged student has same book in database Excel")
    public void verify_logged_student_has_same_book_in_database_excel() {
        DB_Util.runQuery("select full_name,b.name,bb.borrowed_date from users u\n" +
                "                                                  inner join book_borrow bb on u.id = bb.user_id\n" +
                "                                                  inner join books b on bb.book_id = b.id\n" +
                "where full_name='Test Student 1'\n" +
                "order by 3 desc");

        String nameOfBookDB = DB_Util.getCellValue(1, 2);
        String dateOfBookDB = DB_Util.getCellValue(1, 3);
        Assert.assertEquals(nameOfNotReturnedBook, nameOfBookDB);
        Assert.assertEquals(bDateOfNotReturnedBook, dateOfBookDB);
    }


    @Test
    public void test1() throws IOException {
        String path = "src/test/resources/files/library_credentials.xlsx";
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = workbook.getSheet("library1");
        String username = String.valueOf(sheet.getRow(0).getCell(1));
        String password = String.valueOf(sheet.getRow(0).getCell(2));
        System.out.println(username);
        System.out.println(password);
    }

    @Test
    public void test2() throws IOException {
        for (int i = 0; i < 2; i++) {
            Driver.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Driver.getDriver().manage().window().maximize();
            Driver.getDriver().get(ConfigurationReader.getProperty("url1"));
            i_login_as_a_student_from_excel();
            i_search_book_name_called_from_excel("string");
            verify_that_book_is_shown_in_page_excel("String");
            verify_logged_student_has_same_book_in_database_excel();
        }
    }
}
