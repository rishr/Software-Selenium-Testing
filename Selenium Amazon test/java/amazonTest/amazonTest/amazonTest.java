package amazonTest.amazonTest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

//import com.sun.media.sound.InvalidFormatException;
//import com.sun.rowset.internal.Row;



public class amazonTest {
    ExtentReports report;
    ExtentHtmlReporter htmlReporter;
    ExtentTest logger;

    private static String email;
    private static String password;
    private static String name;
    private static String addr1;
    private static String addr2;
    private static String city;
    private static String state;
    private static String zipCode;
    private static String phone;
    private static String card;
    private static String search;

    public static void reader() throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = null;
		try {
			//workbook = WorkbookFactory.create(new File("amazonTest.xlsx"));
			workbook = new XSSFWorkbook(System.getProperty("user.dir") + "/amazonTest.xlsx");
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DataFormatter dataFormatter = new DataFormatter();

        Sheet sheet = workbook.getSheetAt(0);
       // @SuppressWarnings("restriction")
		Row row1 = (Row) sheet.getRow(0);
        Row row2 = (Row) sheet.getRow(1);
        email = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(0));
        password = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(1));
        name = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(2));
        search = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row1).getCell(3));
        addr1 = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row2).getCell(0));
        addr2 = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row2).getCell(1));
        city = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row2).getCell(2));
        state = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row2).getCell(3));
        zipCode = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row2).getCell(4));
        phone = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row2).getCell(5));
        card = dataFormatter.formatCellValue(((org.apache.poi.ss.usermodel.Row) row2).getCell(6));
        
        System.out.println("The value is : " + email);
        System.out.println("The value is : " + password);
        System.out.println("The value is : " + name);
        System.out.println("The value is : " + search);
        System.out.println("The value is : " + addr1);
        System.out.println("The value is : " + addr2);
        System.out.println("The value is : " + city);
        System.out.println("The value is : " + state);
        System.out.println("The value is : " + state);
        System.out.println("The value is : " + zipCode);
        System.out.println("The value is : " + phone);
        System.out.println("The value is : " + card);
        
        
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
         report.setSystemInfo("Host Name", "Rishi Nandedkar");
         report.setSystemInfo("Environment", "Windows");
    }

    @Test
    public void firstCase() throws IOException {
    	//to log the  name the test in html reports
        logger = report.createTest("Registration Test");
        
        // to load the web driver(chrome in our case) ,to the path specified 
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Gauri\\Documents\\Software_QA\\testPractise\\lib\\chromedriver.exe");
        //instantiate the driver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        //open the browser with amazon.com
        driver.get("https://www.amazon.com/");
        // Screen Shot
        File srcFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        FileUtils.copyFile(srcFile1, new File("./Test1_SS/1-1.jpg"));

        driver.findElement(By.xpath("//*[@id=\"nav-signin-tooltip\"]/div/a")).click();
        
        File srcFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile2, new File("./Test1_SS/1-2.jpg"));

        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"ap_password_check\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        String warn = driver.findElement(By.xpath("//*[@id=\"auth-customerName-missing-alert\"]/div/div")).getText();
        // Actual & Expected message in report
        
        messagePrinter(warn, "Enter your name", logger);
        File srcFile1n = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile1n, new File("./Test1_SS/N1-1.jpg"));
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"ap_password_check\"]")).clear();

        driver.findElement(By.xpath("//*[@id=\"ap_customer_name\"]")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        warn = driver.findElement(By.xpath("//*[@id=\"auth-passwordCheck-missing-alert\"]/div/div")).getText();
        messagePrinter(warn, "Type your password again", logger);
        File srcFile2n = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile2n, new File("./Test1_SS/N1-2.jpg"));
        driver.findElement(By.xpath("//*[@id=\"ap_customer_name\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).clear();

        driver.findElement(By.xpath("//*[@id=\"ap_customer_name\"]")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"ap_password_check\"]")).sendKeys(password);
        File srcFile3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile3, new File("./Test1_SS/1-3.jpg"));

        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        File srcFile4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile4, new File("./Test1_SS/1-4.jpg"));
        driver.quit();
        logger.pass("Registration Part is Pass");
    }



    @Test
    public void secondCase() throws IOException {
        logger = report.createTest("Signin Test");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Gauri\\Documents\\Software_QA\\testPractise\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.amazon.com/");
        File srcFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile1, new File("./Test2_SS/2-1.jpg"));
        driver.findElement(By.xpath("//*[@id=\"nav-flyout-ya-signin\"]/a/span")).click();
        //driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]")).click();.
        File srcFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile2, new File("./Test2_SS/2-2.jpg"));
      //*[@id="ap_email"]
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        File srcFile3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile3, new File("./Test2_SS/2-3.jpg"));

        driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();
        String hello = driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span[1]")).getText();
        messagePrinter(hello, "Hello, Gauri Sarpotdar", logger);
        File srcFile4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile4, new File("./Test2_SS/2-4.jpg"));
        driver.quit();
        logger.pass("Signin Part is Pass");
    }

    @Test
    public void thirdCase() throws InterruptedException, IOException {
    	
    	logger = report.createTest("Shopping Test");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Gauri\\Documents\\Software_QA\\testPractise\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
       
        driver.get("https://www.amazon.com/");
        File srcFile1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile1, new File("./Test3_SS/3-1.jpg"));
        System.out.println("Third caaaaaaaaaaaaasee");
        driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]")).click();
        File srcFile2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile2, new File("./Test3_SS/3-2.jpg"));
       ////*[@id="nav-link-accountList"]
        System.out.println("Third caaaaaaaaaaaaasee");
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        File srcFile3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile3, new File("./Test3_SS/3-3.jpg"));

        driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();
        String hello = driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]/span[1]")).getText();
        messagePrinter(hello, "Hello, GauriSarpotdar", logger);
        File srcFile4 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile4, new File("./Test3_SS/3-4.jpg"));

        driver.findElement(By.xpath("//*[@id=\"twotabsearchtextbox\"]")).sendKeys(search);
        driver.findElement(By.xpath("//*[@id=\"nav-search\"]/form/div[2]/div/input")).click();
        File srcFile5 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile5, new File("./Test3_SS/3-5.jpg"));

