package com.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @BeforeTest
    public void setupDriver(ITestContext ctx) throws MalformedURLException {
        // BROWSER => chrome / firefox
        // HUB_HOST => localhost / 10.0.1.3 / hostname

        String host = "localhost";
        DesiredCapabilities dc;

        if(System.getProperty("BROWSER") != null &&
                System.getProperty("BROWSER").equalsIgnoreCase("firefox")){
            dc = DesiredCapabilities.firefox();
        }else{
            dc = DesiredCapabilities.chrome();
        }

        if(System.getProperty("HUB_HOST") != null){
            host = System.getProperty("HUB_HOST");
        }

        String testName = ctx.getCurrentXmlTest().getName();

        String completeUrl = "http://" + host + ":4444/wd/hub";
        dc.setCapability("name", testName);
        if(System.getProperty("local") != null) {
        		if(System.getProperty("local").equalsIgnoreCase("true")) {
        			
        			this.driver = createDriverLocally();
        		} 
        	
        }
        else {
        	
        	this.driver = new RemoteWebDriver(new URL(completeUrl), dc);
        }
        
    }

    private WebDriver createDriverLocally() {
		
    	System.setProperty("webdriver.chrome.driver", "/Users/skoduru/Downloads/chromedriver_new");
    	return new ChromeDriver();
    	
		//return null;
	}

	@AfterTest
    public void quitDriver(){
        this.driver.quit();
    }



}
