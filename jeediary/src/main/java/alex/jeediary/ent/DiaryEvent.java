/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alex.jeediary.ent;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Model for Diary Event (stores details for an individual event excl participants)
 * @author alex
 */
@Entity
public class DiaryEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startDate = new Date();
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate = new Date();
    private Long creatorId;

    /**
     * getter for events title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for events title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for events description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for events description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for events start date
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * setter for events start date
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * getter for events end date
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * setter for events end date
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * getter for events id
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * setter for events id
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * getter for creators id
     * @return
     */
    public Long getCreatorId() {
        return creatorId;
    }

    /**
     * setter for creators id
     * @param creatorId
     */
    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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
        if (!(object instanceof DiaryEvent)) {
            return false;
        }
        DiaryEvent other = (DiaryEvent) object;
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
