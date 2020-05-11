/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.pers;

import alex.jeediary.ent.DiaryEventParticipant;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Facade for interacting with DiaryEventParticipant objects in persistence
 * @author alex
 */
@Stateless
public class DiaryEventParticipantFacade extends AbstractFacade<DiaryEventParticipant> {

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
    public DiaryEventParticipantFacade() {
        super(DiaryEventParticipant.class);
    }
    
    /**
     *
     * @param eventId
     * @return
     */
    public List<DiaryEventParticipant> getDiaryEventParticipantsByDiaryEventId(String eventId){
        Query query = em.createQuery("Select dep from DiaryEventParticipant dep where dep.eventId = '" + eventId + "'");
        return (List<DiaryEventParticipant>) query.getResultList();
    }
    
    /**
     *
     * @param eventId
     */
    public void removeDiaryEventParticipantsByDiaryEventId(String eventId){
        Query query = em.createQuery("Delete from DiaryEventParticipant dep where dep.eventId = '" + eventId + "'");
        query.executeUpdate();
    }
    
    /**
     *
     * @param teamMemberId
     * @return
     */
    public List<DiaryEventParticipant> getDiaryEventParticipantsByTeamMemberId(String teamMemberId){
        Query query = em.createQuery("Select dep from DiaryEventParticipant dep where dep.teamMemberId = '" + teamMemberId + "'");
        return (List<DiaryEventParticipant>) query.getResultList();
    }
    
    /**
     *
     * @param teamMemberId
     */
    public void removeDiaryEventParticipantsByTeamMemberId(String teamMemberId){
        Query query = em.createQuery("Delete from DiaryEventParticipant dep where dep.teamMemberId = '" + teamMemberId + "'");
        query.executeUpdate();
    }

}
