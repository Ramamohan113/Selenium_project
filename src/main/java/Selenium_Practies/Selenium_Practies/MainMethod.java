package Selenium_Practies.Selenium_Practies;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class MainMethod {

	public static WebDriver driver = new ChromeDriver();
	public static int tabCount = 0;

	// Verify launching browser
	@Test
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(2000);
		driver.get("https://blazedemo.com/index.php");
	}

	// Verifying expected title with actual title
	@Test
	public static void verifyHomeTitle() {
		String expectedTitle = "Welcome to the Simple Travel Agency!";
		String actualTitle = driver.getTitle();
		// Verifying expected title with actual title
		if (expectedTitle.equals(actualTitle)) {
			System.out.println("Expected title is matching with Actual");
		} else {
			System.out.println("Expected title is NOT matching with Actual, the actual title is : " + actualTitle);
		}

		// Click on the hyperlink
		WebElement link = driver.findElement(By.xpath("//a[contains(@href, 'vacation.html')]"));

		link.click();

	}

	// Verify new tab opening
	@Test
	public static void verifyNewtab() {
		// Checking new tab opening in browser
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		tabCount = tabs.size();

		if (tabCount > 1) {
			System.out.println("New tab has been opened after clicking on hyperlink");
		} else {
			System.out.println("New tab has been NOT opened after clicking on hyperlink");
		}

		// Verify vacation string in URL

		String stringURL = driver.getCurrentUrl();

		if (stringURL.contains("vacation")) {
			System.out.println("Vacation string is present in the URL");
		} else {
			System.out.println("Vacation string is NOT present in the URL");
		}

		// Navigate back to home page

		driver.navigate().back();

		// Select departure city
		WebElement departureDD = driver.findElement(By.xpath("//select[contains(@name, 'fromPort')]"));
		Select selDeparture = new Select(departureDD);
		selDeparture.selectByValue("Mexico City");

		// Select destination city
		WebElement destinationDD = driver.findElement(By.xpath("//select[contains(@name, 'toPort')]"));
		Select selDestination = new Select(destinationDD);
		selDestination.selectByValue("London");

		// Click on find Flights
		WebElement findFlights = driver.findElement(By.xpath("//input[contains(@value, 'Find Flights')]"));
		findFlights.click();

		// Get all flight prices
		ArrayList<Double> priceList = new ArrayList<Double>();

		int flightsCount = driver.findElements(By.xpath("//tbody/tr")).size();

		for (int i = 1; i < flightsCount; i++) {
			String flightPrice = driver.findElement(By.xpath("//tbody/tr[" + i + "]/td[6]")).getText();
			double flightPriceInt = Integer.parseInt(flightPrice);
			priceList.add(flightPriceInt);
		}
		// Find lowest price flight
		int lowestFlight = 0;
		double lowPrice = priceList.get(0);
		for (int i = 1; i < priceList.size(); i++) {
			if (lowPrice > priceList.get(i)) {
				lowPrice = priceList.get(i);
				lowestFlight = i;
			}

			System.out.println("Lowest price in list = " + lowPrice);

		}

		// Select lowest flight from list
		driver.findElement(By.xpath("//tbody/tr[" + lowestFlight + "]/td[1]")).click();

		// Verify purchase page
		String expectedPurchaseTitle = "BlazeDemo Purchase";

		String actualPurchaseTitle = driver.getTitle();

		// Verifying expected title with actual title
		if (expectedPurchaseTitle.equals(actualPurchaseTitle)) {
			System.out.println("Expected title is matching with Actual, so puchase page is opened");
		} else {
			System.out.println(
					"Expected title is NOT matching with Actual, the actual title is : " + actualPurchaseTitle);
		}

		// Verify price format

		String price = driver.findElement(By.xpath("//p[contains(text(),'Price')]")).getText();

		// Click on Purchase Flight
		driver.findElement(By.xpath("//input[contains(@value,'Purchase Flight')]")).click();

		// Purchase Confirmation page
		String expectedConfirmationTitle = "BlazeDemo Confirmation";

		String actualConfirmationTitle = driver.getTitle();

		// Verifying expected title with actual title
		if (expectedConfirmationTitle.equals(actualConfirmationTitle)) {
			System.out.println("Expected title is matching with Actual, so puchase confirmation page is opened");
		} else {
			System.out.println(
					"Expected title is NOT matching with Actual, the actual title is : " + actualConfirmationTitle);
		}

		// Capture booking ID
		String bookingId = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();
		System.out.println("Booking Id : " + bookingId);
	}

}
