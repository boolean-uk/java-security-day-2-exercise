package com.booleanuk.api.Library.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private String username;

    @Column
    @Email
    private String email;

    @Column
    private String phone;

    @NotBlank
    @Size(max=120)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value ={"users", "games"})
    private List<Game> games;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value ={"users", "games"})
    private Set<Game> prevGames;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name="user_roles", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles=new HashSet<>();

    public User(String username, String email, String password){
        this.username=username;
        this.email=email;
        this.password=password;
    }

    public void borrowGame(Game game){
        if (!games.contains(game)){
            games.add(game);
        }
        prevGames.add(game);
    }

    public void returnGame(Game game){
        if (!games.contains(game)){
            games.remove(game);
        }
    }

}
