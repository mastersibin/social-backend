package com.sideproject.social.RequestBodies;

import com.sideproject.social.Entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequestBody {
    private String username;
    private String password;
    private String filename;
}
