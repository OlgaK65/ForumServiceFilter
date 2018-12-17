package telran.ashkelon2018.ForumService.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter

public class NewPostDto {
	
	@Setter String title;
	@Setter String content;
	String author;
	Set<String> tags;
}
