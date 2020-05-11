/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.ctrl;

import alex.jeediary.bus.DiaryEventService;
import alex.jeediary.bus.TeamMemberService;
import alex.jeediary.ent.DiaryEvent;
import alex.jeediary.ent.TeamMember;
import alex.jeediary.enums.DiaryFilter;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;

/**
 * Controller for Dairy View
 * @author alex
 */
@Named(value = "diaryCtrl")
@SessionScoped
public class DiaryCtrl implements Serializable {

    @Inject
    SecurityContext securityContext;

    @EJB
    private DiaryEventService des;
    @EJB
    private TeamMemberService tms;
    private Date dayOverviewDate = new Date();
    private String userEmail = "";
    private DiaryFilter filter = DiaryFilter.PERSONAL;
    private List<DiaryEvent> listEvents = new ArrayList<>();

    /**
     *
     */
    public DiaryCtrl() {
    }

    /**
     *
     * @return
     */
    public String getDayOverviewDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(dayOverviewDate);
    }

    /**
     *
     * @param date
     */
    public void setDayOverviewDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        try {
            dayOverviewDate = formatter.parse(date);
        } catch (ParseException ex) {
            Logger.getLogger(EditEventCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public List<DiaryEvent> getListEvents() {
        return listEvents;
    }

    /**
     *
     * @param listEvents
     */
    public void setListEvents(List<DiaryEvent> listEvents) {
        this.listEvents = listEvents;
    }

    /**
     *
     * @return
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     *
     * @param userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     *
     */
    public void filterAll() {
        filter = DiaryFilter.ALL;
        listEvents = des.getEventsForDate(dayOverviewDate);
    }

    /**
     *
     */
    public void filterPersonal() {

        Principal p = securityContext.getCallerPrincipal();
        filter = DiaryFilter.PERSONAL;
        listEvents = des.getEventsForDate(dayOverviewDate);
        listEvents = des.filterEventsByTeamMemberId(listEvents, Long.parseLong(p.getName()));
    }

    /**
     *
     */
    public void filterEmail() {
        filter = DiaryFilter.EMAIL;
        TeamMember tm = tms.findTeamMemberByEmail(userEmail);
        listEvents = des.getEventsForDate(dayOverviewDate);
        if (tm != null) {
            listEvents = des.filterEventsByTeamMemberId(listEvents, tm.getId());
        }

    }

    /**
     *
     */
    public void updateDayOverview() {
        switch (filter) {
            case PERSONAL:
                filterPersonal();
                break;
            case EMAIL:
                filterEmail();
                break;
            default:
                filterAll();
        }
    }

    /**
     *
     * @param id
     */
    public void viewDiaryEvent(String id) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("view-appointment.xhtml?id=" + id);
        } catch (IOException ex) {
            Logger.getLogger(DiaryCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    @PostConstruct
    public void setup() {
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        dayOverviewDate = date.getTime();
        updateDayOverview();
    }
}
