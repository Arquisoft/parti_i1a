package participants.cucumber;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumDriverFactory {
	
	private static class CustomFirefoxDriver extends FirefoxDriver {
		private boolean hasQuit;

		@Override
		public void quit() {
			super.quit();
			hasQuit = true;
		}
		
		public boolean hasQuit() {
			return hasQuit;
		}
		
		public CustomFirefoxDriver(FirefoxBinary binary, FirefoxProfile profile) {
			super(binary, profile);
		}
	}
	
	private static CustomFirefoxDriver driver;

	public static RemoteWebDriver getDriver() {
		if (driver == null || driver.hasQuit()) {
			setUp();
		}
		return driver;
	}
	
	
	private static void setUp() {
		FirefoxBinary ffBinary = null;
		if (SystemUtils.IS_OS_WINDOWS) {
			ffBinary = new FirefoxBinary(
					new File("..\\FirefoxPortable\\FirefoxPortable.exe"));
		}

		FirefoxProfile firefoxProfile = new FirefoxProfile();
		driver = new CustomFirefoxDriver(ffBinary, firefoxProfile);
		driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
	}
}
