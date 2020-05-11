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
 * Controller for View Team Member View
 * @author alex
 */
@Named(value = "viewTeamMemberCtrl")
@SessionScoped
public class ViewTeamMemberCtrl implements Serializable{
    
    private boolean userFound = false;    
    private Long id;
    private String header;
    private String subHeader;
    
    private TeamMember tm = new TeamMember();
    
    /**
     * 
     */
    public ViewTeamMemberCtrl(){}
    
    /**
     *
     * @return
     */
    public boolean getUserFound(){
        return userFound;
    }
    
    /**
     *
     * @return
     */
    public Object getId(){
        return id;
    }
    
    /**
     *
     * @param id
     */
    public void setId(String id){
        this.id = Long.parseLong(id);
    }
    
    /**
     *
     * @return
     */
    public String getHeader(){
        return header;
    }
    
    /**
     *
     * @return
     */
    public String getSubHeader(){
        return subHeader;
    }
    
    /**
     *
     * @return
     */
    public String getName(){
        return tm.getName();
    }
    
    /**
     *
     * @return
     */
    public String getEmail(){
        return tm.getEmail();
    }
    
    /**
     *
     * @return
     */
    public String getPhone(){
        return tm.getPhone();
    }
    
    /**
     *
     * @return
     */
    public String getAddressLn1(){
        return tm.getAddressLn1();
    }
    
    /**
     *
     * @return
     */
    public String getAddressLn2(){
        return tm.getAddressLn2();
    }
    
    /**
     *
     * @return
     */
    public String getCity(){
        return tm.getCity();
    }
    
    /**
     *
     * @return
     */
    public String getRegion(){
        return tm.getRegion();
    }
    
    /**
     *
     * @return
     */
    public String getZip(){
        return tm.getZip();
    }
    
    @EJB
    private TeamMemberService tms;
    
    /**
     *
     */
    public void onLoad(){
        if (id != null) {
            TeamMember result = tms.getTeamMember(id);
            if (result != null){
                userFound = true;
                tm = result;
                header = tm.getName();
                subHeader = tm.getEmail();
            }
        } else {
            header = "Team Member Not Found";
            subHeader = "Sorry the team member you are looking for cannot be found";
        }
    }
    
    /**
     *
     * @param userId
     * @param isAdmin
     * @return
     */
    public boolean hasEditPermission(String userId, boolean isAdmin){
        if (Long.parseLong(userId) == id || isAdmin ){
            return true;
        } else return false;
    }
    
    /**
     *
     * @param userId
     * @param isAdmin
     * @return
     */
    public boolean hasDeletePermission(String userId, boolean isAdmin){
        if (Long.parseLong(userId) != id && isAdmin ){
            return true;
        } else return false;
    }
    
    /**
     *
     */
    public void editTeamMember(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("edit-team-member.xhtml?id=" + id);
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void changePassword(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("change-password.xhtml?id=" + id);
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     */
    public void deleteTeamMember(){
        tms.deleteTeamMember(tm);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("team.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
