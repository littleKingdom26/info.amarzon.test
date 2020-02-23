package info.amarzon.test.web.dto;

import info.amarzon.test.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
	private Long id;
	private String title;
	private String content;
	private String author;
	private LocalDateTime modifiedDate;

	public PostsListResponseDto(Posts entity){
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.content = entity.getContent();
		this.author = entity.getAuthor();
		this.modifiedDate = entity.getModifiedDate();
	}
}
