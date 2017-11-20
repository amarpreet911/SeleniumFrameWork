package New;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by amarpreet911 on 10/18/17.
 */
public class ReadWriteExcel

    {

    WebDriver driver;

    WebDriverWait wait;
    XSSFCell cell;

    @BeforeTest
    public void TestSetup()
        {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            // Set the path of the Firefox driver.
            System.setProperty("webdriver.chrome.driver", "/home/amarpreet911/Documents/Selenium/Selenium_Files/" +
                           "chromedriver");
            driver = new ChromeDriver(chromeOptions);
            // Enter url.
            driver.get("http://www.linkedin.com/");
    //             driver.manage().window().maximize();
            wait = new WebDriverWait(driver,30);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        }

    @Test
    public void ReadData() throws IOException{
       // Import excel sheet.
        String src = "src/main/resources/TestData.xls";
       // Load the file.
            XSSFWorkbook srcBook = new XSSFWorkbook(src);
            XSSFSheet sourceSheet = srcBook.getSheetAt(0);

        System.out.println("the sheet's last row is "+sourceSheet.getLastRowNum());

        for(int i=1; i <= sourceSheet.getLastRowNum(); i++){
                // Import data for Email.
                cell = sourceSheet.getRow(i).getCell(1);//.getRow(i).getCell(1);
              //  cell.setCellType(Cell.CELL_TYPE_STRING);
                driver.findElement(By.id("login-email")).sendKeys(cell.getStringCellValue());
                System.out.println("the number of rows column loop for jenkins");
                // Import data for password.
                cell = sourceSheet.getRow(i).getCell(2);
             //   cell.setCellType(Cell.CELL_TYPE_STRING);
                driver.findElement(By.id("login-password")).sendKeys(cell.getStringCellValue());
        }

        //Writing data:
        try{
              System.out.println("Now in writing for jenkins");
              String result = "you passed";
              cell  = sourceSheet.getRow(1).getCell(2);

              if (cell == null) {
                    cell = sourceSheet.getRow(1).createCell(2);
                    cell.setCellValue(result);
                } else {
                    cell.setCellValue(result);
                }

                // Constant variables Test Data path and Test Data file name
                System.out.println("Now in writing for jenkins_before output stream");
                FileOutputStream fileOutput = new FileOutputStream(src);
            System.out.println("Now in writing for jenkins_before output stream111");
                srcBook.write(fileOutput);
            System.out.println("Now in writing for jenkins_before output stream222");
                fileOutput.flush();
                fileOutput.close();
        }catch(Exception e){
           System.out.println("the exception is "+e);
        }
    }
}
