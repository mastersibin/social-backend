package com.sideproject.social.RequestBodies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestBody {
    private Integer userId;
    private String username;
    private String password;
    private String filename;
    private String profileImageURL;
}
