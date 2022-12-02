package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BasePage;
import com.cydeo.library.pages.BookPage;
import com.cydeo.library.pages.LoginPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

public class US07_StudentBorrowNewBook {
    LoginPage loginPage = new LoginPage();
    BasePage basePage = new BasePage();
    BookPage bookPage = new BookPage();
    String nameOfNotReturnedBook;
    String bDateOfNotReturnedBook;
    String bookName;


    @Given("I login as a student")
    public void i_login_as_a_student() {
        loginPage.login("student");
    }

    @And("I search book name called {string}")
    public void i_search_book_name_called(String string) {
        bookName = string;
        bookPage.inputSearch.sendKeys(string);
        BrowserUtils.waitFor(1);
        BrowserUtils.selectByVisibleText(bookPage.selectShowRecords, "500");
        BrowserUtils.waitFor(1);
    }

    @When("I click Borrow Book")
    public void i_click_borrow_book() {
        for (WebElement each : bookPage.borrowBookButtons) {
            if (each.getAttribute("class").equals("btn btn-primary btn-sm  ")) {
                each.click();
                break;
            }
        }
    }

    @Then("verify that book is shown in {string} page")
    public void verify_that_book_is_shown_in_page(String string) {
        List<WebElement> list = bookPage.listOfNotReturnedBook;
        basePage.navigateModule(string);
        BrowserUtils.waitForVisibility(bookPage.listOfNotReturnedBook.get(0), 10);
        for (int i = 0; i < list.size(); i++) {
            nameOfNotReturnedBook = list.get(list.size() - 5).getText();
            bDateOfNotReturnedBook = list.get(list.size() - 4).getText();
        }
        Assert.assertEquals(bookName, nameOfNotReturnedBook);
    }

    @Then("verify logged student has same book in database")
    public void verify_logged_student_has_same_book_in_database() {
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

}
