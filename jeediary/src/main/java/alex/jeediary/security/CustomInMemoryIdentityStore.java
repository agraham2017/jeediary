/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.security;
import alex.jeediary.bus.TeamMemberService;
import alex.jeediary.ent.TeamMember;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Arrays;
import java.util.HashSet;
import javax.ejb.EJB;


/**
 * Provides an identity store for verifying users during authentication
 * @author alex
 */

@ApplicationScoped
public class CustomInMemoryIdentityStore implements IdentityStore {
    
    @EJB
    TeamMemberService tms;

    /**
     *
     * @param credential
     * @return
     */
    @Override
    public CredentialValidationResult validate(Credential credential) {

        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
        
        TeamMember tm = tms.findTeamMemberByEmail(login.getCaller());
        
        if (tm != null){
            if (login.getCaller().equals(tm.getEmail()) && login.getPasswordAsString().equals(tm.getPassword())) {
                return new CredentialValidationResult(tm.getId().toString(), new HashSet<>(Arrays.asList("USER")));
            }
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }
}
