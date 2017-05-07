package uniovi.asw.hello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.services.ProposalService;
import uniovi.asw.services.UserService;

@Controller
@RequestMapping("/")
public class MainController {

	private static final Logger LOGGER = Logger.getLogger(MainController.class);
	private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

	@Autowired
	private ProposalService pService;

	@Autowired
	private UserService uService;

	@Autowired
	private ProposalsLiveHandler proposals;

	@RequestMapping("/admin")
	public String admin() {
		LOGGER.debug("Redirected to live page");
		return "admin";
	}

	@RequestMapping("/viewProposal")
	public String viewProposal(Model model, Long proposalId) {
		// Check the correctness of the parameter
		if (proposalId != null) {
			// Search for the chosen proposal on the database
			Proposal p = pService.findById(proposalId);

			if (p != null) {
				model.addAttribute("proposal", p);
			}
		}
		LOGGER.debug("Redirected to view proposal");
		return "viewProposal";
	}

	@ModelAttribute("proposals")
	public Map<Long, Proposal> getProposals() {
		return proposals.getMap();
	}

	public List<SseEmitter> getSseEmitters() {
		return sseEmitters;
	}

	public void setSseEmitters(List<SseEmitter> sseEmitters) {
		this.sseEmitters = sseEmitters;
	}

	// Login from participants

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("credentials", new UserCredentials());
		return "login";
	}

	@PostMapping(value = "/login", produces = MediaType.TEXT_HTML_VALUE)
	public String handleLogin(@ModelAttribute UserCredentials credentials, Model model, HttpServletRequest request) {
		String email = credentials.getEmail();
		String pass = credentials.getPassword();

		User user = uService.findByEmail(email);

		if (user.getPassword().equals(pass)) {
			model.addAttribute("user", user);
			request.getSession().setAttribute("user", user);
			return "user_info";
		} else {
			model.addAttribute("credentials", new UserCredentials());
			return "login";
		}
	}

	@RequestMapping("/user_info")
	public String getUserInfo(Model model) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession sesion = attr.getRequest().getSession(true); // true ==
																	// allow
																	// create

		User user = (User) sesion.getAttribute("user");

		if (user == null || user.getId() == null) {
			model.addAttribute("credentials", new UserCredentials());
			return "login";
		} else
			return "user_info";
	}

	// Change pasword from participants

	@RequestMapping("/change_password")
	public String changePassword(Model model) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession sesion = attr.getRequest().getSession(true); // true ==
																	// allow
																	// create

		User user = (User) sesion.getAttribute("user");

		if (user == null || user.getId() == null) {
			model.addAttribute("credentials", new UserCredentials());
			return "login";
		} else
			return "change_password";
	}

	@RequestMapping("/changing_password")
	public String changingPassword(HttpServletRequest request, Model model) {

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession sesion = attr.getRequest().getSession(true); // true ==
																	// allow
																	// create

		User user = (User) sesion.getAttribute("user");

		if (user == null || user.getId() == null) {
			model.addAttribute("credentials", new UserCredentials());
			return "login";
		}

		String oldPassword = request.getParameter("oldpass");
		String newPassword = request.getParameter("newpass");
		String newRepeatedPassword = request.getParameter("repeatnewpass");

		String messageForTheUser = "The new password introduced is different in both fields";
		if (newPassword.equals(newRepeatedPassword)) {
			if (user.getPassword().equals(oldPassword)) {
				try {
					user.setPassword(newPassword);
					uService.save(user);

					request.setAttribute("user", user);

					messageForTheUser = "The password has been updated succesfully";
					return "user_info";
				} catch (PersistenceException p) {
					messageForTheUser = "There has been a persistency problem while changing the password";
				}
			}
			messageForTheUser = "The old password introduced is wrong.";
		}

		model.addAttribute("messageForTheUser", messageForTheUser);
		return "change_password";
	}
}