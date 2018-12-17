package telran.ashkelon2018.ForumService.configuration;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@Configuration
@ManagedResource


public class AccountConfiguration {
	
	public String User = "User";
	public String Admin = "Admin";
	public String Moderator = "Moderaror";
	
	@Value("${exp.value}")
	int expPeriod;
	
	@ManagedAttribute
	public int getExpPeriod() {
		return expPeriod;
	}

	@ManagedAttribute
	public void setExpPeriod(int expPeriod) {
		this.expPeriod = expPeriod;
	}

	public AccountUserCredential tokenDecode(String token) {
		 
		int index = token.indexOf(" ");
		token = token.substring(index + 1);
		byte[] base64DecodeBytes = Base64.getDecoder().decode(token);
		token = new String(base64DecodeBytes);
			 
		String[] auth = token.split(":");
		AccountUserCredential credential = new AccountUserCredential(auth[0], auth[1]);
		return credential;
	}
}
