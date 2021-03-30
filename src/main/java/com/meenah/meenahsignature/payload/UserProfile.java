package com.meenah.meenahsignature.payload;




import com.meenah.meenahsignature.role.Role;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Set<Role> roles = new HashSet<>();
    private Instant joinedAt;


    public UserProfile(Long id, String username, String name, Set<Role> roles, Instant joinedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
        this.joinedAt = joinedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }




}