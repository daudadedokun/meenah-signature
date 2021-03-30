package com.meenah.meenahsignature.jwt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UsernameAndPasswordAuthRequest {

    private final String username;
    private final String password;
}
