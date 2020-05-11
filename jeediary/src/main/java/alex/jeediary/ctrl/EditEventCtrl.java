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
import alex.jeediary.ent.DiaryEventParticipant;
import alex.jeediary.ent.TeamMember;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controller for Edit Appointment View
 * @author alex
 */
@Named(value = "editEventCtrl")
@SessionScoped
public class EditEventCtrl implements Serializable {

    private Long queryId;
    private DiaryEvent de = new DiaryEvent();
    private String header;
    private String subHeader;
    private boolean eventFound = false;
    private boolean formVisible = true;
    private List<DiaryEventParticipant> participants = new ArrayList<>();
    private List<TeamMember> teamMembers = new ArrayList<>();
    private String email;

    @EJB
    private DiaryEventService des;

    @EJB
    private TeamMemberService tms;

    @EJB
    private DiaryEventParticipantService deps;

    /**
     *
     */
    public EditEventCtrl() {
    }

    /**
     *
     * @return
     */
    public Object getQueryId() {
        return queryId;
    }

    /**
     *
     * @param id
     */
    public void setQueryId(String id) {
        this.queryId = Long.parseLong(id);
    }

    /**
     *
     * @return
     */
    public List<TeamMember> getTeamMembers() {
        return teamMembers;
    }

    /**
     *
     * @param teamMembers
     */
    public void setTeamMembers(List<TeamMember> teamMembers) {
        this.teamMembers = teamMembers;
    }

    /**
     *
     * @return
     */
    public boolean getEventFound() {
        return eventFound;
    }

    /**
     *
     * @return
     */
    public boolean getEventNotFound() {
        return !eventFound;
    }

    /**
     *
     * @return
     */
    public boolean getFormVisible() {
        return formVisible;
    }

    /**
     *
     * @return
     */
    public String getHeader() {
        return header;
    }

    /**
     *
     * @return
     */
    public String getSubHeader() {
        return subHeader;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return de.getTitle();
    }

    /**
     *
     * @param title
     */
    public void setTitle(String title) {
        de.setTitle(title);
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return de.getDescription();
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        de.setDescription(description);
    }

    /**
     *
     * @return
     */
    public String getStartDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(de.getStartDate());
    }

    /**
     *
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
     *
     * @return
     */
    public String getEndDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatter.format(de.getEndDate());
    }

    /**
     *
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
     *
     * @param id
     */
    public void setCreatorId(String id) {
        de.setCreatorId(Long.parseLong(id));
    }

    @Inject
    private FacesContext facesContext;

    /**
     *
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
     *
     * @param tm
     */
    public void removeTeamMember(TeamMember tm) {
        teamMembers.remove(tm);
    }

    /**
     *
     */
    public void submitForm() {
        if (eventFound) {
            List<TeamMember> additions = deps.findTeamMembersNotInParticipants(teamMembers, participants);
            List<TeamMember> conflicts = deps.findConflicts(additions, de.getStartDate(), de.getEndDate());
            if (conflicts != null && conflicts.isEmpty()) {
                List<DiaryEventParticipant> removals = deps.findParticipantsNotInTeamMembers(teamMembers, participants);
                des.updateDiaryEvent(de);
                deps.addEventParticipants(additions, de.getId());
                deps.deleteEventParticipants(removals);

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
    }

    private void setEditState(DiaryEvent de) {
        this.de = de;
        header = "Edit " + de.getTitle();
        subHeader = "Complete the form below and hit Update.";
        eventFound = true;
        formVisible = true;
        participants = deps.getDiaryEventParticipantsByDiaryEventId(de.getId());
        teamMembers = tms.getTeamMembersFromParticipants(participants);
    }

    private void setNotFoundState() {
        de = new DiaryEvent();
        header = "No Appointment Found Here";
        subHeader = "Sorry we can't find the appointment you're looking for.";
        eventFound = false;
        formVisible = false;
    }

    private void setNoAccessState() {
        de = new DiaryEvent();
        header = "Sorry but...";
        subHeader = "You do not have permission to edit other team members' appointments, if you think you should speak to an admin!";
        eventFound = false;
        formVisible = false;
    }

    /**
     *
     * @param userId
     * @param isAdmin
     */
    public void onLoad(String userId, boolean isAdmin) {
        if (queryId != null) {
            DiaryEvent result = des.getDiaryEvent(queryId);
            if (Long.parseLong(userId) == result.getCreatorId() || isAdmin) {
                if (result != null) {
                    setEditState(result);
                } else {
                    setNotFoundState();
                }
            } else {
                setNoAccessState();
            }
        }
    }

}
