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
 * Controller for Edit Team Member View
 * @author alex
 */
@Named(value = "editTeamMemberCtrl")
@SessionScoped
public class EditTeamMemberCtrl implements Serializable {

    private Long queryId;
    private TeamMember tm = new TeamMember();
    private String header;
    private String subHeader;
    private boolean userFound = false;
    private boolean formVisible = true;

    /**
     *
     */
    public EditTeamMemberCtrl() {
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
    public boolean getUserFound() {
        return userFound;
    }

    /**
     *
     * @return
     */
    public boolean getUserNotFound() {
        return !userFound;
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
    public boolean getIsAdmin() {
        return tm.getIsAdmin();
    }

    /**
     *
     * @param isAdmin
     */
    public void setIsAdmin(boolean isAdmin) {
        tm.setIsAdmin(isAdmin);
    }
    
    /**
     *
     * @param userId
     * @return
     */
    public boolean canSetIsAdmin(String userId){
        if (tm.getId() != Long.parseLong(userId)){
            return true;
        } else return false;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return tm.getFirstName();
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        tm.setFirstName(firstName);
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return tm.getLastName();
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        tm.setLastName(lastName);
    }

    /**
     *
     * @return
     */
    public String getName() {
        return tm.getName();
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return tm.getEmail();
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        tm.setEmail(email);
    }

    /**
     *
     * @return
     */
    public String getPhone() {
        return tm.getPhone();
    }

    /**
     *
     * @param phone
     */
    public void setPhone(String phone) {
        tm.setPhone(phone);
    }

    /**
     *
     * @return
     */
    public String getAddressLn1() {
        return tm.getAddressLn1();
    }

    /**
     *
     * @param addressLn1
     */
    public void setAddressLn1(String addressLn1) {
        tm.setAddressLn1(addressLn1);
    }

    /**
     *
     * @return
     */
    public String getAddressLn2() {
        return tm.getAddressLn2();
    }

    /**
     *
     * @param addressLn2
     */
    public void setAddressLn2(String addressLn2) {
        tm.setAddressLn2(addressLn2);
    }

    /**
     *
     * @return
     */
    public String getCity() {
        return tm.getCity();
    }

    /**
     *
     * @param city
     */
    public void setCity(String city) {
        tm.setCity(city);
    }

    /**
     *
     * @return
     */
    public String getRegion() {
        return tm.getRegion();
    }

    /**
     *
     * @param region
     */
    public void setRegion(String region) {
        tm.setRegion(region);
    }

    /**
     *
     * @return
     */
    public String getZip() {
        return tm.getZip();
    }

    /**
     *
     * @param zip
     */
    public void setZip(String zip) {
        tm.setZip(zip);
    }

    @EJB
    private TeamMemberService tms;

    /**
     *
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

    private void setEditState(TeamMember tm) {
        this.tm = tm;
        header = "Edit " + tm.getName();
        subHeader = "Complete the form below and hit Update.";
        userFound = true;
        formVisible = true;
    }

    private void setNotFoundState() {
        tm = new TeamMember();
        header = "Nobody Found Here";
        subHeader = "Sorry we can't find the team member you're looking for.";
        userFound = false;
        formVisible = false;
    }

    private void setNoAccessState() {
        tm = new TeamMember();
        header = "Sorry but...";
        subHeader = "You do not have permission to edit or create Team Members, if you think you should speak to an admin!";
        userFound = false;
        formVisible = false;
    }

    /**
     *
     * @param userId
     * @param isAdmin
     */
    public void onLoad(String userId, boolean isAdmin) {
        if (queryId != null) {
            if (Long.parseLong(userId) == queryId || isAdmin) {
                TeamMember result = tms.getTeamMember(queryId);
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
