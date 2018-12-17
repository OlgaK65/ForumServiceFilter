package telran.ashkelon2018.ForumService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.ForumService.domain.Post;
import telran.ashkelon2018.ForumService.dto.DatePeriodDto;
import telran.ashkelon2018.ForumService.dto.NewCommentDto;
import telran.ashkelon2018.ForumService.dto.NewPostDto;
import telran.ashkelon2018.ForumService.dto.PostUpdateDto;
import telran.ashkelon2018.ForumService.service.ForumService;

@RestController
@RequestMapping("/forum")

@Component
public class ForumController {

	@Autowired
	ForumService forumService;

	@PostMapping("/post")
	public Post addNewPost(@RequestBody NewPostDto newPost) {
		return forumService.addNewPost(newPost);
	}
	
	@GetMapping("/post/{id}")
	public Post getPost(@PathVariable String id) {
		return forumService.getPost(id);
	}
	
	@DeleteMapping("/post/{id}")
	public Post removePost(@PathVariable String id, @RequestHeader("Authorization") String token) {
		return forumService.removePost(id, token);
	}
	
	@PostMapping("/post/{id}")
	public Post updatePost(@RequestBody PostUpdateDto post, @RequestHeader("Authorization") String token) {
		return forumService.updatePost(post, token);
	}
	
	@PutMapping("/post/{id}/like")
	public boolean addLike(@PathVariable String id) {
		return forumService.addLike(id);
	}
	
	@PostMapping("/post/{id}/comment")
	public Post addComment(@PathVariable String id, @RequestBody NewCommentDto newComment) {
		return forumService.addComment(id, newComment);
	}
	
	@GetMapping("/posts/author/{author}")
	public Iterable<Post> findByAuthor(@PathVariable String author){
		return forumService.findByAuthor(author);
	}
	
	@GetMapping("/posts/datecreated")
	public Iterable<Post> findByDates(@RequestBody DatePeriodDto datesDto){
		return forumService.findByDate(datesDto);
	}
	
	@GetMapping("/posts/tags")
	Iterable<Post> findByTags(@RequestBody List<String> tags){
		return forumService.findByTags(tags);
	}

	
}
