import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BuggyCarsRatingTestcases {

public WebDriver driver;
// user details
public String email = "sritest07@mailinator.com";
public String firstName = "RAM1";
public String lastName = "LAST2";
public String userPassword = "Tester01!" ; 
    

	@BeforeTest
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium Java\\ChromeDriver\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	

	@Test(priority=1)
	public void launchValidateHomePage() throws InterruptedException {
		
		
		driver.manage().deleteAllCookies();
		//implicit wait 5 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://buggy.justtestit.org/");
		
		//Buggy Rating displays on Banner
		Assert.assertEquals(driver.findElement(By.className("navbar-brand")).getText(), "Buggy Rating");
	    //Validate UI Elements in the screen , User id, password, login, Register
		Assert.assertEquals(driver.findElement(By.xpath("//form/div/input[1]")).getAccessibleName(),"Login");
		Assert.assertEquals(driver.findElement(By.xpath("//form/div/input[2]")).getDomAttribute("name"),"password");
		Assert.assertEquals(driver.findElement(By.xpath("//button[normalize-space()='Login']")).getText(), "Login");
		//Validate Login Background color is rgb(92, 184, 92)
		Assert.assertEquals(driver.findElement(By.xpath("//button[normalize-space()='Login']")).getCssValue("Background").contains("rgb(92, 184, 92)"), true);
		// Validate Register button is available
		Assert.assertEquals(driver.findElement(By.xpath("//form/a")).getDomAttribute("href"), "/register");
		//validate Home Page Heading or Hero text 
		Assert.assertEquals(driver.findElement(By.xpath("//div/h1")).getText().replaceAll("[\\n\t ]", ""), "BuggyCarsRating");
		// Validate three tiles are displayed as popular make , popular model and over all rating
		Assert.assertEquals(driver.findElement(By.xpath("//my-home/div/div[1]")).getText().contains("Popular Make"),true );
		Assert.assertEquals(driver.findElement(By.xpath("//h2[normalize-space()='Popular Model']")).getText(),"Popular Model" );
		Assert.assertEquals(driver.findElement(By.xpath("//h2[normalize-space()='Overall Rating']")).getText(),"Overall Rating" );
		// Validate 2016 buggy software inc displays 
		Assert.assertEquals(driver.findElement(By.cssSelector("div[class='container'] p")).getText(), "© 2016 Buggy Software, Inc.");
		Thread.sleep(5000);
		driver.close();
	}
	
	@Test(priority=2)
	public void userRegistration() throws InterruptedException  {
		
			openBrowser();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.manage().deleteAllCookies();;
			driver.get("https://buggy.justtestit.org/");
			// Open User registration page
			driver.findElement(By.xpath("//form/a")).click();
			Assert.assertEquals(driver.findElement(By.xpath("//h2[normalize-space()='Register with Buggy Cars Rating']")).getText(), "Register with Buggy Cars Rating");
			driver.findElement(By.cssSelector("#username")).sendKeys(email);
			driver.findElement(By.cssSelector("#firstName")).sendKeys(firstName);
			driver.findElement(By.cssSelector("#lastName")).sendKeys(lastName);
			driver.findElement(By.cssSelector("#password")).sendKeys(userPassword);
			driver.findElement(By.cssSelector("#confirmPassword")).sendKeys(userPassword);
			driver.findElement(By.xpath("//button[normalize-space()='Register']")).click();
			String result = driver.findElement(By.className("result")).getText();
			if(result.contains("User already exists") == true ) {
				System.out.println("Its an existing user , change email address from top of the code which is declared public and rerun");
				driver.findElement(By.xpath("//a[@role='button']")).click();
			}
			else if (result.contains("Registration is successful") == true) {
				System.out.println("User Sucessfully registered , Can run user login case");
				driver.findElement(By.xpath("//a[@role='button']")).click();
				
			}
			// Validate user is back in Home Page 
			Assert.assertEquals(driver.findElement(By.xpath("//my-home/div/div[1]")).getText().contains("Popular Make"),true );
			Thread.sleep(5000);
			driver.close();
			
			
	}
	
	@Test(priority=3)
	public void userLogin() throws InterruptedException {
		
		openBrowser();
		driver.manage().deleteAllCookies();
		//implicit wait 5 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://buggy.justtestit.org/");
		
		//Enter User id and password 
		driver.findElement(By.xpath("//form/div/input[1]")).clear();
		driver.findElement(By.xpath("//form/div/input[1]")).sendKeys(email);
		driver.findElement(By.xpath("//form/div/input[2]")).clear();
		driver.findElement(By.xpath("//form/div/input[2]")).sendKeys(userPassword);
		// Click on Login and validate login screen 
		driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector(".nav-link.disabled")).getText(),"Hi, "+firstName);
		Thread.sleep(5000);
		
			
	}
	
	
	@Test(priority=4)
	public void profileUpdate() throws InterruptedException {
		
		driver.quit();
		
		userLogin();
		//implicit wait 5 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		
		w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[normalize-space()='Profile']"))));
		driver.findElement(By.xpath("//a[normalize-space()='Profile']")).click();
		String gender, address,phone;
		gender = "Male";
		String age = "25";
		phone = "07834873";
		address = "22 Stoddard Road, Mount Roskill, Auckland 1041";
		
		
		// 
		Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='card'])[1]")).getText().contains("Basic"),true);
		Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='card'])[2]")).getText().contains("Additional Info"),true);
		Assert.assertEquals(driver.findElement(By.xpath("(//div[@class='card'])[3]")).getText().contains("Additional Info"),true);
		
		driver.findElement(By.xpath("//input[@id='gender']")).clear();
		driver.findElement(By.xpath("//input[@id='gender']")).sendKeys(gender);
		driver.findElement(By.cssSelector("#age")).clear();
		driver.findElement(By.cssSelector("#age")).sendKeys(age);
		driver.findElement(By.cssSelector("#address")).clear();
		driver.findElement(By.cssSelector("#address")).sendKeys(address);
		driver.findElement(By.cssSelector("#phone")).clear();
		driver.findElement(By.cssSelector("#phone")).sendKeys(phone);
		WebElement staticDropElement = driver.findElement(By.id("hobby"));
		Select dropdown = new Select(staticDropElement);
		dropdown.selectByIndex(3);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		Assert.assertEquals(driver.findElement(By.className("result")).getText().replaceAll("[\\n\t ]", ""), "Theprofilehasbeensavedsuccessful");
		//Go back to home screen
		driver.findElement(By.className("navbar-brand")).click();
		driver.close();
		
		
	}
	
	@Test(priority=5)
	public void navigationPagesAndVote() throws InterruptedException {
		// This test will navigate to a page popular model and provide vote and validate its coming in comments 
		userLogin();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		String modelName = driver.findElement(By.xpath("//div/a[contains(@href,'/model')]")).getAccessibleName();
		driver.findElement(By.xpath("//div/a[contains(@href,'/model')]")).click();
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class='row'] h3"))));
		
		
		Assert.assertEquals(driver.findElement(By.cssSelector("div[class='row'] h3")).getText(), modelName);
		// Vote and add comments and validate in 
		
		// also check if vote is already made , if made goback to home screen and logout
		String voteButton = ">Vote!</button>";
		if(driver.getPageSource().contains(voteButton)) {
			String voteCount = driver.findElement(By.xpath("//h4/strong")).getText();
			long voteCount1 =  Long.parseLong(voteCount);
			String comment = "Its a part of Test";
			driver.findElement(By.cssSelector("#comment")).sendKeys(comment);
			driver.findElement(By.cssSelector(".btn.btn-success")).click();
			
			long voteCount2 = voteCount1 + 1;
			String voteCount3 = String.valueOf(voteCount2);
			Thread.sleep(8000);
			Assert.assertEquals(driver.findElement(By.xpath("//h4/strong")).getText(), voteCount3);
			System.out.println(driver.findElement(By.xpath("//tbody/tr[1]")).getText());
			Assert.assertEquals(driver.findElement(By.xpath("//tbody/tr[1]")).getText().contains(comment), true);
			
			
		}else {
			
			System.out.println("Vote already made for this car model");
		}
		
		//Goback to Home screen
		driver.findElement(By.className("navbar-brand")).click();
		// Validate User is back to home screen
		Assert.assertEquals(driver.findElement(By.xpath("//my-home/div/div[1]")).getText().contains("Popular Make"),true );
		Assert.assertEquals(driver.findElement(By.xpath("//h2[normalize-space()='Popular Model']")).getText(),"Popular Model" );
		Assert.assertEquals(driver.findElement(By.xpath("//h2[normalize-space()='Overall Rating']")).getText(),"Overall Rating" );
		
		// Logout Application
		driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//form/div/input[1]")).getAccessibleName(),"Login");
		Assert.assertEquals(driver.findElement(By.xpath("//form/div/input[2]")).getDomAttribute("name"),"password");
		Assert.assertEquals(driver.findElement(By.xpath("//button[normalize-space()='Login']")).getText(), "Login");
		driver.quit();
	}
	
}
