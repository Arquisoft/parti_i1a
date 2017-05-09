package uniovi.asw.persistence.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TUsers")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String surname;

	@Column(unique = true, nullable = false)
	private String email;
    private String password;

	@Column(unique = true)
	private String dni;
    private String nationality;
    private String address;
	private Date birth;

    private boolean isAdmin;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Proposal> proposals = new HashSet<Proposal>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Vote> votes = new HashSet<Vote>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Comment> comments = new HashSet<Comment>();

    public User(){}
	
	public User(String name, String surname, String password, String email, String nationality, String dni,
			String address, Date birth) {
		super();
		setName(name);
		setSurname(surname);
		setPassword(password);
		setEmail(email);
		setNationality(nationality);
		setDni(dni);
		setAddress(address);
		setBirth(birth);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		dni = dni;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth_date) {
		this.birth = birth_date;
	}

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	public Set<Proposal> getProposals() {
		return new HashSet<Proposal>(proposals);
	}

	Set<Proposal> _getProposals() {
		return proposals;
	}

	public Set<Comment> getComments() {
		return new HashSet<Comment>(comments);
	}

	Set<Comment> _getComments() {
		return comments;
	}

	Set<Vote> _getVotes() {
		return votes;
	}
	
	public Set<Vote> getVotes() {
		return new HashSet<Vote>(votes);
	}
	
	public void makeProposal(Proposal proposal){
		Association.MakeProposal.link(this,proposal);
	}
	
	public void vote(Vote vote, Votable votable){
		Association.Votation.link(this, vote, votable);
	}
	
	public void comment(Proposal proposal, Comment comment){
		Association.MakeComment.link(this,comment,proposal);
	}
	
	public void deleteProposal(Proposal proposal){
		Association.MakeProposal.unlink(this,proposal);
	}	
	
	public void deleteComment(Proposal proposal, Comment comment){
		Association.MakeComment.unlink(this,comment,proposal);
	}
	
	public void deleteVote(Vote vote, Proposal proposal){
		Association.Votation.unlink(this, vote, proposal);
	}

}