//        driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[1]" +
//                "/div[1]/div/div/div/div/div/div[2]/div[1]/div/div/span/a/div")).click();
        driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[4]/div[1]/div[3]/div/span/div/div/div[2]/div[2]/div/div[1]/div/div/div[1]/h2/a/span")).click();
       // driver.findElement(By.xpath("//*[@id=\"search\"]/div[1]/div[2]/div/span[3]/div[1]/div[1]/div/div/div/div[2]/div[1]/div/div/span/a/div")).click();
        File srcFile6 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile6, new File("./Test3_SS/3-6.jpg"));

        driver.findElement(By.xpath("//*[@id=\"add-to-cart-button\"]")).click();
        Thread.sleep(3000);
//        driver.findElement(By.xpath("//*[@id=\"siNoCoverage-announce\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"hlb-ptc-btn-native\"]")).click();
//        driver.findElement(By.xpath("//*[@id=\"a-autoid-48\"]/span/input")).click();
        File srcFile7 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile7, new File("./Test3_SS/3-7.jpg"));

        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"continue\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        File srcFile3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile3, new File("./Test3_SS/3-3.jpg"));
        driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();
        
        ////*[@id="signInSubmit"]
        
        driver.findElement(By.xpath("//*[@id=\"enterAddressFullName\"]")).clear();
        String preName = driver.findElement(By.xpath("//*[@id=\"enterAddressFullName\"]")).getAttribute("value");
        messagePrinter(preName + "null", "null", logger);
        driver.findElement(By.xpath("//*[@id=\"enterAddressFullName\"]")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id=\"enterAddressAddressLine1\"]")).sendKeys(addr1);
        driver.findElement(By.xpath("//*[@id=\"enterAddressAddressLine2\"]")).sendKeys(addr2);
        driver.findElement(By.xpath("//*[@id=\"enterAddressCity\"]")).sendKeys(city);
        driver.findElement(By.xpath("//*[@id=\"enterAddressStateOrRegion\"]")).sendKeys(state);
        driver.findElement(By.xpath("//*[@id=\"enterAddressPostalCode\"]")).sendKeys(zipCode);
        driver.findElement(By.xpath("//*[@id=\"enterAddressPhoneNumber\"]")).sendKeys(phone);
        driver.findElement(By.xpath("//*[@id=\"newShippingAddressFormFromIdentity\"]/div[1]/div/form/div[6]/span/span/input")).click();
        File srcFile9 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile9, new File("./Test3_SS/3-9.jpg"));

        driver.findElement(By.xpath("//*[@id=\"AVS\"]/div[2]/form/div/div[2]/div/div/div/span/input")).click();

        driver.findElement(By.xpath("//*[@id=\"shippingOptionFormId\"]/div[1]/div[2]/div/span[1]/span/input")).click();
        File srcFile10 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile10, new File("./Test3_SS/3-10.jpg"));

        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"ccName\"]")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id=\"addCreditCardNumber\"]")).sendKeys(card);
        driver.findElement(By.xpath("//*[@id=\"form-add-credit-card\"]/div[3]/div[4]/span[1]/span/span/button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"form-add-credit-card\"]/div[3]/div[4]/span[2]/span/span/button")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"2_dropdown_combobox\"]/li[5]/a")).click();
        String cardYear = driver.findElement(By.xpath("//*[@id=\"form-add-credit-card\"]/div[3]/div[4]/span[2]/span/span/button/span")).getText();
        messagePrinter(cardYear, "2023", logger);
        driver.findElement(By.xpath("//*[@id=\"ccAddCard\"]")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"existing-credit-cards-box\"]/div[3]")).click();
        Thread.sleep(500);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys(Keys.ENTER).perform();
        Thread.sleep(3000);
        File srcFile11 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile11, new File("./Test3_SS/3-11.jpg"));

        driver.findElement(By.xpath("//*[@id=\"address-book-entry-0\"]/div[2]/span/a")).click();
        File srcFile13 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile13, new File("./Test3_SS/3-13.jpg"));
        driver.quit();
        logger.pass("Shopping Part is Pass");
    }

    @Test
    public void z_removeCard() throws InterruptedException {
        logger = report.createTest("Remove Test");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Gauri\\Documents\\Software_QA\\testPractise\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.amazon.com/");
        driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"ap_email\"]")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"ap_password\"]")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"signInSubmit\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"nav-link-accountList\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"a-page\"]/div[2]/div/div[3]/div[2]/a/div")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"cpefront-mpo-widget\"]/div/form/div[1]/div/div[2]/div/a")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"a-autoid-2\"]/span/input")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"a-popover-content-3\"]/div/form[2]/div[3]/div/span[2]/span/input")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@id=\"a-popover-4\"]/div/header/button")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"nav-cart\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[@class='a-size-small sc-action-delete']")).click();
        Thread.sleep(500);
        String countCart = driver.findElement(By.xpath("//*[@id=\"nav-cart-count\"]")).getText();
        messagePrinter(countCart, "0", logger);
        driver.quit();
        logger.pass("Remove Part is Pass");
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



