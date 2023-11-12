package com.auth.opinionscope.model.auth;

import com.auth.opinionscope.model.BaseEntity;
import com.auth.opinionscope.model.Role;
import com.auth.opinionscope.model.token.Token;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_data")
public class UserData extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "user_id")
    private Long userId;

    @Column(unique = true, nullable = false)
    @Email(message = "Please enter a valid email address")
    @NotEmpty(message = "This field cannot be empty")
    private String email;

    @Column(name = "password")
    @Size(min=5, message="Password must be at least 5 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "password")
    private String password;


    @Column(nullable = false,name = "mobile_number")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobile_number;

    @Column(nullable = true)
    private String country;

    @Column(nullable = false)
    @NotEmpty(message = "firstname cannot be empty")
    @Size(min = 2, message = "First name must be at least 2 characters long")
    private String firstname;

    @Column(nullable = false)
    @NotEmpty(message = "lastname cannot be empty")
    @Size(min = 2, message = "Last name must be at least 2 characters long")
    private String lastname;

    @Column()
    private Date dateOfBirth;

    private String googleAuthId;

    @Column()
    private String googleAuthSecret;

    @Column(columnDefinition = "boolean default false")
    private Boolean email_verified;

    @Column( columnDefinition = "boolean default false")
    private Boolean mobile_number_verified;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "UserData")
    private List<Token> tokens;

//    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = UserDetails.class)
//    @JoinColumn(name = "usersDetails_id", referencedColumnName = "usersDetailsId",nullable = false)
//    @OneToOne(mappedBy = "user")
//    private UsersDetails usersDetails;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = UsersDetails.class)
    @JoinColumn(name = "usersDetails_id", referencedColumnName = "usersDetailsId",nullable = true)
    private UsersDetails usersDetails;

    //    @OneToMany(mappedBy = "user")
//    private List<VerificationToken> verificationToken;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "Username")
    @Override
    public String getUsername() {
        return email;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "accountNonExpired")
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "accountNonLocked")
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "isCredentialsNonExpired")
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
