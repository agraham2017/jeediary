/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.pers;

import alex.jeediary.ent.TeamMember;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Facade for interacting with TeamMember objects in persistence
 * @author alex
 */
@Stateless
public class TeamMemberFacade extends AbstractFacade<TeamMember> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public TeamMemberFacade() {
        super(TeamMember.class);
    }

    /**
     *
     * @param email
     * @return
     */
    public List<TeamMember> findByEmail(String email) {
        Query query = em.createQuery("Select tm from TeamMember tm where tm.email = '" + email + "'");
        return (List<TeamMember>) query.getResultList();
    }
}
