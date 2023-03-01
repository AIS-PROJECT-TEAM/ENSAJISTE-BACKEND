package com.enjajiste.platform.utils;

import com.enjajiste.platform.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsCustomResponse {

    private User user;
    private String message;
    private Boolean iserror;
    private Integer status;
}
