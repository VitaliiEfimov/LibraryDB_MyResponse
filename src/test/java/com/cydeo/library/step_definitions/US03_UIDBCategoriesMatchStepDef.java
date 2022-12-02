package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BookPage;
import com.cydeo.library.pages.DashBoardPage;
import com.cydeo.library.pages.LoginPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

public class US03_UIDBCategoriesMatchStepDef {

    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    BookPage bookPage = new BookPage();
    List<String> listOfCatUI;
    List<String> listOfCatDB;

    @Given("I login as a librarian")
    public void i_login_as_a_librarian() {

        loginPage.login("librarian");

    }
    @When("I navigate to {string} page")
    public void i_navigate_to_page(String string) {

        dashBoardPage.navigateModule(string);

    }
    @When("I take all book categories in UI")
    public void i_take_all_book_categories_in_ui() {

        listOfCatUI=BrowserUtils.getAllSelectOptions(bookPage.selectCategories);
        listOfCatUI.remove(0);

    }
    @When("I execute query to get book categories")
    public void i_execute_query_to_get_book_categories() {

        DB_Util.runQuery("select name from book_categories;");
        listOfCatDB = DB_Util.getColumnDataAsList(1);

    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {

        Assert.assertEquals(listOfCatUI,listOfCatDB);

    }
}
