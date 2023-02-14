package com.sideproject.social.Entities;

import com.sideproject.social.RequestBodies.UserRequestBody;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    private String username;

    private String password;

    private String profileImageURL;

    public User (UserRequestBody userRequestBody)
    {
        this.userId = userRequestBody.getUserId();
        this.username = userRequestBody.getUsername();
        this.password = userRequestBody.getPassword();
        this.profileImageURL = userRequestBody.getProfileImageURL();
    }
}
