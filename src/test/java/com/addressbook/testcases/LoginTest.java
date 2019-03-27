package com.addressbook.testcases;

import com.addressbook.base.TestBase;
import com.addressbook.utilites.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class LoginTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "getdata")
    public void loginTest(Hashtable<String, String> data, Method m){

        log.debug("Starting test execution : " + m.getName());

        SoftAssert softAssert = new SoftAssert();
        // Check Home Page Title
        softAssert.assertEquals(getPageTitle(), data.get("hometitle"), "Home page title do not matched");

        // Login flow
        click("loginBtn_XPATH");

        softAssert.assertTrue(isElementPresent("signupBtn_CSS"), "Sign Up button not found");

        type("emailField_XPATH", data.get("useremail"));
        type("passwordField_XPATH", data.get("password"));

        click("signinBtn_XPATH");

        // Check reaching authorized home page
        softAssert.assertTrue(isElementPresent("addresses_XPATH"), "Addresses button not found");
        softAssert.assertEquals(getPageTitle(), data.get("hometitle"), "Authorized home page title do not matched");

        log.debug("Finishing test execution : " + m.getName());
//        softAssert.fail();

        softAssert.assertAll();
    }
}
