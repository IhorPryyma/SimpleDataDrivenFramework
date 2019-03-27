package com.addressbook.listeners;

import com.addressbook.base.TestBase;
import com.addressbook.utilites.TestUtil;
import org.testng.*;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {

    @Override
    public void onStart(ISuite suite) {

    }

    @Override
    public void onFinish(ISuite suite) {

    }

    @Override
    public void onTestStart(ITestResult result) {
        if(!TestUtil.isTestRunnable(result.getName(), excel)) {
            throw new SkipException("Skipping the test " + result.getName() + " as the Run Mode is NO");
        }

        Reporter.log(result.getTestName() + " started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        Reporter.log(result.getTestName() + " passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.setProperty("org.uncommons.reportng.escape-output", "false");
        TestUtil.captureScreenshot();

        Reporter.log(result.getTestName() + " failed !!!");
        Reporter.log("Click to see screenshot");
        Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.SCREENSHOT_PATH + TestUtil.screenshotName + "\">Screenshot</a>");
        Reporter.log("<br>");
        Reporter.log("<br>");
        Reporter.log("<a target=\"_blank\" href=\"" + TestUtil.SCREENSHOT_PATH + TestUtil.screenshotName + "\"><img height=200 width=200 src=\"" + TestUtil.screenshotName + "\"></img></a>");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Reporter.log(result.getTestName() + " skipped !!!");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
