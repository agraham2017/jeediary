/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.bus;

import alex.jeediary.ent.DiaryEvent;
import alex.jeediary.ent.DiaryEventParticipant;
import alex.jeediary.pers.DiaryEventFacade;
import alex.jeediary.pers.DiaryEventParticipantFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * This class contains the business logic for interacting with DiaryEvent objects
 * @author alex
 */
@Stateless
public class DiaryEventService {

    @EJB
    private DiaryEventFacade def;

    @EJB
    private DiaryEventParticipantFacade depf;

    /**
     * Creates a DiaryEvent in persistence based on the given DiaryEvent object
     * @param de the DiaryEvent object
     * @return the new DiaryEvent object created in persistence
     */
    public DiaryEvent createDiaryEvent(DiaryEvent de) {
        def.create(de);
        return de;
    }

    /**
     * Gets a DiaryEvent of a given Id from persistence
     * @param id is the id of the DiaryEvent
     * @return the DiaryEvent object
     */
    public DiaryEvent getDiaryEvent(Long id) {
        return def.find(id);
    }

    /**
     * Updates a DiaryEvent in persistence using the given DiaryEvent object
     * @param de is the DiaryEvent to update to persistence.
     */
    public void updateDiaryEvent(DiaryEvent de) {
        def.edit(de);
    }

    /**
     * Deletes a DiaryEvent in persistence based on the given DiaryEvent object
     * @param de is the DiaryEvent to delete
     */
    public void deleteDiaryEvent(DiaryEvent de) {
        depf.removeDiaryEventParticipantsByDiaryEventId(de.getId().toString());
        def.remove(de);
    }

    /**
     * Gets all DiaryEvent objects stored in persistence
     * @return a list of DiaryEvent
     */
    public List<DiaryEvent> getAllDiaryEvents() {
        return def.findAll();
    }

    /**
     * Gets all DiaryEvent in a given date range from persistence
     * @param startDate is the start date of the range
     * @param endDate is the end date of the range
     * @return
     */
    public List<DiaryEvent> getEventsInRange(Date startDate, Date endDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return def.findDiaryEventsWithRange(formatter.format(startDate), formatter.format(endDate));
    }

    /**
     * Deletes all DiaryEvent created by a team member specified by an id 
     * @param teamMemberId is the team members id
     */
    public void deleteDiaryEventsbyTeamMemberId(Long teamMemberId) {
        def.removeEventsByTeamMemberId(teamMemberId);
    }

    /**
     * Gets a list of events for a given day from persistence 
     * @param day the day to get events for
     * @return
     */
    public List<DiaryEvent> getEventsForDate(Date day) {
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(day);
        endDate.add(Calendar.DATE, 1);
        return getEventsInRange(day, endDate.getTime());
    }

    /**
     * Filters a list of events based on a team member Id, returns any event 
     * where the team member is a creator or participant
     * @param events the list of events to check
     * @param teamMemberId the id of the team member to check for
     * @return the new filtered list of DiaryEvents
     */
    public List<DiaryEvent> filterEventsByTeamMemberId(List<DiaryEvent> events, Long teamMemberId) {
        List<DiaryEvent> result = new ArrayList<>();
        events.forEach((e) -> {
            if (Objects.equals(e.getCreatorId(), teamMemberId)) {
                result.add(e);
            } else {
                List<DiaryEventParticipant> participants = depf.getDiaryEventParticipantsByDiaryEventId(e.getId().toString());
                participants.stream().filter((p) -> (Objects.equals(teamMemberId, p.getTeamMemberId()))).forEachOrdered((_item) -> {
                    result.add(e);
                });
            }
        });
        return result;

    }

}
