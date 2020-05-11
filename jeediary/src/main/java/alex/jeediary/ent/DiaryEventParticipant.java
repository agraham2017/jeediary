/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.ent;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Participant record indicating team member participation in Diary Event
 * @author alex
 */
@Entity
public class DiaryEventParticipant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long eventId;
    private Long teamMemberId;

    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     *
     * @param eventId
     */
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    /**
     *
     * @return
     */
    public Long getTeamMemberId() {
        return teamMemberId;
    }

    /**
     *
     * @param teamMemberId
     */
    public void setTeamMemberId(Long teamMemberId) {
        this.teamMemberId = teamMemberId;
    }
            
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DiaryEventParticipant)) {
            return false;
        }
        DiaryEventParticipant other = (DiaryEventParticipant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ent.DiaryEvent[ id=" + id + " ]";
    }

}
