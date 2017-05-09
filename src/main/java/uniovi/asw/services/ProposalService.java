package uniovi.asw.services;

import java.util.List;

import uniovi.asw.persistence.model.Proposal;

public interface ProposalService {

	Proposal save(Proposal proposal);

	/**
	 * Saves a proposal and sends a Kafka notification
	 * @return saved proposal
	 */
	Proposal makeProposal(Proposal proposal);
	
	boolean checkExists(Long id);

	List<Proposal> findAll();

	Proposal findById(Long id);

	void delete(Proposal proposal);

	/**
	 * Deletes a proposal and sends a Kafka notification
	 * @param proposal 
	 */
	void remove(Proposal proposal);
	
	Proposal findProposalByTitle(String tit);

	void clearTable();

}
