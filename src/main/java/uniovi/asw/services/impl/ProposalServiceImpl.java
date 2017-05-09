package uniovi.asw.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uniovi.asw.persistence.model.Proposal;
import uniovi.asw.persistence.repositories.ProposalRepository;
import uniovi.asw.services.ProposalService;

@Service
public class ProposalServiceImpl implements ProposalService{

	private ProposalRepository repository;

	@Autowired
	public ProposalServiceImpl(ProposalRepository repository) {
		setRepository(repository);
	}
	
	@Override
	public Proposal save(Proposal proposal) {
		return getRepository().save(proposal);
	}

	@Override
	public List<Proposal> findAll() {
		List<Proposal> proposals = new ArrayList<>();
		if (getRepository().findAll() != null) {
			Iterator<Proposal> it = getRepository().findAll().iterator();
			while (it.hasNext())
				proposals.add(it.next());
		}
		return proposals;
	}

	@Override
	public boolean checkExists(Long id) {
		return getRepository().findOne(id) != null;
	}

	private void setRepository(ProposalRepository repository){
		this.repository = repository;
	}
	
	private ProposalRepository getRepository(){
		return this.repository;
	}

	@Override
	public Proposal findById(Long id) {
		return getRepository().findOne(id);
	}

    @Override
    public void clearTable() {
        getRepository().deleteAll();
    }

    @Override
    public void delete(Proposal proposal) {
        getRepository().delete(proposal);
    }

    @Override
    public Proposal findProposalByTitle(String tit) {
        return getRepository().findByTitle(tit);
    }
}
