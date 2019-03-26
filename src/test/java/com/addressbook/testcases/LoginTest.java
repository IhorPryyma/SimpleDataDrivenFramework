package com.addressbook.testcases;

import com.addressbook.base.TestBase;
import com.addressbook.utilites.TestUtil;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Hashtable;

public class LoginTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "getdata")
    public void loginTest(Hashtable<String, String> data){

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(getPageTitle(), data.get("hometitle"));


        click("loginBtn_XPATH");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        softAssert.assertEquals("a", data.get("logintitle"));
        softAssert.assertAll();
    }
}
