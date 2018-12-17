package telran.ashkelon2018.ForumService.dto;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class DatePeriodDto {
	@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
	LocalDateTime minDate;
	@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss")
	LocalDateTime maxDate;
}
