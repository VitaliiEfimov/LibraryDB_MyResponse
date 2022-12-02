package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.DashBoardPage;
import com.cydeo.library.pages.LoginPage;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US02_BorrowedBooksNumber_StepDef {
    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    String expectedDBBorrowedBooks;

    @Given("user login as a librarian")
    public void user_login_as_a_librarian() {
        loginPage.login("librarian");
    }

    @When("user take borrowed books number")
    public void user_take_borrowed_books_number() {
        expectedDBBorrowedBooks = dashBoardPage.getModuleCount("Borrowed Books");
    }

    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        DB_Util.createConnectionConfigProp(1);
        DB_Util.runQuery("select count(*) as borrowedBooks\n" +
                "from users u\n" +
                "         inner join book_borrow b on u.id = b.user_id\n" +
                "where is_returned = 0");
        String actualDBBorrowedBooks = DB_Util.getFirstRowFirstColumn();
        Assert.assertEquals(expectedDBBorrowedBooks, actualDBBorrowedBooks);
    }
}
