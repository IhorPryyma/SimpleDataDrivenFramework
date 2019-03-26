package com.addressbook.base;

import com.addressbook.utilites.ExcelReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    public static final String PROJDIR = System.getProperty("user.dir");
    private static final String CONFIGFILE_PATH = "/src/test/resources/properties/config.properties";
    private static final String ORFILE_PATH = "/src/test/resources/properties/objectrepo.properties";
    public static final String EXCELFILE_PATH = "/src/test/resources/excel/testdata.xlsx";


    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties objectrepo = new Properties();
    private static FileInputStream fis;

    public static String browser = "";
    public static WebDriverWait wait;

    public static ExcelReader excel = new ExcelReader(PROJDIR + EXCELFILE_PATH);


    @BeforeSuite
    public void setUp(){

        if(driver == null){

            try {
                fis = new FileInputStream(PROJDIR + CONFIGFILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                config.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fis = new FileInputStream(PROJDIR + ORFILE_PATH);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                objectrepo.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(System.getenv("browser") != null && !System.getenv("browser").equals("")){
                browser = System.getenv("browser");
            } else {
                browser = config.getProperty("browser");
            }

            config.setProperty("browser", browser);

            if(browser.equalsIgnoreCase("chrome")){
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if(browser.equalsIgnoreCase("firefox")){
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if(browser.equalsIgnoreCase("ie")){
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
            } else if(browser.equalsIgnoreCase("opera")){
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
            } else if(browser.equalsIgnoreCase("edge")){
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            } else if(browser.equalsIgnoreCase("safari")){
                driver = new SafariDriver();
            }

            driver.get(config.getProperty("testurl"));
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(config.getProperty("pageload.timeout")), TimeUnit.SECONDS);
            wait = new WebDriverWait(driver, Integer.parseInt(config.getProperty("webdriver.wait")));
        }
    }


    private WebElement findElementByLocator(String locator){
        WebElement element = null;

        if(locator.endsWith("_CSS")){
            element = driver.findElement(By.cssSelector(objectrepo.getProperty(locator)));
            System.out.print("locator" + element.toString());
        } else if(locator.endsWith("_XPATH")){
            element = driver.findElement(By.xpath(objectrepo.getProperty(locator)));
        } else if(locator.endsWith("_ID")){
            element = driver.findElement(By.id(objectrepo.getProperty(locator)));
        } else if(locator.endsWith("_TAG")){
            element = driver.findElement(By.tagName(objectrepo.getProperty(locator)));
        } else if(locator.endsWith("_CLASS")){
            element = driver.findElement(By.className(objectrepo.getProperty(locator)));
        } else if(locator.endsWith("_LINK")){
            element = driver.findElement(By.linkText(objectrepo.getProperty(locator)));
        }
        return element;
    }


    public void click(String locator){
        WebElement element = findElementByLocator(locator);

        element.click();
    }


    public void type(String locator, String value){
        WebElement element = findElementByLocator(locator);

        element.sendKeys(value);
    }


    public boolean isElementPresent(String locator){
        try{
            findElementByLocator(locator);
            return true;
        } catch (NoSuchElementException exc){
            exc.printStackTrace();
        }
        return false;
    }


    public void select(String locator, String value){
        WebElement dropdown = findElementByLocator(locator);

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);
    }


    public void verifyEquality(String expected, String actual){
        try{
            Assert.assertEquals(expected, actual);
        } catch (Throwable t){
            t.printStackTrace();
        }
    }


    public String getPageTitle(){
        return driver.getTitle();
    }

    @AfterSuite
    public void tearDown(){

        if(driver != null){
            driver.quit();
        }
    }
}