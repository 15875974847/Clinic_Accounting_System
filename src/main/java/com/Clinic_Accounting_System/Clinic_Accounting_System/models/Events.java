package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "events")
public class Events implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "`header`", columnDefinition = "varchar(50)", nullable = false)
    private String header;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "`content`")
    private String content;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "only_for_personal", columnDefinition = "tinyint(1) default 0")
    private boolean onlyForPersonal;

    public Events() { }

    public Events(@NotNull @Size(max = 50) String header, String content, Date startDate, Date endDate, boolean onlyForPersonal) {
        this.header = header;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.onlyForPersonal = onlyForPersonal;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isOnlyForPersonal() {
        return onlyForPersonal;
    }

    public void setOnlyForPersonal(boolean onlyForPersonal) {
        this.onlyForPersonal = onlyForPersonal;
    }
}
