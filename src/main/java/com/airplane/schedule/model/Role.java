package com.airplane.schedule.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<User> users = new HashSet<>();

    @OneToMany(mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private Collection<Permission> permissions = new HashSet<>();

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    public void assignRoleToUser(User user){
        user.setRole(this);
        if(users == null){
            users = new HashSet<>();
        }
        this.getUsers().add(user);
    }

    public void removeUserFromRole(User user){
        this.getUsers().remove(user);
    }

    public void assignPermissionToRole(Permission permission){
        permission.setRole(this);
        if(permissions == null){
            permissions = new HashSet<>();
        }
        this.getPermissions().add(permission);
    }

    public void removePermissionFromRole(Permission permission){
        this.getPermissions().remove(permission);
    }
}
