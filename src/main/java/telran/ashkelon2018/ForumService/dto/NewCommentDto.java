package telran.ashkelon2018.ForumService.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
public class NewCommentDto {
	String user;
	@Setter String message;
	Set<String> tags;	
}
