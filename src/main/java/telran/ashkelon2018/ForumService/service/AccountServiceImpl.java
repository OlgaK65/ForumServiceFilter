package telran.ashkelon2018.ForumService.service;

import java.time.LocalDateTime;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.ForumService.configuration.AccountConfiguration;
import telran.ashkelon2018.ForumService.configuration.AccountUserCredential;
import telran.ashkelon2018.ForumService.dao.UserAccountRepository;
import telran.ashkelon2018.ForumService.domain.UserAccount;
import telran.ashkelon2018.ForumService.dto.UserProfileDto;
import telran.ashkelon2018.ForumService.dto.UserRegDto;

import telran.ashkelon2018.ForumService.exceptions.UserExistsConflictException;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	AccountConfiguration accountConfiguration;
	
	@Override
	public UserProfileDto addUser(UserRegDto userRegDto, String token) {
		AccountUserCredential credential = accountConfiguration.tokenDecode(token);
		if(userRepository.existsById(credential.getLogin())) {
			throw new UserExistsConflictException();
		}
		String hashPassword =
				BCrypt.hashpw(credential.getPassword(), 
						BCrypt.gensalt());
		UserAccount userAccount = UserAccount.builder()
						.login(credential.getLogin())
						.password(hashPassword)
						.firstName(userRegDto.getFirstName())
						.lastName(userRegDto.getLastName())
						.role("User")
						.expdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()))
						.build();
		userRepository.save(userAccount);
		return UserProfileDto.builder()
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.login(userAccount.getLogin())
				.roles(userAccount.getRoles())
				.build();
	}

	private UserProfileDto convertToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto.builder()
				.firstName(userAccount.getFirstName())
				.lastName(userAccount.getLastName())
				.login(userAccount.getLogin())
				.roles(userAccount.getRoles())
				.build();
	}
	
	@Override
	public UserProfileDto editUser(UserRegDto userRegDto, String token) {
		
		AccountUserCredential credential  = null;
		try
		{
			credential = accountConfiguration.tokenDecode(token);
		} catch(Exception e) {
			return null;
		}
		
		UserAccount userAccount = userRepository.findById(credential.getLogin()).get();
		if(userRegDto.getFirstName() != null) {
			userAccount.setFirstName(userRegDto.getFirstName());
		}
		if(userRegDto.getLastName() != null) {
			userAccount.setLastName(userRegDto.getLastName());
		}
		return null;
	}

	@Override
	public UserProfileDto removeUser(String login, String token)  {
		
		AccountUserCredential credential  = null;
		try
		{
			credential = accountConfiguration.tokenDecode(token);
		} catch(Exception e) {
			return null;
		}
		UserAccount userAccountFromToken = userRepository.findById(credential.getLogin()).get();
		if(userAccountFromToken != null) {
		
			UserAccount userAccount = userRepository.findById(login).get();
			if(userAccount != null) {
				if(login.equals(credential.getLogin()) || userAccountFromToken.getRoles().contains(accountConfiguration.Admin)) {
				userRepository.delete(userAccount);
			}
			else
			  return null;	
			}
			else
				return null;	
		
			return convertToUserProfileDto(userAccount);
		}
		  return null;	
	}

	@Override
	public Set<String> addRole(String login, String role, String token) {
		
		AccountUserCredential credential  = null;
		try
		{
			credential = accountConfiguration.tokenDecode(token);
		} catch(Exception e) {
			return null;
		}
		UserAccount userAccountFromToken = userRepository.findById(credential.getLogin()).get();
		UserAccount userAccount = userRepository.findById(login).get();
		
		if(userAccountFromToken != null 
				&& userAccount != null 
				&& (login.equals(credential.getLogin()) || userAccountFromToken.getRoles().contains("Admin"))) {		
			   userAccount.addRole(role);
			   userRepository.save(userAccount);
		}
		else
		  return null;  
		  
		return userAccount.getRoles();
	}

	@Override
	public Set<String> removeRole(String login, String role, String token) {
		AccountUserCredential credential  = null;
		try
		{
			credential = accountConfiguration.tokenDecode(token);
		} catch(Exception e) {
			return null;
		}
		UserAccount userAccountFromToken = userRepository.findById(credential.getLogin()).get();
		UserAccount userAccount = userRepository.findById(login).get();
		
		if(userAccountFromToken != null 
				&& userAccount != null 
				&& (login.equals(credential.getLogin()) || userAccountFromToken.getRoles().contains("Admin"))) {	
				userAccount.removeRole(role);
				userRepository.save(userAccount);
		}
		else
		  return null;
				
		return userAccount.getRoles();
	}

	@Override
	public boolean changePassworf(String password, String token) {
		// FIXME
		AccountUserCredential credential = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credential.getLogin()).get();
 
		if(BCrypt.checkpw(credential.getPassword(), userAccount.getPassword())) {
			String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
			userAccount.setPassword(hashPassword);
			userAccount.setExpdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod()));
			userRepository.save(userAccount);
			return true;
		} 		
	
		return false;
	}

	@Override
	public UserProfileDto login(String token) {
		AccountUserCredential credential = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credential.getLogin()).get();
		if(userAccount == null) 
			return null;
		
		return convertToUserProfileDto(userAccount);
	}

}
