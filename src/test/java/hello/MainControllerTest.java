package hello;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import uniovi.asw.hello.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
@SuppressWarnings({ "deprecation" })
public class MainControllerTest {

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

	@Test
	public void testError() throws Exception {
		base = new URL("http://localhost:" + port + "/error.html");
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertThat(response.getBody(), containsString("There was an unsupported error"));
		assertThat(response.getBody(), containsString("Contact the admin to get feedback"));
		assertThat(response.getBody(), containsString("Go back"));
	}

	@Test
	public void testGetAdmin() {
		String body = "";
		try {
			MvcResult result = this.mockMvc.perform(post("/admin")
					.accept(MediaType.TEXT_HTML_VALUE)).andExpect(status().isOk()).andReturn();
			body = result.getResponse().getContentAsString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// An HTML
		assertTrue(body.startsWith("<!DOCTYPE HTML>"));
		// Not a JSON
		assertTrue(!body.startsWith("{"));
		assertTrue(!body.endsWith("}"));
		// Not an XML
		assertTrue(!body.startsWith("<user>"));

		assertTrue(body.contains("Votations live broadcast"));
		assertTrue(body.contains("Number"));
		assertTrue(body.contains("Votes"));
		assertTrue(body.contains("Proposal"));
		assertTrue(body.contains("Topic"));
	}

	@Test
	public void testViewProposal() throws Exception {
		base = new URL("http://localhost:" + port + "/viewProposal?proposalId=1");
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		assertThat(response.getBody(), containsString("Title"));
		assertThat(response.getBody(), containsString("Total votes"));
		assertThat(response.getBody(), containsString("Topic"));
		assertThat(response.getBody(), containsString("Minimum votes"));
		assertThat(response.getBody(), containsString("Content"));
		assertThat(response.getBody(), containsString("Comments"));
		assertThat(response.getBody(), containsString("Votes"));
		assertThat(response.getBody(), containsString("User"));
		assertThat(response.getBody(), containsString("Comment"));
	}
}
