/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.ctrl;

import alex.jeediary.bus.DiaryEventParticipantService;
import alex.jeediary.bus.DiaryEventService;
import alex.jeediary.bus.TeamMemberService;
import alex.jeediary.ent.DiaryEvent;
import alex.jeediary.ent.TeamMember;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;

/**
 * Controller for the Create Appointment View
 * @author alex
 */
@Named(value = "createEventCtrl")
@SessionScoped
public class CreateEventCtrl implements Serializable {

    private DiaryEvent de = new DiaryEvent();
    private List<TeamMember> teamMembers = new ArrayList<>();
    private String email;

    @EJB
    private DiaryEventService des;

    @EJB
    private TeamMemberService tms;

    @EJB
    private DiaryEventParticipantService deps;

    /**
     * Controller Constructor
     */
    public CreateEventCtrl() {
    }

    /**
     * getter for the page title
     * @return
     */
    public String getTitle() {
        return de.getTitle();
    }

    /**
     * setter for the page title
     * @param title
     */
    public void setTitle(String title) {
        de.setTitle(title);
    }

    /**
     * getter for the event description
     * @return
     */
    public String getDescription() {
        return de.getDescription();
    }

    /**
     * setter for the event description
     * @param description
     */
    public void setDescription(String description) {
        de.setDescription(description);
    }

    /**
     * getter for the event start date
     * @return
     */
    public String getStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(de.getStartDate());
    }

    /**
     * setter for the event start date
     * @param startDate
     */
    public void setStartDate(String startDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        try {
            de.setStartDate(formatter.parse(startDate));
        } catch (ParseException ex) {
            Logger.getLogger(EditEventCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * getter for the event end date
     * @return
     */
    public String getEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(de.getEndDate());
    }

    /**
     * setter for the event end date
     * @param endDate
     */
    public void setEndDate(String endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        try {
            de.setEndDate(formatter.parse(endDate));
        } catch (ParseException ex) {
            Logger.getLogger(EditEventCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * setter for the event creator's id
     * @param id
     */
    public void setCreatorId(String id) {
        de.setCreatorId(Long.parseLong(id));
    }

    /**
     * getter for form email field
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for form email field
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter for team members (participants)
     * @return
     */
    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    @Inject
    private FacesContext facesContext;

    /**
     * Function to check email is valid and add team member to the event
     */
    public void addTeamMember() {
        if (!tms.emailInTeamMemberList(teamMembers, email)) {
            TeamMember tm = tms.findTeamMemberByEmail(email);
            if (tm != null) {
                teamMembers.add(tm);
                email = "";
            } else {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No team member registered with email " + email, null));
            }
        }
    }

    /**
     * Function to remove team member from the event
     * @param tm
     */
    public void removeTeamMember(TeamMember tm) {
        teamMembers.remove(tm);
    }

    /**
     * Function to submit the form, checking for conflicts and creating
     * message if this is the case
     */
    public void submitForm() {

        List<TeamMember> conflicts = deps.findConflicts(teamMembers, de.getStartDate(), de.getEndDate());

        if (conflicts != null && conflicts.isEmpty()) {
            DiaryEvent result = des.createDiaryEvent(de);
            de = result;
            deps.addEventParticipants(teamMembers, result.getId());

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("view-appointment.xhtml?id=" + de.getId());
            } catch (IOException ex) {
                Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            de = new DiaryEvent();
            teamMembers = new ArrayList<>();
            email = "";
            
        } else {
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, deps.generateConflictsMessage(conflicts), null));
        }
    }

    @Inject
    SecurityContext securityContext;

    /**
     * Sets up the form for the user on load
     */
    @PostConstruct
    public void setup() {
        Principal p = securityContext.getCallerPrincipal();
        setCreatorId(p.getName());
        teamMembers.add(tms.getTeamMember(Long.parseLong(p.getName())));
    }

}
