/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.ctrl;

import alex.jeediary.bus.TeamMemberService;
import alex.jeediary.ent.TeamMember;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Controller for Team View
 * @author alex
 */
@Named(value = "teamCtrl")
@ConversationScoped
public class TeamCtrl implements Serializable{
    @EJB
    private TeamMemberService tms;

    /**
     *
     */
    public TeamCtrl(){}
    
    /**
     *
     * @return
     */
    public List<TeamMember> getTeamMembers(){
        return tms.getAllTeamMembers();
    }
    
    /**
     *
     * @param id
     */
    public void viewTeamMember(String id){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("view-team-member.xhtml?id=" + id);
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        

}
