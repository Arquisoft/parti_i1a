package uniovi.asw.hello;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import uniovi.asw.persistence.model.Comment;
import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.model.User;
import uniovi.asw.persistence.model.Vote;
import uniovi.asw.persistence.model.types.Topic;
import uniovi.asw.persistence.model.types.VoteType;
import uniovi.asw.services.CommentService;
import uniovi.asw.services.ProposalService;
import uniovi.asw.services.UserService;
import uniovi.asw.services.VoteService;

@Controller
@RequestMapping("/")
public class MainController {

	private static final Logger LOGGER = Logger.getLogger(MainController.class);
//	private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

	@Autowired
	private ProposalService pService;

	@Autowired
	private VoteService vService;

	@Autowired
	private CommentService cService;

	@Autowired
	private UserService uService;

	// @Autowired
	// private ProposalsLiveHandler proposals;

	@GetMapping("/admin")
	public String landing() {
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

	/**
	 * @ModelAttribute("proposals") public Map<Long, Proposal> getProposals() {
	 * return proposals.getMap(); }
	 * 
	 * public List<SseEmitter> getSseEmitters() { return sseEmitters; }
	 * 
	 * public void setSseEmitters(List<SseEmitter> sseEmitters) {
	 * this.sseEmitters = sseEmitters; }
	 */
	// Login from participants

	@RequestMapping({ "/login", "/" })
	public String login(Model model) {
		model.addAttribute("loginUser", new User());
		return "login";
	}

	@RequestMapping("/loginCheck")
	public ModelAndView handleLogin(Model model, @ModelAttribute User loginUser, HttpServletRequest request) {
		String email = loginUser.getEmail();
		String pass = loginUser.getPassword();

		User user = uService.findByEmail(email);
		if (user == null)
			user = (User) request.getSession().getAttribute("user");
		if (user.getPassword().equals(pass)) {
			model.addAttribute("user", user);
			request.getSession().setAttribute("user", user);

			if (user.isAdmin())
				return new ModelAndView("redirect:/admin");
			else
				return new ModelAndView("redirect:/user_info");
		} else {
			model.addAttribute("loginUser", new User());
			return new ModelAndView("redirect:/login");
		}

	}

	@RequestMapping("/user_info")
	public String getUserInfo(Model model) {

		User user = getSessionUser();
//		user = (User) model.asMap().get("user");

		if (user == null || user.getId() == null) {
			model.addAttribute("loginUser", new User());
			return "login";
		} else{
			model.addAttribute("user", user);
			return "user_info";
		}
	}

	// Change pasword from participants

	@RequestMapping("/change_password")
	public String changePassword(Model model) {

		User user = getSessionUser();

		if (user == null || user.getId() == null) {
			model.addAttribute("credentials", new UserCredentials());
			return "login";
		} else
			return "change_password";
	}

	@RequestMapping("/changing_password")
	public String changingPassword(HttpServletRequest request, Model model) {

		User user = getSessionUser();

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
		return "user_info";
	}

	@RequestMapping("/see_proposals")
	public String user(Model model) {
		model.addAttribute("createProposal", new Proposal());
		return "user";
	}

	@RequestMapping("/createProposal")
	public ModelAndView createProposal(Model model, @ModelAttribute Proposal createProposal) {
		
		model.asMap().get("user");

		User user = getSessionUser();
		
		Proposal proposal = new Proposal(user, createProposal.getTitle(), createProposal.getDescription(),
				createProposal.getTopic());

        if (proposal.checkNotAllowedWords()) {
            pService.makeProposal(proposal);
        }
        
        return new ModelAndView("redirect:/see_proposals");
    }

    @RequestMapping("/deleteProposal/{id}")
    public String deleteProposal(Model model, @PathVariable("id") Long id) {
        Proposal p = pService.findById(id);
        pService.remove(p);
        return "redirect:/admin";
    }

    @RequestMapping("/selectProposal/{id}")
    public String selectProposal(Model model, @PathVariable Long id) {
        model.addAttribute("p", pService.findById(id));
        model.addAttribute("createComment", new Comment());
        return "proposal";
    }

    @RequestMapping("/admin/selectProposal/{id}")
    public String selectProposalAdmin(Model model, @PathVariable Long id) {
        model.addAttribute("p", pService.findById(id));
        model.addAttribute("createComment", new Comment());
        return "admin_proposal";
    }
    
    @RequestMapping("/upvoteProposal/{id}")
    public String upvoteProposal(Model model, @PathVariable("id") Long id) {

        User user = getSessionUser();
        Proposal prop = pService.findById(id);

        if (prop != null && user != null) {
            Vote v = vService.findVoteByUserByVotable(user, prop);

			if (v != null) {
				vService.undoVote(v);

				if (v.getVoteType() != VoteType.POSITIVE) {
					v = new Vote(user, prop, VoteType.POSITIVE);
					vService.makeVote(v);
				}
			} else {
				v = new Vote(user, prop, VoteType.POSITIVE);
				vService.makeVote(v);
			}

			pService.save(prop);
		}

		return "redirect:/selectProposal/" + id;
	}

	@RequestMapping("/downvoteProposal/{id}")
	public String downvoteProposal(Model model, @PathVariable("id") Long id) {

		User user = getSessionUser();
		Proposal prop = pService.findById(id);

		if (prop != null && user != null) {
			Vote v = vService.findVoteByUserByVotable(user, prop);

			if (v != null) {
				vService.undoVote(v);

				if (v.getVoteType() != VoteType.NEGATIVE) {
					v = new Vote(user, prop, VoteType.NEGATIVE);
					vService.makeVote(v);
				}
			} else {
				v = new Vote(user, prop, VoteType.NEGATIVE);
				vService.makeVote(v);
			}

			pService.save(prop);
		}

		return "redirect:/selectProposal/" + id;
	}

	@RequestMapping("/createComment/{id}")
	public String commentProposal(Model model, @PathVariable("id") Long id, @ModelAttribute Comment createComment) {

		User user = getSessionUser();
		Proposal p = pService.findById(id);

		Comment comment = new Comment(createComment.getContent(), user, p);

        cService.makeComment(comment);
        return "redirect:/selectProposal/" + id;
    }

	@RequestMapping("/upvoteComment/{proposalId}/{id}")
	public String upvoteComment(Model model, @PathVariable("proposalId") Long proposalId, @PathVariable("id") Long id) {

		User user = getSessionUser();
		Comment c = cService.findByProposalAndId(proposalId, id);

		if (c != null && user != null) {
			Vote v = vService.findVoteByUserByVotable(user, c);

			if (v != null) {
				vService.undoVote(v);

				if (v.getVoteType() != VoteType.POSITIVE) {
					v = new Vote(user, c, VoteType.POSITIVE);
					vService.makeVote(v);
				}
			} else {
				v = new Vote(user, c, VoteType.POSITIVE);
				vService.makeVote(v);
			}

			cService.updateComment(proposalId, c);
		}

		return "redirect:/selectProposal/" + proposalId;
	}

	@RequestMapping("/downvoteComment/{proposalId}/{id}")
	public String downvoteComment(Model model, @PathVariable("proposalId") Long proposalId,
			@PathVariable("id") Long id) {

		User user = getSessionUser();
		Comment c = cService.findByProposalAndId(proposalId, id);

		if (c != null && user != null) {
			Vote v = vService.findVoteByUserByVotable(user, c);

			if (v != null) {
				vService.undoVote(v);

				if (v.getVoteType() != VoteType.NEGATIVE) {
					v = new Vote(user, c, VoteType.NEGATIVE);
					vService.makeVote(v);
				}
			} else {
				v = new Vote(user, c, VoteType.NEGATIVE);
				vService.makeVote(v);
			}

			cService.updateComment(proposalId, c);
		}

		return "redirect:/selectProposal/" + proposalId;
	}

	@ModelAttribute("proposals")
	public List<Proposal> proposals() {
		return pService.findAll();
	}

	@ModelAttribute("topics")
	public Topic[] topics() {
		return Topic.values();
	}

	private User getSessionUser() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession sesion = attr.getRequest().getSession(true); // true ==
																	// allow
																	// create
		return (User) sesion.getAttribute("user");
	}

}