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
import java.security.Principal;
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
 * Controller for current user session (used globally across application)
 * @author alex
 */
@Named(value = "userSessionCtrl")
@SessionScoped
public class UserSessionCtrl implements Serializable {

    private TeamMember tm = new TeamMember();
    
    @Inject
    private SecurityContext securityContext;

    /**
     * Returns whether current user is an Administrator
     * @return
     */
    public boolean getIsAdmin() {
        return tm.getIsAdmin();
    }
    
    /**
     * Return current users ID
     * @return
     */
    public String getId() {
        return tm.getId().toString();
    }

    /**
     * Logs out Current User
     */
    public void logOut() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Loads current user's view team member page
     */
    public void viewDetails() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("view-team-member.xhtml?id="+ getId());
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * loads current user's change password view
     */
    public void changePassword() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("change-password.xhtml?id="+ getId());
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Getter for current logged in team members Name
     * @return
     */
    public String getName() {
        return tm.getName();
    }
    
    @EJB
    private TeamMemberService tms;

    /**
     * Gets current user's information after the user logs into the application
     */
    @PostConstruct
    public void setup () {
        Principal p = securityContext.getCallerPrincipal();
        tm = tms.getTeamMember(Long.parseLong(p.getName()));
    }
    
}
