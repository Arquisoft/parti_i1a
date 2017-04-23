package ccmber.steps;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.util.List;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uniovi.asw.hello.MainController;

@SuppressWarnings({"deprecation","unused"})
public class ProposalSteps {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @Value("${local.server.port}")
    private int port;

    private URL base;
    private RestTemplate template;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
	this.base = new URL("http://localhost:" + port);
	template = new TestRestTemplate();
	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Given("^a list of proposals:$")
    public void a_list_of_proposals(List<Prop> proposals) throws Throwable {
	for (Prop p : proposals) {
	    LOG.debug("Inserting proposal..." + p.name + " - " + p.id);
	}
    }

    @When("I enter in a proposal$")
    public void entring_proposal() throws Throwable {
	LOG.debug("Entering a proposal");
    }
    
    @Given("^I am redirected to its proposal view page$")
    public void redirection() throws Throwable {
	LOG.debug("Entering a proposal");
    }
    
    @Then("^I can see the \"([^\"]*)\"$")
    public void i_can_see(String text) throws Throwable {
	LOG.debug("Checking text present: " + text);
	String body = "";
	try {
	    	text = body;
		MvcResult result = this.mockMvc.perform(post("/viewProposal?id=1")
				.accept(MediaType.TEXT_HTML_VALUE)).andExpect(status().isOk()).andReturn();
		body = result.getResponse().getContentAsString();

	} catch (Exception e) {
		e.printStackTrace();
	}
    }
    
//    I cannot see other proposals
    @But("^I cannot see other proposals$")
    public void but_cannot_see() throws Throwable {
	LOG.debug("Checking text not present");
	String body = "";
	try {
		MvcResult result = this.mockMvc.perform(post("/admin")
				.accept(MediaType.TEXT_HTML_VALUE)).andExpect(status().isOk()).andReturn();
		body = result.getResponse().getContentAsString();

	} catch (Exception e) {
		e.printStackTrace();
	}
	assertFalse(body.contains("Another proposal"));
    }

    public static class Prop {
	private String name;
	private Long id;
    }
}
