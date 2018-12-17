package telran.ashkelon2018.ForumService.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsConflictException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
