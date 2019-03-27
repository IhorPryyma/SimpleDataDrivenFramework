package com.addressbook.testcases;

import com.addressbook.base.TestBase;
import com.addressbook.utilites.TestUtil;
import org.testng.SkipException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.Hashtable;

public class AddAddressTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "getdata")
    public void addAddressTest(Hashtable<String, String> data, Method m){

        if(!data.get("runmode").equalsIgnoreCase("Y")) {
            throw new SkipException("Skipping the test case as the Run Mode is NO");
        }

        log.debug("Starting test execution : " + m.getName());

        SoftAssert softAssert = new SoftAssert();

        click("addresses_XPATH");

        softAssert.assertTrue(isElementPresent("addressesLabel_XPATH"));

        click("addNewAddressLink_CSS");

        // add new address
        type("addfirstname_XPATH", data.get("firstname"));
        type("addlastname_XPATH", data.get("lastname"));
        type("addaddress1_XPATH", data.get("primaryaddr"));
        type("addaddress2_XPATH", data.get("secondaryaddr"));
        type("addcity_XPATH", data.get("city"));
        select("addstate_XPATH", data.get("state"));
        type("addcode_XPATH", data.get("zipcode"));

        if(data.get("country").equals("United States")){
            click("usradioBtn_XPATH");
        } else {
            click("canadaradioBtn_XPATH");
        }

        type("calendar_XPATH", data.get("birthday"));

        setAttribute("color_XPATH", data.get("color"));

        type("addage_XPATH", data.get("age"));
        type("addwebsite_XPATH", data.get("website"));
        type("addphonenumber_XPATH", data.get("phone"));

        if(data.get("interests").contains("climbing")){
            click("climbCheckBox_XPATH");
        }
        if(data.get("interests").contains("dancing")){
            click("danceCheckBox_XPATH");
        }
        if(data.get("interests").contains("reading")){
            click("readCheckBox_XPATH");
        }

        type("addnote_XPATH", data.get("note"));


        click("createAddress_XPATH");

//        click("backtolistLink_XPATH");


        // Validate successful adding
        String expected = "Address was successfully created.";

        softAssert.assertEquals(expected, getLabelText("successLabel_XPATH"), "Successfull label text do not matched!!!");

        log.debug("Finishing test execution : " + m.getName());
        softAssert.assertAll();
    }
}
