package telran.ashkelon2018.ForumService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2018.ForumService.configuration.AccountConfiguration;
import telran.ashkelon2018.ForumService.configuration.AccountUserCredential;
import telran.ashkelon2018.ForumService.dao.ForumRepository;
import telran.ashkelon2018.ForumService.dao.UserAccountRepository;
import telran.ashkelon2018.ForumService.domain.Comment;
import telran.ashkelon2018.ForumService.domain.Post;
import telran.ashkelon2018.ForumService.domain.UserAccount;
import telran.ashkelon2018.ForumService.dto.DatePeriodDto;
//import telran.ashkelon2018.ForumService.dto.DatePeriodDto;
import telran.ashkelon2018.ForumService.dto.NewCommentDto;
import telran.ashkelon2018.ForumService.dto.NewPostDto;
import telran.ashkelon2018.ForumService.dto.PostUpdateDto;

@Component
public class ForumServiceImpl implements ForumService {
	
	@Autowired
	ForumRepository forumRepository;
	
	@Autowired
	UserAccountRepository userRepository;
	
	@Autowired
	AccountConfiguration accountConfiguration;
	
	@Override
	public Post addNewPost(NewPostDto newPost) {	
		if(newPost.getAuthor() == null)
		  return null;
			
		Post post = new Post(newPost.getTitle(), newPost.getContent(), newPost.getAuthor(), newPost.getTags()); 
		forumRepository.save(post);	
		return post;	
	}	
	
	@Override
	public Post getPost(String id) {
		return forumRepository.findById(id).orElse(null);
	}
	
	@Override
	public Post removePost(String id, String token) {
		
		AccountUserCredential credential  = null;
		try
		{
			credential = accountConfiguration.tokenDecode(token);
		} catch(Exception e) {
			return null;
		}
		
		UserAccount userAccountFromToken = userRepository.findById(credential.getLogin()).get();
		Post post = forumRepository.findById(id).orElse(null);
		if(post == null)
		  return null;
			
		UserAccount userAccount = userRepository.findById(post.getAuthor()).get();
		
		if(userAccountFromToken != null 
				&& userAccount != null 
				&& (userAccount.equals(userAccountFromToken) 
						|| userAccountFromToken.getRoles().contains(accountConfiguration.Admin))
						|| userAccountFromToken.getRoles().contains(accountConfiguration.Moderator)) {	
			forumRepository.delete(post);   
		}
		else
		  return null;
		  
		return post;
	}
	
	@Override
	public Post updatePost(PostUpdateDto post, String token) {
		
		AccountUserCredential credential  = null;
		try
		{
			credential = accountConfiguration.tokenDecode(token);
		} catch(Exception e) {
			return null;
		}
		
		UserAccount userAccountFromToken = userRepository.findById(credential.getLogin()).get();
		Post pt = forumRepository.findById(post.getId()).orElse(null);
		if(pt == null)return null;
			
		UserAccount userAccount = userRepository.findById(pt.getAuthor()).get();
		
		if(userAccountFromToken != null 
				&& userAccount != null 
				&& (userAccount.equals(userAccountFromToken))) {
						
			if(post.getTitle()!= null) {
				pt.setTitle(post.getTitle());
			}
			if(post.getContent() != null) {
				pt.setContent(post.getContent());
			}
			forumRepository.save(pt);
		}
		return pt;	
	}
	
	@Override
	public boolean addLike(String id
			) {
		Post post = forumRepository.findById(id).orElse(null);
		if(post == null)return false;
		
		post.addLike();
		forumRepository.save(post);
        return true;
	}
	
	@Override
	public Post addComment(String id, NewCommentDto newComment) {	 
		if(newComment.getUser() == null)
		  return null;
		
		Post post = forumRepository.findById(id).orElse(null);
		if(post == null)return null;
		
		post.addComment(new Comment(newComment.getUser(), newComment.getMessage()));
		forumRepository.save(post);
        return post;
	}
	
	@Override
	public Iterable<Post> findByAuthor(String author){
		return forumRepository.findByAuthor(author);
	}
	
	@Override
	public Iterable<Post> findByDate(DatePeriodDto datesDto){
		return forumRepository.findByDateCreatedBetween (datesDto.getMinDate(), datesDto.getMaxDate());
	}

	@Override
	public Iterable<Post> findByTags(List<String> tags) {
		return forumRepository.findByTagsIn(tags);
	}
}