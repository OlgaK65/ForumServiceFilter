package telran.ashkelon2018.ForumService.domain;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = { "user", "dateCreated"})

public class Comment {
	
	String user;
	@Setter String message;
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	LocalDateTime dateCreated;
	Set<String> tags;
	int likes;
	
	public Comment(String user, String message) {
		super();
		this.user = user;
		this.message = message;
		
		dateCreated = LocalDateTime.now();
	}
	
	public void addLike() {
		likes++;
	}
}
