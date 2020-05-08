package com.globerce.management.entity.system;

import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "managementTime", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames={"currentDate", "user_id"}))
@Access(AccessType.PROPERTY)
public class ManagementTime {

    private Long id;
    private Date currentDate;
    private Date startDate;
    private Date endDate;
    private Date workTime;
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "currentDate")
    @Temporal(TemporalType.DATE)
    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    @Column(name = "startDate")
    @Temporal(TemporalType.TIME)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Column(name = "endDate")
    @Temporal(TemporalType.TIME)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Column(name = "workTime")
    @Temporal(TemporalType.TIME)
    public Date getWorkTime() {
        return workTime;
    }

    public void setWorkTime(Date workTime) {
        this.workTime = workTime;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotAudited
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
