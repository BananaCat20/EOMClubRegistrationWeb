package cs.project.eom.ClubRegistrationWeb;

import java.io.Serializable;

public class RegisterForm implements Serializable {
	
	public enum ClubNames {

		RAINBOW_CLUB,
	    ICP_CLUB,
	    IMPROVE_CLUB,
	    EARL_READS_BOOK_CLUB,
	    LIONS_ROAR_NEWSPAPER_CLUB,
	    ANIMAL_WELFARE_CLUB,
	    HOSA_CLUB,
	    OTHER_CLUB
	}
	private String loginName;
	private String loginEmail;
	private ClubNames clubNameOption;
	private String otherClubName;
	private String clubExecutiveTeamMembersNames;
	private String clubExecutiveTeamMembersEmails;
	private String clubPresidentsNames;
	private String clubPresidentsEmails;
	private String clubPresidentsInstagram;
	private String clubPurpose;
	private String clubMeetingDescription;
	private String whoCanJoin;
	private String clubLocation;
	private String clubMeetInterval;
	private String googleClassroomCode;
	private String clubSocialMediaInfo;
	private String supervisorName;
	private String supervisorEmail;
	private String note;
	
	public ClubNames getClubNameOption() {
		return clubNameOption;
	}
	public void setClubNameOption(ClubNames clubNameOption) {
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
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginEmail() {
		return loginEmail;
	}
	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

}
