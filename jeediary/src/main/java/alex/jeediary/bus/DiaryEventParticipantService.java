/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.bus;

import alex.jeediary.ent.DiaryEvent;
import alex.jeediary.ent.DiaryEventParticipant;
import alex.jeediary.ent.TeamMember;
import alex.jeediary.pers.DiaryEventFacade;
import alex.jeediary.pers.DiaryEventParticipantFacade;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * This class contains the business logic for interacting with 
 * DiaryEventParticipants
 * @author alex
 */
@Stateless
public class DiaryEventParticipantService {

    @EJB
    private DiaryEventParticipantFacade depf;

    @EJB
    private DiaryEventFacade def;

    /**
     * This method takes DiaryEventPartipant object and adds it to the database.
     * @param dep Original DiaryEventPartipant to add to the database.
     * @return the created DiaryEventPartipant from the database.
     */
    public DiaryEventParticipant createDiaryEventParticipant(DiaryEventParticipant dep) {
        depf.create(dep);
        return dep;
    }

    /**
     * This method will delete a list of DiaryEventPartipant from the database.
     * @param participants is the list of DiaryEventPartipant to delete.
     */
    public void deleteEventParticipants(List<DiaryEventParticipant> participants) {
        participants.forEach((p) -> {
            deleteDiaryEvent(p);
        });
    }

    /**
     * This method will take a list of TeamMember and list of 
     * DiaryEventPartipant and return any TeamMember objects not in the other
     * list.
     * @param teamMembers is the list of team members.
     * @param participants is the list of DiaryEventPartipant objects.
     * @return the new list of team members not found in the DiaryEventPartipant
     *         list
     */
    public List<TeamMember> findTeamMembersNotInParticipants(List<TeamMember> teamMembers, List<DiaryEventParticipant> participants) {
        List<TeamMember> result = new ArrayList<>();
        teamMembers.forEach((tm) -> {
            boolean notFound = true;
            for (DiaryEventParticipant p : participants) {
                if (Objects.equals(p.getTeamMemberId(), tm.getId())) {
                    notFound = false;
                }
            }
            if (notFound) {
                result.add(tm);
            }
        });
        return result;
    }

    /**
     * This method will take a list of DiaryEventPartipant and TeamMember and
     * return a list of any DiaryEventPartipant not in team member list.
     * @param teamMembers is the list of team members.
     * @param participants is the list of DiaryEventPartipant objects.
     * @return the new list of DiaryEventPartipant where the team members are
     *         not found in the team members list.
     */
    public List<DiaryEventParticipant> findParticipantsNotInTeamMembers(List<TeamMember> teamMembers, List<DiaryEventParticipant> participants) {
        List<DiaryEventParticipant> result = new ArrayList<>();
        participants.forEach((p) -> {
            boolean notFound = true;
            for (TeamMember tm : teamMembers) {
                if (Objects.equals(p.getTeamMemberId(), tm.getId())) {
                    notFound = false;
                }
            }
            if (notFound) {
                result.add(p);
            }
        });
        return result;
    }

    /**
     * Adds a list of team members as participants for a specific event using 
     * the given eventId
     * @param participants is the list of team members to create participant
     *                     records for
     * @param eventId is the id of the event you are creating the record for
     * @return the list of created DiaryEventParticipant
     */
    public List<DiaryEventParticipant> addEventParticipants(List<TeamMember> participants, Long eventId) {
        List<DiaryEventParticipant> result = new ArrayList<>();
        participants.stream().map((tm) -> {
            DiaryEventParticipant p = new DiaryEventParticipant();
            p.setEventId(eventId);
            p.setTeamMemberId(tm.getId());
            return p;
        }).forEachOrdered((p) -> {
            result.add(createDiaryEventParticipant(p));
        });
        return result;
    }

    /**
     * Returns a list of team members who have events that overlap with a 
     * given date range
     * @param teamMembers is a list of team members to check
     * @param startDate is the start of the date range to check
     * @param endDate is the end of the date range to check
     * @return the list of team members that overlay (may be empty)
     */
    public List<TeamMember> findConflicts(List<TeamMember> teamMembers, Date startDate, Date endDate) {
        List<TeamMember> result = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        List<DiaryEvent> events = def.findDiaryEventsWithRange(formatter.format(startDate), formatter.format(endDate));
        events.stream().map((e) -> getDiaryEventParticipantsByDiaryEventId(e.getId())).forEachOrdered((participants) -> {
            participants.forEach((p) -> {
                teamMembers.stream().filter((tm) -> (Objects.equals(tm.getId(), p.getTeamMemberId()))).forEachOrdered((tm) -> {
                    result.add(tm);
                });
            });
        });
        return result;
    }

    /**
     * Takes a list of team members who have event conflicts and generates a 
     * textual message naming them
     * @param conflicts is the list of team members with the conflict
     * @return the textual message in String form
     */
    public String generateConflictsMessage(List<TeamMember> conflicts) {
        String message = "The following team members are busy at this time: ";
        for (TeamMember tm : conflicts) {
            message = message + tm.getName() + ", ";
        }
        return message;
    }

    /**
     * Gets a DiaryEventParticipant of given id from persistence if one exists
     * @param id is the ID of the DiaryEventParticipant
     * @return the found DiaryEventParticipant object
     */
    public DiaryEventParticipant getDiaryEvent(Long id) {
        return depf.find(id);
    }

    /**
     * Updates a DiaryEventParticipant to the given object
     * @param dep is the updated DiaryEventParticipant
     */
    public void updateDiaryEvent(DiaryEventParticipant dep) {
        depf.edit(dep);
    }

    /**
     * Removes a DiaryEventParticipant from persistence
     * @param dep is the DiaryEventParticipant object to remove
     */
    public void deleteDiaryEvent(DiaryEventParticipant dep) {
        depf.remove(dep);
    }

    /**
     * Gets all DiaryEventParticipant objects in persistence
     * @return a list of DiaryEventParticipant
     */
    public List<DiaryEventParticipant> getAllDiaryEventParticipants() {
        return depf.findAll();
    }

    /**
     * Gets a list of participation objects for a given team member
     * @param teamMemberId is the id of the team member to retrieve
     * @return a list of DiaryEventParticipant objects
     */
    public List<DiaryEventParticipant> getDiaryEventParticipantsByTeamMemberId(Long teamMemberId) {
        return depf.getDiaryEventParticipantsByTeamMemberId(teamMemberId.toString());
    }

    /**
     * Delete all DiaryEventParticipant objects containing a team member's id
     * @param teamMemberId the id of the team member
     */
    public void deleteDiaryEventParticipantsByTeamMemberId(Long teamMemberId) {
        depf.removeDiaryEventParticipantsByTeamMemberId(teamMemberId.toString());
    }

    /**
     * Gets a list of participation objects for a given diary event
     * @param diaryEventId the id of the diary event
     * @return a list of DiaryEventParticipant objects
     */
    public List<DiaryEventParticipant> getDiaryEventParticipantsByDiaryEventId(Long diaryEventId) {
        return depf.getDiaryEventParticipantsByDiaryEventId(diaryEventId.toString());
    }

    /**
     * Delete all DiaryEventParticipant objects containing a diary event's id
     * @param diaryEventId is the id of the diary event
     */
    public void deleteDiaryEventParticipantsByDiaryEventId(Long diaryEventId) {
        depf.removeDiaryEventParticipantsByDiaryEventId(diaryEventId.toString());
    }

}
