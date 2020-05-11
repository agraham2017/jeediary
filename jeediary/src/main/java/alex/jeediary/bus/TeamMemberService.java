/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.bus;

import alex.jeediary.ent.DiaryEventParticipant;
import alex.jeediary.ent.TeamMember;
import alex.jeediary.pers.DiaryEventFacade;
import alex.jeediary.pers.DiaryEventParticipantFacade;
import alex.jeediary.pers.TeamMemberFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * This class contains the business logic for interacting with TeamMember objects
 * @author alex
 */
@Stateless
public class TeamMemberService {

    @EJB
    private TeamMemberFacade tmf;

    @EJB
    private DiaryEventFacade def;

    @EJB
    private DiaryEventParticipantFacade depf;
    
    /**
     * Checks if an email is in a list of team members
     * @param list the team members to check
     * @param email the email address to check for
     * @return true if found, else false
     */
    public boolean emailInTeamMemberList(List<TeamMember> list, String email){
        return list.stream().anyMatch((tm) -> (tm.getEmail() == null ? email == null : tm.getEmail().equals(email)));
    }

    /**
     * Creates a team member in persistence
     * @param tm is the TeamMember object to create
     * @return the new TeamMember object
     */
    public TeamMember createTeamMember(TeamMember tm) {
        tmf.create(tm);
        return tm;
    }

    /**
     * Gets a team member from persistence based on the team member id
     * @param id of the team member
     * @return the team member from persistence
     */
    public TeamMember getTeamMember(Long id) {
        return tmf.find(id);
    }
    
    /**
     * Gets a list of team members from a list of DiaryEventParticiapant
     * @param list of participants to get team members for
     * @return list of team members
     */
    public List<TeamMember> getTeamMembersFromParticipants(List<DiaryEventParticipant> list){
        List<TeamMember> result = new ArrayList<>();
        list.forEach((p) -> {
            result.add(getTeamMember(p.getTeamMemberId()));
        });
        return result;
    }

    /**
     * finds a team member by email address
     * @param email to look for
     * @return TeamMember object if email is found
     */
    public TeamMember findTeamMemberByEmail(String email) {
        List<TeamMember> result = tmf.findByEmail(email);
        if (result != null && result.size() == 1) {
            return result.get(0);
        } else {
            return null;
        }
    }

    /**
     * Updates a team member in persistence from a given TeamMember
     * @param tm the TeamMember to update
     */
    public void updateTeamMember(TeamMember tm) {
        tmf.edit(tm);
    }

    /**
     * Deletes a team member from persistence based on a given TeamMember
     * @param tm the TeamMember to delete
     */
    public void deleteTeamMember(TeamMember tm) {
        depf.removeDiaryEventParticipantsByTeamMemberId(tm.getId().toString());
        def.removeEventsByTeamMemberId(tm.getId());
        tmf.remove(tm);
    }

    /**
     * gets all team members from persistence
     * @return a list of all TeamMember objects
     */
    public List<TeamMember> getAllTeamMembers() {
        return tmf.findAll();
    }

}
