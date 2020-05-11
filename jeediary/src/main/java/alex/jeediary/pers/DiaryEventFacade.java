/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.pers;

import alex.jeediary.ent.DiaryEvent;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Facade for interacting with Diary Events in persistence
 * @author alex
 */
@Stateless
public class DiaryEventFacade extends AbstractFacade<DiaryEvent> {

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
    public DiaryEventFacade() {
        super(DiaryEvent.class);
    }
    
    /**
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public List<DiaryEvent> findDiaryEventsWithRange(String startDate, String endDate) {
        Query query = em.createQuery("Select de from DiaryEvent de where (de.startDate <= '" + startDate + "' AND de.endDate >= '" + endDate + "') OR (de.startDate <= '" + startDate + "' AND de.endDate > '" + startDate + "') OR (de.startDate < '" + endDate + "' AND de.endDate >= '" + endDate + "') OR (de.startDate >= '" + startDate + "' AND de.endDate <= '" + endDate + "')");
        return (List<DiaryEvent>) query.getResultList();
    }
    
    /**
     *
     * @param teamMemberId
     */
    public void removeEventsByTeamMemberId(Long teamMemberId){
        Query query = em.createQuery("Delete de from DiaryEvent de where de.creatorId = '" + teamMemberId + "'");
        query.executeUpdate();
    }

}
