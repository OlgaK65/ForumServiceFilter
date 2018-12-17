package telran.ashkelon2018.ForumService.service;

import java.util.Set;

import telran.ashkelon2018.ForumService.dto.UserProfileDto;
import telran.ashkelon2018.ForumService.dto.UserRegDto;

public interface AccountService {
	
	UserProfileDto addUser(UserRegDto userRegDto, String token);
	UserProfileDto editUser(UserRegDto userRegDto, String token);
	UserProfileDto removeUser(String login, String token);
	Set<String> addRole(String login, String role, String token);
	Set<String> removeRole(String login, String role, String token);
	boolean changePassworf(String password, String token);
	UserProfileDto login(String token);

}
