package com.addressbook.listeners;

import com.addressbook.base.TestBase;
import com.addressbook.utilites.TestUtil;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.*;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {

    public void onStart(ISuite suite) {

    }


    public void onFinish(ISuite suite) {

    }


    public void onTestStart(ITestResult result) {

        test = rep.startTest(result.getName());

        if(!TestUtil.isTestRunnable(result.getName(), excel)) {
            throw new SkipException("Skipping the test " + result.getName() + " as the Run Mode is NO");
        }

        Reporter.log(result.getName() + " started");
    }


    public void onTestSuccess(ITestResult result) {
        Reporter.log(result.getName() + " passed");

        test.log(LogStatus.PASS, result.getName() + " PASSED");
        rep.endTest(test);
        rep.flush();
    }


    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        TestUtil.captureScreenshot();

        test.log(LogStatus.FAIL, result.getName() + " FAILED with exception : " + result.getThrowable());
        test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));

        Reporter.log(result.getName() + " failed !!!");
        Reporter.log("Click to see screenshot");
        Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.SCREENSHOT_PATH + TestUtil.screenshotName + "\">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.SCREENSHOT_PATH + TestUtil.screenshotName + "\"><img height=200 width=200 src=\"" + TestUtil.screenshotName + "\"></img></a>");

        rep.endTest(test);
        rep.flush();
    }


    public void onTestSkipped(ITestResult result) {
        Reporter.log(result.getName() + " skipped !!!");

        test.log(LogStatus.SKIP, result.getName() + " Skipped the test as the Run Mode is NO.");
        rep.endTest(test);
        rep.flush();
    }


    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }


    public void onStart(ITestContext context) {

    }


    public void onFinish(ITestContext context) {

    }
}
