package io.github.stackpan.mgs_be_test.entity;

import java.util.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_picture")
    private String profilePicture;

    private String password;

    private String role;

    @ElementCollection
    @CollectionTable(name = "permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "name")
    private Set<String> permissions;

    public String getRole() {
        return role.toUpperCase();
    }

    public Set<String> getPermissions() {
        if (this.getRole().equals("ADMIN")) return Set.of("*");
        return permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>(Set.of(new SimpleGrantedAuthority("ROLE_" + this.getRole())));

        this.permissions.forEach(permission -> grantedAuthorities.add(new SimpleGrantedAuthority("PERMISSION_" + permission)));

        return grantedAuthorities;
    }

}
