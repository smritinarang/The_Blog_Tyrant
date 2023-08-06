package thedailyscribbles.model;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;


/**
 * Class representing a Community.
 * 
 * @author The Daily Scribbles Team
 *
 */
@Entity
@Table(name="community_details")
public class Community {
	
	/**
	 * unique identifier of the community
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer communityId;
	
	
	/**
	 * unique name of the community.
	 */
	@Column(name="community_name",nullable=false,unique=true)
	@NotBlank(message= "Name field cannot be empty!")
	private String communityName;
	
	/**
	 * description of the community.
	 */
	private String communityDescription;
	
	/**
	 * total members in this community.
	 */
	private Integer totalMembers;
	
	/**
	 * total members of this community who are active at any particular instance.
	 */
	private Integer onlineMembers;
	
	
	/**
	 * profile image for the community.
	 */
	private File image;
	
	
	/**
	 * date on which the community was created.
	 */
	private LocalDate createdOn;
	
	/**
	 * allowed posting rules set up by the admin.
	 */
	private List<String> postRulesAllowed;
	
	/**
	 * disallowed posting rules set up by the admin.
	 */
	private List<String> postRulesDisallowed;
	
	/**
	 * banning policy set up by the admin.
	 */
	private List<String> banningPolicy;
	
	//flairs
	private List<String> flairs;
	
	
	/**
	 * list of bloggers who are part of the community.
	 */
	@ManyToMany(mappedBy="communities")
	private List<Blogger> members;
	
	
	//Constructors
	public Community() {
		super();
		this.totalMembers = 0;
		this.onlineMembers = 0;
	}
	
	public Community(String communityName,String communityDescription) {
		this.communityName = communityName;
		this.communityDescription = communityDescription;
	}
	
	
	//Getters and Setters
	public String getCommunityName() {
		return communityName;
	}

	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	public Integer getCommunityId() {
		return communityId;
	}

	public void setCommunityId(Integer communityId) {
		this.communityId = communityId;
	}

	public String getCommunityDescription() {
		return communityDescription;
	}

	public void setCommunityDescription(String communityDescription) {
		this.communityDescription = communityDescription;
	}

	public Integer getTotalMembers() {
		return totalMembers;
	}

	public void setTotalMembers(Integer totalMembers) {
		this.totalMembers = totalMembers;
	}

	public Integer getOnlineMembers() {
		return onlineMembers;
	}

	public void setOnlineMembers(Integer onlineMembers) {
		this.onlineMembers = onlineMembers;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}

	public List<String> getPostRulesAllowed() {
		return postRulesAllowed;
	}

	public void setPostRulesAllowed(List<String> postRulesAllowed) {
		this.postRulesAllowed = postRulesAllowed;
	}

	public List<String> getPostRulesDisallowed() {
		return postRulesDisallowed;
	}

	public void setPostRulesDisallowed(List<String> postRulesDisallowed) {
		this.postRulesDisallowed = postRulesDisallowed;
	}

	public List<String> getBanningPolicy() {
		return banningPolicy;
	}

	public void setBanningPolicy(List<String> banningPolicy) {
		this.banningPolicy = banningPolicy;
	}

	public List<String> getFlairs() {
		return flairs;
	}

	public void setFlairs(List<String> flairs) {
		this.flairs = flairs;
	}
	
	

	public List<Blogger> getMembers() {
		return members;
	}

	public void setMembers(List<Blogger> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Community [communityId=" + communityId + ", communityDescription=" + communityDescription
				+ ", totalMembers=" + totalMembers + ", onlineMembers=" + onlineMembers
				+ ", createdOn=" + createdOn + ", postRulesAllowed=" + postRulesAllowed + ", postRulesDisallowed="
				+ postRulesDisallowed + ", banningPolicy=" + banningPolicy + ", flairs=" + flairs + "]";
	}
	
	
	
}
