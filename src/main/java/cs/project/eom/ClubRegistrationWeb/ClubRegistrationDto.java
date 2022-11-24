package cs.project.eom.ClubRegistrationWeb;

import java.util.Date;

import javax.persistence.*;

// https://howtodoinjava.com/spring-boot2/spring-boot-crud-hibernate/

@Entity
@Table(name = "club_registration")
public class ClubRegistrationDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userEmail;

	public enum ClubNameOption {

		RAINBOW_CLUB("Rainbow Club"),
	    ICP_CLUB("International Certificate Program (ICP)"),
	    IMPROV_CLUB("Improv Club"),
	    EARL_READS_BOOK_CLUB("Earl Reads Book Club"),
	    LIONS_ROAR_NEWSPAPER_CLUB("Lion's Roar Newspaper"),
	    ANIMAL_WELFARE_CLUB("Animal Welfare Club"),
	    HOSA_CLUB("HOSA (Future Health Professionals)"),
	    MEDLIFE_CLUB("MEDLIFE"),
	    OTHER_CLUB("Other");
	    public final String label;

	    private ClubNameOption(String label) {
	        this.label = label;
	    }
	    public String getLabel() {
	    	return label;
	    }
	}
   
	public enum ClubRegisterStatus {

		SUBMITTED("Submitted"),
	    APPROVLED("Approvled"),
	    REFUSED("Refused"),
		CANCELLED("Cancelled");

	    public final String label;

	    private ClubRegisterStatus(String label) {
	        this.label = label;
	    }
	    public String getLabel() {
	    	return label;
	    }
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private ClubRegisterStatus clubRegisterStatus;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
    private ClubNameOption clubNameOption;

    @Column
    private String otherClubName;

    @Column(nullable = false)
	private String clubExecutiveTeamMembersNames;

    @Column(nullable = false)
	private String clubExecutiveTeamMembersEmails;

    @Column(nullable = false)
	private String clubPresidentsNames;

    @Column(nullable = false)
	private String clubPresidentsEmails;

    @Column(nullable = false)
	private String clubPresidentsInstagram;

    @Column(nullable = false)
	private String clubPurpose;

    @Column(nullable = false)
	private String clubMeetingDescription;

    @Column(nullable = false)
	private String whoCanJoin;

    @Column(nullable = false)
	private String clubLocation;

    @Column(nullable = false)
	private String clubMeetInterval;

    @Column
	private String googleClassroomCode;

    @Column
	private String clubSocialMediaInfo;

    @Column(nullable = false)
	private String supervisorName;

    @Column(nullable = false)
	private String supervisorEmail;

    @Column
	private String note;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedDate;
    
    @PrePersist
    protected void onCreate() {
    	updatedDate = createdDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    	updatedDate = new Date();
    }
 
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public ClubRegisterStatus getClubRegisterStatus() {
		return clubRegisterStatus;
	}

	public void setClubRegisterStatus(ClubRegisterStatus clubRegisterStatus) {
		this.clubRegisterStatus = clubRegisterStatus;
	}

	public ClubNameOption getClubNameOption() {
		return clubNameOption;
	}

	public void setClubNameOption(ClubNameOption clubNameOption) {
		this.clubNameOption = clubNameOption;
	}

	public String getOtherClubName() {
		return otherClubName;
	}

	public void setOtherClubName(String otherClubName) {
		this.otherClubName = otherClubName;
	}

	public String getClubExecutiveTeamMembersNames() {
		return clubExecutiveTeamMembersNames;
	}

	public void setClubExecutiveTeamMembersNames(String clubExecutiveTeamMembersNames) {
		this.clubExecutiveTeamMembersNames = clubExecutiveTeamMembersNames;
	}

	public String getClubExecutiveTeamMembersEmails() {
		return clubExecutiveTeamMembersEmails;
	}

	public void setClubExecutiveTeamMembersEmails(String clubExecutiveTeamMembersEmails) {
		this.clubExecutiveTeamMembersEmails = clubExecutiveTeamMembersEmails;
	}

	public String getClubPresidentsNames() {
		return clubPresidentsNames;
	}

	public void setClubPresidentsNames(String clubPresidentsNames) {
		this.clubPresidentsNames = clubPresidentsNames;
	}

	public String getClubPresidentsEmails() {
		return clubPresidentsEmails;
	}

	public void setClubPresidentsEmails(String clubPresidentsEmails) {
		this.clubPresidentsEmails = clubPresidentsEmails;
	}

	public String getClubPresidentsInstagram() {
		return clubPresidentsInstagram;
	}

	public void setClubPresidentsInstagram(String clubPresidentsInstagram) {
		this.clubPresidentsInstagram = clubPresidentsInstagram;
	}

	public String getClubPurpose() {
		return clubPurpose;
	}

	public void setClubPurpose(String clubPurpose) {
		this.clubPurpose = clubPurpose;
	}

	public String getClubMeetingDescription() {
		return clubMeetingDescription;
	}

	public void setClubMeetingDescription(String clubMeetingDescription) {
		this.clubMeetingDescription = clubMeetingDescription;
	}

	public String getWhoCanJoin() {
		return whoCanJoin;
	}

	public void setWhoCanJoin(String whoCanJoin) {
		this.whoCanJoin = whoCanJoin;
	}

	public String getClubLocation() {
		return clubLocation;
	}

	public void setClubLocation(String clubLocation) {
		this.clubLocation = clubLocation;
	}

	public String getClubMeetInterval() {
		return clubMeetInterval;
	}

	public void setClubMeetInterval(String clubMeetInterval) {
		this.clubMeetInterval = clubMeetInterval;
	}

	public String getGoogleClassroomCode() {
		return googleClassroomCode;
	}

	public void setGoogleClassroomCode(String googleClassroomCode) {
		this.googleClassroomCode = googleClassroomCode;
	}

	public String getClubSocialMediaInfo() {
		return clubSocialMediaInfo;
	}

	public void setClubSocialMediaInfo(String clubSocialMediaInfo) {
		this.clubSocialMediaInfo = clubSocialMediaInfo;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getSupervisorEmail() {
		return supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
