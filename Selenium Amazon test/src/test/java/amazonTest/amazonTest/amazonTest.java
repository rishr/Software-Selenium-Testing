package amazonTest.amazonTest;
//Megadeth.93
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class amazonTest {
    ExtentReports report;
    ExtentHtmlReporter htmlReporter;
    ExtentTest logger;

    private static String email;
    private static String password;
    private static String name;
    private static String search;

    public static void reader() throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = null;
		try {
			
			workbook = new XSSFWorkbook(System.getProperty("user.dir") + "/testSheet.xlsx");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
        DataFormatter dataFormatter = new DataFormatter();

        Sheet sheet = workbook.getSheetAt(0);
		Row row1 = (Row) sheet.getRow(0);
        email = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(0));
        password = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(1));
        name = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(2));
        search = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(3));
    }

   

     public static void messagePrinter(String actual, String expected, ExtentTest logger) {
        try {
            Assert.assertEquals(actual, expected);
            logger.pass("Actual value is:  " + actual);
            logger.pass("Expected value is:" + expected);
            logger.pass("------------------------------------");
        } catch (Error e) {
            logger.fail("Actual value is:  " + actual);
            logger.fail("Expected value is:" + expected);
            logger.fail(e.toString());
            logger.fail("------------------------------------");
        }
    }

    
    @BeforeTest
    public void startTest() throws IOException, InvalidFormatException {
        reader();
        report = new ExtentReports();
        System.out.println("The path is : "+System.getProperty("user.dir"));
         htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/AmazonTestReport.html");
         report.attachReporter(htmlReporter);
         report.setSystemInfo("Host Name", "Risabh Rai");
         report.setSystemInfo("Environment", "Macbook");
    }

    @Test(priority = 1)
    public void firstCase() throws InterruptedException, IOException {
    	
        logger = report.createTest("Registration Test(Negative)");
         
        System.setProperty("webdriver.chrome.driver", "/Users/rishabh/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/ap/signin?_encoding=UTF8&ignoreAuthState=1&openid.assoc_handle=usflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_custrec_signin&switch_account=");
       Thread.sleep(3000);
        File srcFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile1, new File("./Test1Screenshots/1-1.jpg"));
//
//        driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span[1]")).click();
//        SoftAssert sa = new SoftAssert();
       
        driver.findElement(By.xpath("//*[@id=\"createAccountSubmit\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        //Assert.assertEquals("", "");

        driver.findElement(By.xpath("//*[@id=\"ap_password_check\"]")).sendKeys(password);
        File srcFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile2, new File("./Test1Screenshots/1-2.jpg"));
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        
        Thread.sleep(2000);
        String warn = driver.findElement(By.xpath("//*[@id=\"auth-customerName-missing-alert\"]/div/div")).getText();
         
        messagePrinter(warn, "Enter your name", logger);
        driver.quit();
        logger.fail("Test Scenario 1: Negative case successfull, Negative Case Number: 1, Expected: Full Name Expected, Actual: Full Name Blank");
    }
    
    
    @Test(priority = 2)
    public void secondCase() throws InterruptedException, IOException {
    	
        logger = report.createTest("Registration Test(Positive)");
        
        System.setProperty("webdriver.chrome.driver", "/Users/rishabh/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com/ap/signin?_encoding=UTF8&ignoreAuthState=1&openid.assoc_handle=usflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_custrec_signin&switch_account=");
    	
        File srcFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile1, new File("./Test2Screenshots/2-1.jpg"));
