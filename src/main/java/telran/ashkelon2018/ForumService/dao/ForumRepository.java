package telran.ashkelon2018.ForumService.dao;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.ashkelon2018.ForumService.domain.Post;

public interface ForumRepository extends MongoRepository<Post, String>{
	
	Iterable<Post> findByAuthor(String author);
	

//db.pets.find( {type: {$in:["Cat", "Mouse"]}}).pretty()
	
 	//@Query("{ tags: { $in: ?0 } }")
	Iterable<Post> findByTagsIn(List<String> tags);
	
	//@Query("{'dateCreated': {'$gte':?0, '$lte':?1 }}")
	Iterable<Post> findByDateCreatedBetween (LocalDateTime minDate,  LocalDateTime maxDate);
}
