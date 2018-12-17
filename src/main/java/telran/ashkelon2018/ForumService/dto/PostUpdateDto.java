package telran.ashkelon2018.ForumService.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PostUpdateDto {
	String id;
	@Setter String title;
	@Setter String content;
	Set<String> tags;
}
