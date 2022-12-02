package com.cydeo.library.step_definitions;

import com.cydeo.library.pages.BookPage;
import com.cydeo.library.utilities.BrowserUtils;
import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class US04_BookInfoMatchDB_StepDef {

    BookPage bookPage = new BookPage();
    Map<String, String> mapOfUIInfo = new HashMap<>();
    Map<String, String> mapOfDBInfo = new HashMap<>();


    @When("I open book {string}")
    public void i_open_book(String string) {

        bookPage.inputSearch.sendKeys(string);
        BrowserUtils.waitForVisibility(bookPage.expectedResultAfterSearchByName(string), 10);
        List<WebElement> listOfColumnNames = bookPage.listNamesOfColumn;
        List<WebElement> listOfBook = bookPage.listBookInfo;
        for (int i = 0; i < listOfColumnNames.size(); i++) {
            if (listOfColumnNames.get(i).getText().equalsIgnoreCase("name") || listOfColumnNames.get(i).getText().equalsIgnoreCase("author") || listOfColumnNames.get(i).getText().equalsIgnoreCase("year")) {
                mapOfUIInfo.put(listOfColumnNames.get(i).getText().toLowerCase(), listOfBook.get(i).getText());
            }
        }
//        System.out.println("mapOfUIInfo = " + mapOfUIInfo);
    }

    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {

        DB_Util.runQuery("select name, author,year from books where name='Chordeiles minor'");
        mapOfDBInfo = DB_Util.getRowMap(1);
//        System.out.println("mapOfDBInfo = " + mapOfDBInfo);
        Assert.assertEquals(mapOfDBInfo, mapOfUIInfo);

    }
}
