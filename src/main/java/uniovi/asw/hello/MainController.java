package uniovi.asw.hello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.services.ProposalService;

@Controller
@RequestMapping("/")
public class MainController {

    private static final Logger LOGGER = Logger.getLogger(MainController.class);
    private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private ProposalService pService;

    @Autowired
    private ProposalsLiveHandler proposals;

    @RequestMapping(value = { "/admin", "/" })
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
}