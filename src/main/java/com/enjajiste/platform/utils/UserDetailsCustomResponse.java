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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Boolean getIserror() {
		return iserror;
	}
	public void setIserror(Boolean iserror) {
		this.iserror = iserror;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}
