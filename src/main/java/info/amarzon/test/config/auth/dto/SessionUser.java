package info.amarzon.test.config.auth.dto;

import info.amarzon.test.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

	private String name;
	private String email;
	private String picture;

	public SessionUser(User user){
		name = user.getName();
		email = user.getEmail();
		picture = user.getPicture();
	}
}
