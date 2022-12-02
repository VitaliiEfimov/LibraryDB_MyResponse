package com.cydeo.library.step_definitions;

import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.sql.SQLException;


public class US05_GenreMostBorrowed_StepDef {

    String actual;

    @Given("Establish the database connection")
    public void establish_the_database_connection() throws SQLException {
        DB_Util.createConnectionConfigProp(1);
    }

    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() throws SQLException {

        DB_Util.runQuery("select bc.name, count(*)\n" +
                "from book_borrow bb\n" +
                "         inner join books b on bb.book_id = b.id\n" +
                "         inner join book_categories bc on b.book_category_id = bc.id\n" +
                "group by name\n" +
                "order by 2 desc");
        actual = DB_Util.getFirstRowFirstColumn();
        System.out.println("actual+string = " + actual);
    }

    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String string) {
        System.out.println("actual+string = " + actual + string);
        Assertions.assertEquals(string,actual);
    }

}
