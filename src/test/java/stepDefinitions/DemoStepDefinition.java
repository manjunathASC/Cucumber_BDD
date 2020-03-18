package stepDefinitions;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.vimalselvam.cucumber.listener.Reporter;

import actions.DemoActions;
import actions.WpmobilePackActions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.Environment;
import helpers.FeatureData;
import helpers.SharedDriver;
import pageObjects.DemoPageObjects;
import pageObjects.WpmobilePackPageObjects;
import utilities.GetScreenShot;

public class DemoStepDefinition {

	public static final String BROWSER="Chrome"; //values Chrome,FireFox,IE
	public static String env="WpMobile";
	public static WebDriver driver;
	static String scenarioName;
	GetScreenShot screenshot;
	DemoPageObjects demoPO;
	WpmobilePackPageObjects wpPO;
	DemoActions demoA;
	WpmobilePackActions wpAction;
	SharedDriver shdriver;
	WebDriverWait wait;
	Environment environment;

	@Before
	public void browserLaunch(Scenario scenario) throws Exception {	

		System.out.println("Before scenario------------------excecuted");
		shdriver = new SharedDriver(BROWSER);
		driver = shdriver.getDriver();
		System.out.println(driver);
		screenshot = new GetScreenShot();
		scenarioName=scenario.getName();
		environment=new Environment(env);
		wait=new WebDriverWait(driver, 20);

		demoA = new DemoActions(driver);
		wpAction=new WpmobilePackActions(driver);
		demoPO=new DemoPageObjects();
		wpPO=new WpmobilePackPageObjects();

		demoPO = PageFactory.initElements(driver, DemoPageObjects.class);
		wpPO = PageFactory.initElements(driver, WpmobilePackPageObjects.class);

	}

	@Given("^i have privileges to access wpmobilePack site$")
	public void i_have_privileges_to_access_wpmobilePack_site() throws Throwable {
		driver.get(environment.getURL());
		wait.until(ExpectedConditions.visibilityOf(wpPO.homeGrid));
		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName));
	}

	@When("^i select product \"([^\"]*)\" from home$")
	public void i_select_product_from_home(String product) throws Throwable {
		wpAction.shopClick(product);
		wait.until(ExpectedConditions.visibilityOf(wpPO.addCartBtn));
		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName));
		Thread.sleep(1000);
	}

	@When("^add the selected product to cart \"([^\"]*)\"$")
	public void add_the_selected_product_to_cart(String notification) throws Throwable {
		wpPO.addCartBtn.click();
		wait.until(ExpectedConditions.visibilityOf(wpPO.notificationMsg));
		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName));
		wpPO.cartBtn.click();
		wait.until(ExpectedConditions.visibilityOf(wpPO.checkout));
		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName));
		wpPO.checkout.click();
		wait.until(ExpectedConditions.visibilityOf(wpPO.firstName));
		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName)); 
	}

	@Then("^go to checkout page , fill the required fields and place the order$")
	public void go_to_checkout_page_fill_the_required_fields_and_place_the_order(List<FeatureData> data) throws Throwable {
		wpPO.firstName.clear();
		wpPO.firstName.sendKeys(data.get(0).firstName);
		wpPO.lastName.clear();
		wpPO.lastName.sendKeys(data.get(0).lastName);
		wpPO.company.clear();
		wpPO.company.sendKeys(data.get(0).companyName);
		wpPO.country.click();
		wpPO.countryTxtField.sendKeys(data.get(0).country);
		wpPO.countrySelect.click();
		wpPO.address1.clear();
		wpPO.address1.sendKeys(data.get(0).address1);
		wpPO.address2.clear();
		wpPO.address2.sendKeys(data.get(0).address2);
		wpPO.city.clear();
		wpPO.city.sendKeys(data.get(0).city);
		wpPO.state.click();
		wpPO.countryTxtField.sendKeys(data.get(0).country);
		wpPO.countrySelect.click();
		wpPO.postCode.clear();
		wpPO.postCode.sendKeys(data.get(0).zip);
		wpPO.phone.clear();
		wpPO.phone.sendKeys(data.get(0).phoneNumber);
		wpPO.email.clear();
		wpPO.email.sendKeys(data.get(0).email);
		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName));
//		Thread.sleep(2000);
//		wpAction.scroll(wpPO.orderBtn);
//		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName));

	}

	@Then("^order has been successfully placed \"([^\"]*)\"$")
	public void order_has_been_successfully_placed(String arg1) throws Throwable {
		//Thread.sleep(2000);
		wpPO.orderBtn.click();
		//Thread.sleep(4000);
		wait.until(ExpectedConditions.visibilityOf(wpPO.orderReceived));
		Reporter.addScreenCaptureFromPath(screenshot.capture(driver, scenarioName)); 
	}

}
