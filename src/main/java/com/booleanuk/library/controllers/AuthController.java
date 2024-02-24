package com.booleanuk.library.controllers;

import com.booleanuk.library.models.ERole;
import com.booleanuk.library.models.Role;
import com.booleanuk.library.models.User;
import com.booleanuk.library.payload.request.LoginRequest;
import com.booleanuk.library.payload.request.SignupRequest;
import com.booleanuk.library.payload.response.JwtResponse;
import com.booleanuk.library.payload.response.MessageResponse;
import com.booleanuk.library.repository.RoleRepository;
import com.booleanuk.library.repository.UserRepository;
import com.booleanuk.library.security.jwt.JwtUtils;
import com.booleanuk.library.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)    //csrf
@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    //Här kommer saltet, om det nånsin kommer
    //username och password kommer in som ett json objekt
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = this.jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()  //role = item
                .map((item) -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (this.userRepository.existsByUsername(signupRequest.getUsername())) {    //om dem redan finns i databasen
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken."));
        }
        if (this.userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already in use."));
        }
        //if you were adding salt you would do it here
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                this.encoder.encode(signupRequest.getPassword()));  //inte spara lösenord i plain text
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) { //om det inte skickas in några roller så letas rollen efter i repot och läggs till om den hittas
            Role userRole = this.roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(userRole);
        }
        else {  //Om det finns roller
            strRoles.forEach((role) -> {
                switch (role) {
                    case "admin":
                        Role adminRole = this.roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = this.roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = this.roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                        roles.add(userRole);
                        break;
                }
            });
        }
        user.setRoles(roles);
        this.userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
}
