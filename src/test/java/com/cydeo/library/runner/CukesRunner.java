package com.cydeo.library.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

    @RunWith(Cucumber.class)
    @CucumberOptions(
            plugin = {
                    "pretty",
                    "json:target/cucumber/.json",
                    "html:target/cucumber-report.html",
                    "rerun:ztarget/rerun.txt",
                    "me.jvt.cucumber.report.PrettyReports:target/cucumber"
            },
            features = "src/test/resources/features",
            glue = "com/cydeo/library/step_definitions",
            dryRun = false,
            tags = "@1",
            publish = true)

    public class CukesRunner {
}