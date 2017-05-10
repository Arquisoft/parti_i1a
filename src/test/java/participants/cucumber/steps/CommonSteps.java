package participants.cucumber.steps;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import participants.cucumber.SeleniumDriverFactory;
import uniovi.asw.hello.Application;
import uniovi.asw.services.FillDatabase;

@Ignore
@RunWith(Cucumber.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CommonSteps {

	@Autowired
	FillDatabase fD;

	@After
	public void cleanup() {
		// We give for granted FillDatabase has been called either
		// at application startup or after a previous test.
		//
		// We clean up the database for future tests and/or use.
		fD.fill();
	}

	private static RemoteWebDriver driver = SeleniumDriverFactory
			.getDriver();
	private static String BASE_URL = "http://localhost:8080/";

	@Given("^Im a logged user \"([^\"]*)\" with password \"([^\"]*)\"$")
	public void im_a_logged_user_something_with_password_something(
			String name, String pass) {
		driver.get(BASE_URL + "login");
		driver.findElement(By.id("user")).clear();
		driver.findElement(By.id("user")).sendKeys(name);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.id("loginButton")).click();
	}

	@When("^i open the list of proposals$")
	public void i_open_the_list_of_proposals() {
		WebElement seeProposalsButton = driver
				.findElementById("propo");
		seeProposalsButton.click();
	}

	@When("^i open the proposal \"([^\"]*)\"$")
	public void i_open_the_proposal(String title) {
		WebElement seeProposalButton = driver.findElementByXPath(
				".//*[td='" + title + "']//*[.='See']");
		seeProposalButton.click();
	}

}