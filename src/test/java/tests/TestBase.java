package tests;

import apimanager.ApiManager;
import org.testng.annotations.BeforeSuite;

public class TestBase {

    public ApiManager am;

    public TestBase() {
        this.am = new ApiManager();
    }

    @BeforeSuite
    public void setUp() {
        am.dealWithApi();
    }
}
