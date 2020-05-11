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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Controller for View Appointment View
 * @author alex
 */
@Named(value = "viewEventCtrl")
@SessionScoped
public class ViewEventCtrl implements Serializable {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private boolean eventFound = false;
    private Long id;
    private String header;
    private String subHeader;

    private DiaryEvent de = new DiaryEvent();
    private List<TeamMember> teamMembers = new ArrayList<>();

    /**
     *
     */
    public ViewEventCtrl() {
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
    public Object getId() {
        return id;
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
     * @param tm
     */
    public void viewTeamMember(TeamMember tm) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("view-team-member.xhtml?id=" + tm.getId().toString());
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
     * @param id
     */
    public void setId(String id) {
        this.id = Long.parseLong(id);
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
    public String getTitle() {
        return de.getTitle();
    }

    /**
     *
     * @return
     */
    public String getStartDate() {
        return formatter.format(de.getStartDate());
    }

    /**
     *
     * @return
     */
    public String getEndDate() {
        return formatter.format(de.getEndDate());
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return de.getDescription();
    }

    @EJB
    private DiaryEventService des;

    @EJB
    private DiaryEventParticipantService deps;

    @EJB
    private TeamMemberService tms;

    /**
     *
     */
    public void onLoad() {
        if (id != null) {
            DiaryEvent result = des.getDiaryEvent(id);
            if (result != null) {
                eventFound = true;
                de = result;
                header = de.getTitle();
                subHeader = de.getDescription();
                List<DiaryEventParticipant> participants = deps.getDiaryEventParticipantsByDiaryEventId(id);
                teamMembers = tms.getTeamMembersFromParticipants(participants);
            }
        } else {
            header = "Appointment Not Found";
            subHeader = "Sorry the appointment you are looking for cannot be found";
        }
    }

    /**
     *
     * @param userId
     * @param isAdmin
     * @return
     */
    public boolean hasEditPermission(String userId, boolean isAdmin) {
        if (Long.parseLong(userId) == id || isAdmin) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param userId
     * @param isAdmin
     * @return
     */
    public boolean hasDeletePermission(String userId, boolean isAdmin) {
        if (Long.parseLong(userId) == id || isAdmin) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     */
    public void editEvent() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit-appointment.xhtml?id=" + id);
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void deleteEvent() {
        des.deleteDiaryEvent(de);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("diary.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
