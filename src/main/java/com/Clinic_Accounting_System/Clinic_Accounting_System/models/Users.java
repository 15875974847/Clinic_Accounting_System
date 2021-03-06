package com.Clinic_Accounting_System.Clinic_Accounting_System.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class Users implements Serializable {
    /*
        We'll be using this generated id value in child id entities
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "`username`", columnDefinition = "varchar(30)", nullable = false, unique = true)
    private String username;

    @NotNull
    @Size(max = 30)
    @Column(name = "`password`", columnDefinition = "varchar(30)", nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "`role`", columnDefinition = "varchar(10)", nullable = false)
    private Roles role;

    // Specifying the parent relationship with UserInfo datatable
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "user")
    private UserInfo userInfo;

    public Users() {}

    public Users(@NotNull @Size(max = 30) String username, @NotNull @Size(max = 30) String password, @NotNull @Size(max = 10) Roles role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;
        Users that = (Users) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}