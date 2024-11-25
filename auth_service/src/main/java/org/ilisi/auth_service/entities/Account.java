package org.ilisi.auth_service.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "_account")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Ensure this is unique to the Account
    private String password;
    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    // Constructeurs
    public Account() {
    }

    public Account(Long id, String password, String username, String email) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
    }

    // Méthode statique pour obtenir un builder
    public static Builder builder() {
        return new Builder();
    }

    // Classe interne Builder
    public static class Builder {
        private Long id;
        private String password;
        private String username;
        private String email;

        // Méthodes pour définir les champs
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        // Méthode pour construire l'objet principal
        public Account build() {
            return new Account(id, password, username, email);
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Implement as per your logic
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement as per your logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement as per your logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement as per your logic
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement as per your logic
    }
}
