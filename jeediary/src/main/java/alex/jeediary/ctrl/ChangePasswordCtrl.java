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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Controller for the Change Password form
 * @author alex
 */
@Named(value = "changePasswordCtrl")
@SessionScoped
public class ChangePasswordCtrl implements Serializable {
    
    private Long queryId;
    private TeamMember tm = new TeamMember();
    private String header;
    private String subHeader;
    private boolean userFound = false;
    private boolean formVisible = true;
    
    private String password;
    private String passwordConfirmation;

    /**
     * Getter for queryId
     * @return
     */
    public Object getQueryId() {
        return queryId;
    }

    /**
     * Setter for queryId
     * @param id
     */
    public void setQueryId(String id) {
        this.queryId = Long.parseLong(id);
    }
    
    /**
     * Getter for userFound
     * @return
     */
    public boolean getUserFound(){
        return userFound;
    }
    
    /**
     * Getter fir userNotFound
     * @return
     */
    public boolean getUserNotFound(){
        return !userFound;
    }
    
    /**
     * getter for formInvisible (a toggle to determine if the form is visible 
     * on the page)
     * @return
     */
    public boolean getFormVisible(){
        return formVisible;
    }

    /**
     * getter for page header
     * @return
     */
    public String getHeader() {
        return header;
    }

    /**
     * getter for page sub header
     * @return
     */
    public String getSubHeader() {
        return subHeader;
    }
    
    /**
     * getter for the password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for the password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for the password confirmation
     * @return
     */
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * setter for the password confirmation
     * @param passwordConfirmation
     */
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @EJB
    private TeamMemberService tms;

    /**
     * Function to submit the forms data if validation correct
     */
    public void submitForm() {
        if (userFound) {
            tms.updateTeamMember(tm);
        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("view-team-member.xhtml?id=" + tm.getId());
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setEditState(TeamMember tm){
        this.tm = tm;
        header = "Change " + tm.getName() + "'s Password";
        subHeader = "Complete the form below and hit Update.";
        userFound = true;
        formVisible = true;
    }
    
    private void setNotFoundState(){
        tm = new TeamMember();
        header = "Nobody Found Here";
        subHeader = "Sorry we can't find the team member you're looking for.";
        userFound = false;
        formVisible = false;
    }
    
    private void setNoAccessState(){
        tm = new TeamMember();
        header = "Sorry but...";
        subHeader = "You do not have permission to change other people's passwords, if you think you should speak to an admin!";
        userFound = false;
        formVisible = false;
    }

    /**
     * Function executed on the page load to setup the form correctly
     * @param userId is the Id of the currently logged in user
     * @param isAdmin whether the logged in user has admin rights
     */
    public void onLoad(String userId, boolean isAdmin) {
        if (queryId != null) {
            if (Long.parseLong(userId) == queryId || isAdmin ){
            TeamMember result = tms.getTeamMember(queryId);
            if (result != null) {
                setEditState(result);
            } else setNotFoundState();
        } else setNoAccessState();
        
        }
    }
}
