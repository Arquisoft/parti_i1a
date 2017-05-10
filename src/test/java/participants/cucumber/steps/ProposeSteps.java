package participants.cucumber.steps;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import participants.cucumber.SeleniumDriverFactory;
import uniovi.asw.hello.Application;

@Ignore
@RunWith(Cucumber.class)
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProposeSteps {

	private static RemoteWebDriver driver = SeleniumDriverFactory.getDriver();

	@When("^i write \"([^\"]*)\" as the proposal title$")
	public void i_write_proposal_title(String title) {
		driver.findElement(By.id("title")).clear();
		driver.findElement(By.id("title")).sendKeys(title);
	}

	@When("^i write \"([^\"]*)\" as the proposal description$")
	public void i_write_proposal_description(String description) {
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description"))
				.sendKeys(description);
	}

	@When("^i set \"([^\"]*)\" as the proposal topic$")
	public void i_choose_proposal_topic(String topic) {
		new Select(driver.findElement(By.id("topic")))
				.selectByVisibleText(topic.toUpperCase());
	}
	
	@When("^i submit the proposal$") 
	public void i_submit_the_proposal() {
	    driver.findElementById("submit").click();
	}
	
	@Then("^a proposal with title \"([^\"]*)\" will be published$")
	public void the_proposal_will_be_published(String title) {
		driver.findElementsByXPath("//*[.='" + title + "']");
	}
}