package participants.cucumber.steps;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.remote.RemoteWebDriver;
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
public class VoteSteps {

	private static RemoteWebDriver driver = SeleniumDriverFactory.getDriver();

	@When("^i upvote the proposal$")
	public void upvote() {
		driver.findElementById("upvote").click();
	}

	@Then("^the number of positive votes will be \"([^\"]*)\"$")
	public void the_number_of_positive_votes_will_be(String upvotes) {
		driver.findElementByXPath(
				".//*[@id='upVotes' and .='" + upvotes + "']");
	}
}