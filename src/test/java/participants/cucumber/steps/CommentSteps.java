package participants.cucumber.steps;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
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
public class CommentSteps {
	
	private static RemoteWebDriver driver = SeleniumDriverFactory.getDriver();

	@When("^i write \"([^\"]*)\" as comment body$") 
	public void i_write_comment_body(String commentBody) {
		driver.findElement(By.id("contentInput")).clear();
        driver.findElement(By.id("contentInput")).sendKeys(commentBody);
	}
	
	@When("^i submit the comment$") 
	public void i_submit_the_comment() {
		driver.findElementById("SubmitComment").click();
	}
	
	@Then("^a comment wîth the body \"([^\"]*)\" will be published$")
	public void a_comment_will_be_published(String commentBody) {
		driver.findElementsByXPath("//*[.='" + commentBody + "']");
	}
}