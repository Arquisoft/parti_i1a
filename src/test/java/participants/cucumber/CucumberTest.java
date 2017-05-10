package participants.cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/participants")
public class CucumberTest {
	@AfterClass
	public static void tearDown() {
		SeleniumDriverFactory.getDriver().quit();
	}
}