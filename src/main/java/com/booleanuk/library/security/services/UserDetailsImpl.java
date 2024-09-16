package com.booleanuk.library.security.services;

import com.booleanuk.library.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    //these are annotated elsewhere
    private int id;
    private String username;
    private String email;

    @JsonIgnore //don't return password for a user in the json
    private String password;

    //vi vet inte vilken klass än, men den extendar iallafall GrantedAuthority
    private Collection<? extends GrantedAuthority>   authorities;

    public UserDetailsImpl(int id,
                           String username,
                           String email,
                           String password,
                           Collection<? extends GrantedAuthority> authorities)
    {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    //går igenom alla roller associerade emd en user. mappar dom och skapar en lista av dom
    //för dom ska användas för att generera ett objekt som vi ska returnera
    //denna metod kan kallas innan konstruktorn eftersom den är static
    //den skapar saker som behövs för att kalla konstruktorn
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    //dessa fyra overridade metoder finns bara för att kunna använda interfacet UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        //är o ett objekt av denna klass så kan man casta o till denna klass
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
