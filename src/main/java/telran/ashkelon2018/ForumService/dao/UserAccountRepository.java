package telran.ashkelon2018.ForumService.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2018.ForumService.domain.UserAccount;

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

}
