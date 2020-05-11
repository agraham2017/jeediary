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
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Controller for create team member view
 * @author alex
 */
@Named(value = "createTeamMemberCtrl")
@ConversationScoped
public class CreateTeamMemberCtrl implements Serializable {

    private TeamMember tm = new TeamMember();
    private String passwordConfirmation;

    /**
     * Constructor
     */
    public CreateTeamMemberCtrl() {
    }

    /**
     * getter for first name
     * @return
     */
    public String getFirstName() {
        return tm.getFirstName();
    }

    /**
     * setter for first name
     * @param firstName
     */
    public void setFirstName(String firstName) {
        tm.setFirstName(firstName);
    }

    /**
     * getter for last name
     * @return
     */
    public String getLastName() {
        return tm.getLastName();
    }

    /**
     * setter for last name
     * @param lastName
     */
    public void setLastName(String lastName) {
        tm.setLastName(lastName);
    }

    /**
     * getter for tm's full name
     * @return
     */
    public String getName() {
        return tm.getName();
    }

    /**
     * getter for tm's email
     * @return
     */
    public String getEmail() {
        return tm.getEmail();
    }

    /**
     * setter for tm's email
     * @param email
     */
    public void setEmail(String email) {
        tm.setEmail(email);
    }

    /**
     * getter for tm's phone
     * @return
     */
    public String getPhone() {
        return tm.getPhone();
    }

    /**
     * setter for tm's phone
     * @param phone
     */
    public void setPhone(String phone) {
        tm.setPhone(phone);
    }

    /**
     * getter for address line 1
     * @return
     */
    public String getAddressLn1() {
        return tm.getAddressLn1();
    }

    /**
     * setter for address line 1
     * @param addressLn1
     */
    public void setAddressLn1(String addressLn1) {
        tm.setAddressLn1(addressLn1);
    }

    /**
     * getter for address line 2
     * @return
     */
    public String getAddressLn2() {
        return tm.getAddressLn2();
    }

    /**
     * setter for address line 2
     * @param addressLn2
     */
    public void setAddressLn2(String addressLn2) {
        tm.setAddressLn2(addressLn2);
    }

    /**
     * getter for city
     * @return
     */
    public String getCity() {
        return tm.getCity();
    }

    /**
     * setter for city
     * @param city
     */
    public void setCity(String city) {
        tm.setCity(city);
    }

    /**
     * getter for region
     * @return
     */
    public String getRegion() {
        return tm.getRegion();
    }

    /**
     * setter for region
     * @param region
     */
    public void setRegion(String region) {
        tm.setRegion(region);
    }

    /**
     * getter for zip code
     * @return
     */
    public String getZip() {
        return tm.getZip();
    }

    /**
     * setter for zip code
     * @param zip
     */
    public void setZip(String zip) {
        tm.setZip(zip);
    }

    /**
     * getter for password
     * @return
     */
    public String getPassword() {
        return tm.getPassword();
    }

    /**
     * setter for password
     * @param password
     */
    public void setPassword(String password) {
        tm.setPassword(password);
    }

    /**
     * getter for password confirmation
     * @return
     */
    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    /**
     * setter for password confirmation
     * @param passwordConfirmation
     */
    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    @EJB
    private TeamMemberService tms;

    /**
     * Function to create team member if validation is accepted
     */
    public void submitForm() {

        TeamMember result = tms.createTeamMember(tm);
        tm = result;

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("view-team-member.xhtml?id=" + tm.getId());
        } catch (IOException ex) {
            Logger.getLogger(TeamCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
