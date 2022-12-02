package com.cydeo.library.pages;

import com.cydeo.library.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class BookPage extends BasePage {

    @FindBy(xpath = "//a[.=' Add Book']")
    public WebElement addBookButton;

    @FindBy(xpath = "//input[@name='name']")
    public WebElement inputBookName;

    @FindBy(xpath = "//input[@name='isbn']")
    public WebElement inputBookIsbn;

    @FindBy(xpath = "//input[@name='year']")
    public WebElement inputBookYear;

    @FindBy(xpath = "//input[@name='author']")
    public WebElement inputBookAuthor;

    @FindBy(xpath = "//select[@id='book_group_id']")
    public WebElement selectBookGroup;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveChangesButton;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement inputSearch;

    @FindBy(xpath = "//table[@id='tbl_books']/tbody/tr/td[3]")
    public WebElement bookNameInTable;

    @FindBy(xpath = "//table[@id='tbl_books']/tbody/tr/td[1]/a")
    public List<WebElement> borrowBookButtons;

    @FindBy(id = "book_categories")
    public WebElement selectCategories;

    @FindBy(xpath = "//select[@id='book_categories']/option")
    public List<WebElement> listOfBookCategories;

    @FindBy(xpath = "//table[@id='tbl_books']//th")
    public List<WebElement> listNamesOfColumn;

    @FindBy(xpath = "//tr[@class='odd']//td")
    public List<WebElement> listBookInfo;

    @FindBy(xpath = "//select[@name='tbl_books_length']")
    public WebElement selectShowRecords;

    @FindBy(xpath = "//table[@id='borrowed_list']//tr//td//a[@class='btn btn-primary btn-sm ']/../following-sibling::td")
    public List<WebElement> listOfNotReturnedBook;

    @FindBy(xpath = "//table[@id='borrowed_list']//tr//td//a[@class='btn btn-primary btn-sm ']/../following-sibling::td[1]")
    public WebElement nameOfNotReturnedBook;

    @FindBy(xpath = "//table[@id='borrowed_list']//tr//td//a[@class='btn btn-primary btn-sm ']/../following-sibling::td[2]")
    public WebElement bDateOfNotReturnedBook;



    public WebElement expectedResultAfterSearchByName(String string) {

        return Driver.getDriver().findElement(By.xpath("//td[normalize-space()='" + string + "']"));
    }


}
