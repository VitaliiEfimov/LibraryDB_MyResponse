package com.cydeo.library.step_definitions;

import com.cydeo.library.utilities.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.AssertionFailedError;
import org.junit.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class US01_CurrentlyStored_StepDef {

    //first part
List<String> listOfId = new ArrayList<>();
    List<String> listOfAllColumns = new ArrayList<>();
    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        DB_Util.createConnectionConfigProp(1);
        DB_Util.runQuery("select * from users");
        listOfId = DB_Util.getColumnDataAsList(1);

    }
    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        for (int i = 0; i < listOfId.size(); i++) {
            for (int j = 0; j < listOfId.size(); j++) {
                try {
                    Assert.assertFalse(listOfId.get(i).equals(listOfId.get(j)) && i != j);
                } catch (AssertionError e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //second part
    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {
        DB_Util.createConnectionConfigProp(1);
        DB_Util.runQuery("select * from users");
        listOfAllColumns = DB_Util.getAllColumnNamesAsList();
        System.out.println("listOfAllColumns = " + listOfAllColumns);
    }
    @Then("verify the below columns are listed in result")
    public void verify_the_below_columns_are_listed_in_result(List<String> dataTable) {
        try {
            Assert.assertEquals(dataTable, listOfAllColumns);
        } catch (AssertionFailedError e) {
            e.printStackTrace();
        }

    }
}