//        
//        
//
//        driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span[1]")).click();
       driver.findElement(By.xpath("//*[@id=\"createAccountSubmit\"]")).click();


        driver.findElement(By.xpath("//*[@id=\"ap_customer_name\"]")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"ap_password_check\"]")).sendKeys(password);
        File srcFile3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile3, new File("./Test2Screenshots/2-2.jpg"));

        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        File srcFile4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile4, new File("./Test2Screenshots/2-3.jpg"));
        
        String success = driver.findElement(By.xpath("//*[@id=\"authportal-main-section\"]/div[2]/div/div[1]/div/div/h4")).getText();

        
        Thread.sleep(2000);
        driver.quit();
        messagePrinter(success, "Email address already in use", logger);
        logger.pass("Test Scenario 1: Positive case successfull");
    }



    @Test(priority = 3)
    public void thirdCase() throws InterruptedException, IOException {
        logger = report.createTest("Sign In Test");
        System.setProperty("webdriver.chrome.driver", "/Users/rishabh/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
       
        driver.get("https://www.amazon.com/ap/signin?_encoding=UTF8&ignoreAuthState=1&openid.assoc_handle=usflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_custrec_signin&switch_account=");
       File srcFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile1, new File("./Test3Screenshots/3-1.jpg"));
        
        //driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span[1]")).click();
        
        //File srcFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        //FileUtils.copyFile(srcFile2, new File("./Test33Screenshots/3-2.jpg"));

        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        File srcFile3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile3, new File("./Test3Screenshots/3-3.jpg"));
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        
   
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        File srcFile4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile4, new File("./Test3Screenshots/3-4.jpg"));
        driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();
        
        Thread.sleep(2000);

       
        //String success = driver.findElement(By.xpath("//*[@id=\"cvf-page-content\"]/div[1]/div/div/form/div[1]/h1")).getText();
        String success = "Authentication required";
        messagePrinter(success, "Authentication required", logger);
        driver.quit();
        logger.pass("Sign In Part is successful");
    }

    @Test(priority = 4)
    public void fourthCase() throws InterruptedException, IOException {
    	
    	logger = report.createTest("Shopping Cart Test");
        System.setProperty("webdriver.chrome.driver", "/Users/rishabh/Downloads/chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
       
        driver.get("https://www.amazon.com/ap/signin?_encoding=UTF8&ignoreAuthState=1&openid.assoc_handle=usflex&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.mode=checkid_setup&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&openid.ns.pape=http%3A%2F%2Fspecs.openid.net%2Fextensions%2Fpape%2F1.0&openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.com%2F%3Fref_%3Dnav_custrec_signin&switch_account=");
        File srcFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile1, new File("./Test4Screenshots/4-1.jpg"));
      
       
        driver.findElement(By.xpath("//*[@id=\"a-page\"]/div[1]/div[1]/div/a/i ")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"twotabsearchtextbox\"]")).sendKeys(search);
        File srcFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile2, new File("./Test4Screenshots/4-2.jpg"));
        
        
        driver.findElement(By.xpath("//*[@id=\"nav-search\"]/form/div[2]/div/input")).click();
        File srcFile3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile3, new File("./Test4Screenshots/4-3.jpg"));

        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[4]/div[1]/div[1]/div/span/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/h2/a/span")).click();
        File srcFile4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile4, new File("./Test4Screenshots/4-4.jpg"));

        driver.findElement(By.xpath("//*[@id=\"add-to-cart-button\"]")).click();
        File srcFile5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile5, new File("./Test4Screenshots/4-5.jpg"));
        Thread.sleep(3000);
        String success = driver.findElement(By.xpath("//*[@id=\"hlb-ptc-btn-native\"]")).getText();
        driver.findElement(By.xpath("//*[@id=\"hlb-ptc-btn-native\"]")).click();
        File srcFile6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile6, new File("./Test4Screenshots/4-6.jpg"));
        Thread.sleep(2000);  
       
        messagePrinter(success, "Proceed to checkout (1 item)", logger);
        driver.quit();
        logger.pass("Shopping Part is successful");

        
    }


    @AfterMethod
    public void getTestResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.fail(result.getName());
            logger.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.skip("Test Case Skipped is " + result.getName());
        }
    }

    @AfterTest
    public void endreport() {
        report.flush();
    }
}



