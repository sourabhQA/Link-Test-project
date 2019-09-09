package Brokenlink.LinkTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class linkTest {

	static String currentdir = System.getProperty("user.dir");

	public static Properties prop;
	public List<WebElement> firstlist = new ArrayList<WebElement>();
	public List<WebElement> finallist = new ArrayList<WebElement>();

	@Test
	public void Readfile() throws IOException {
		// Reading the url from the properties file

		FileInputStream file = new FileInputStream(currentdir + "/src/main/java/Propertiesfile/File.properties");
		prop = new Properties();
		prop.load(file);

		// Initiating the chrome browser
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get(prop.getProperty("url"));
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		
		//Fetching all the Anchor tag
		List<WebElement> firstlist = driver.findElements(By.tagName("a"));
		
		//Fetching all the Image Tag
        firstlist.addAll(driver.findElements(By.tagName("img")));

		
        //Removing the link and image that does not contains href tag
		for (int i = 0; i < firstlist.size(); i++) {
			if (firstlist.get(i).getAttribute("href") != null
					&& firstlist.get(i).getAttribute("href").contains("http")) {
				finallist.add(firstlist.get(i));

			}

		}
	    //checking the url with http url connection
		for (int j = 0; j < finallist.size(); j++) {

			HttpURLConnection connection = (HttpURLConnection) new URL(finallist.get(j).getAttribute("href"))
					.openConnection();
			connection.connect();
			int S = finallist.size();
			int response = connection.getResponseCode();
			connection.disconnect();
			System.out.println(finallist.get(j).getAttribute("href") + "--------->" + response);

		}
		//Driver close
		driver.quit();

	}
}
