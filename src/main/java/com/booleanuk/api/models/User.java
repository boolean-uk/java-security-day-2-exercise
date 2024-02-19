package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "user")
public class User extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Column(unique = true, length = 20)
    private String username;
    @NotBlank
    @Column(unique = true, length = 50)
    private String email;
    @NotBlank
    @Column(length = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Lend> history;

    public User(final String username, final String email, final String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean haveNullFields() {
        return username == null || email == null || password == null;
    }

    @Override
    public void copyOverData(Model model) {
        User _user = (User) model;

        username = _user.username;
        email = _user.email;
        password = _user.password;

        roles = _user.roles;
    }
}
