package com.desafioapishop.requests.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserBody {
    private String email;
    private String name;
    private String password;
    private List<String> rolesNames = new ArrayList<>();
}
