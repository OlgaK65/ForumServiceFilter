package telran.ashkelon2018.ForumService.service;

import java.util.List;

import telran.ashkelon2018.ForumService.domain.Post;
import telran.ashkelon2018.ForumService.dto.DatePeriodDto;
import telran.ashkelon2018.ForumService.dto.NewCommentDto;
import telran.ashkelon2018.ForumService.dto.NewPostDto;
import telran.ashkelon2018.ForumService.dto.PostUpdateDto;

public interface ForumService {
	Post addNewPost(NewPostDto newPost);
	Post getPost(String id);
	Post removePost(String id, String token);
	Post updatePost(PostUpdateDto post, String token);
	boolean addLike(String id);
	Post addComment(String id, NewCommentDto newComment);
	Iterable<Post> findByTags(List<String> tags);
	Iterable<Post> findByAuthor(String author);
	Iterable<Post> findByDate(DatePeriodDto datesDto);
}
