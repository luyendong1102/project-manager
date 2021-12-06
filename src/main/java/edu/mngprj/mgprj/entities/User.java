package edu.mngprj.mgprj.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "uuser")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "namee")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "birth")
    private Date birthdate;

    @Column(name = "resume")
    private String resume;

    @Column(name = "is_enable")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean is_enable;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Project> projects;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserLogin userLogin;

    @ManyToMany
    @JoinTable(
            name = "participant",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "taskid")
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private Set<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userrole",
            joinColumns = @JoinColumn(name = "userid"),
            inverseJoinColumns = @JoinColumn(name = "roleid")
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private Set<UserRole> roles;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<TaskDetail> taskDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
