package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BookPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class US06_AddedBookMatchingDB {
    BookPage bookPage = new BookPage();


    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {
        bookPage.addBookButton.click();
    }

    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String string) {
        bookPage.inputBookName.sendKeys(string);
    }

    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String string) {
        bookPage.inputBookIsbn.sendKeys(string);
    }

    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String string) {
        bookPage.inputBookYear.sendKeys(string);
    }

    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String string) {
        bookPage.inputBookAuthor.sendKeys(string);
    }

    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String string) {
        BrowserUtils.selectByVisibleText(bookPage.selectBookGroup, string);
    }

    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
        bookPage.saveChangesButton.click();
    }

    @Then("the librarian verify new book by {string}")
    public void the_librarian_verify_new_book_by(String string) {
        BrowserUtils.waitFor(1);
        bookPage.inputSearch.sendKeys(string);
        BrowserUtils.waitForVisibility(bookPage.expectedResultAfterSearchByName(string), 10);
        BrowserUtils.waitFor(1);
        String nameFromUI = bookPage.expectedResultAfterSearchByName(string).getText();
        Assert.assertEquals(string, nameFromUI);
    }

    @Then("the librarian verify new book from database by {string}")
    public void the_librarian_verify_new_book_from_database_by(String string) {

        DB_Util.runQuery("select id,name,author from books where name = '" + string + "' order by id desc");
        String nameFromDB = DB_Util.getCellValue(1, 2);
        Assert.assertEquals(string,nameFromDB);


    }

}
