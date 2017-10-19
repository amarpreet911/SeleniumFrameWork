package main.java.New;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

            // Set the path of the Firefox driver.
            System.setProperty("webdriver.gecko.driver", "/home/amarpreet911/Documents/Selenium/Selenium_Files/" +
                           "geckodriver");
            driver = new FirefoxDriver();
            // Enter url.
            driver.get("http://www.linkedin.com/");
    //             driver.manage().window().maximize();
            wait = new WebDriverWait(driver,30);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        }

    @Test
    public void ReadData() throws IOException{
       // Import excel sheet.
        String src = "/home/amarpreet911/Documents/Selenium/Selenium_Workspace/SeleniumFrameWork/src/main/resources/TestData.xls";
       // Load the file.
            XSSFWorkbook srcBook = new XSSFWorkbook(src);
            XSSFSheet sourceSheet = srcBook.getSheetAt(0);

        System.out.println("the sheet's last row is "+sourceSheet.getLastRowNum());

        for(int i=1; i <= sourceSheet.getLastRowNum(); i++){
                // Import data for Email.
                cell = sourceSheet.getRow(i).getCell(1);//.getRow(i).getCell(1);
              //  cell.setCellType(Cell.CELL_TYPE_STRING);
                driver.findElement(By.id("login-email")).sendKeys(cell.getStringCellValue());

                // Import data for password.
                cell = sourceSheet.getRow(i).getCell(2);
             //   cell.setCellType(Cell.CELL_TYPE_STRING);
                driver.findElement(By.id("login-password")).sendKeys(cell.getStringCellValue());
        }

        //Writing data:
        try{
              String result = "you passed";
              cell  = sourceSheet.getRow(1).getCell(2);

              if (cell == null) {
                    cell = sourceSheet.getRow(1).createCell(2);
                    cell.setCellValue(result);
                } else {
                    cell.setCellValue(result);
                }

                // Constant variables Test Data path and Test Data file name
                FileOutputStream fileOutput = new FileOutputStream(src);
                srcBook.write(fileOutput);
                fileOutput.flush();
                fileOutput.close();
        }catch(Exception e){
            throw (e);
        }
    }
}
