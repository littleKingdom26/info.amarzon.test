package info.amarzon.test.domain.posts;


import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@After
	public void cleanup(){
		postsRepository.deleteAll();
	}


	@Test
	public void board_save(){
		String title = "제목";
		String content = "본문";

		postsRepository.save(Posts.builder()
				.title(title)
				.content(content)
				.author("wingidnream@gmail.com")
				.build());

		List<Posts> postsList = postsRepository.findAll();

		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
	}

	@Test
	public void BaseTime_insert(){
		// given
		LocalDateTime now = LocalDateTime.of(2020,2,18,0,0,0);
		postsRepository.save(Posts.builder().title("title").content("contnet").author("a").build());
		// when
		List<Posts> postsList = postsRepository.findAll();

		Posts posts = postsList.get(0);


		System.out.println(">>>>>>> created Date "+posts.getCreatedDate());
		System.out.println(">>>>>>> getModifyDate Date " + posts.getModifyDate());

		assertThat(posts.getCreatedDate()).isAfter(now);
		assertThat(posts.getModifyDate()).isAfter(now);

	}
}