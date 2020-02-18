package info.amarzon.test.web;


import info.amarzon.test.domain.posts.Posts;
import info.amarzon.test.domain.posts.PostsRepository;
import info.amarzon.test.web.dto.PostsSaveRequestDto;
import info.amarzon.test.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@After
	public void tearDOwn(){
		postsRepository.deleteAll();
	}
	@Test
	public void testSave() {
		// given
		String title = "타이틀";
		String content = "컨텐츠";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("계정").build();

		String url = "http://localhost:" + port + "/api/vi/posts";
		System.out.println("port >>> "+port);
		// when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);


		//then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);

	}

	@Test
	public void psotsUpdate(){
		//given
		Posts savePosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

		Long updateId = savePosts.getId();

		String expectedTitle = "제목";
		String expectedContent = "컨텐츠";

		PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

		String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;
		HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);


		// when
		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);


		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent()).isEqualTo(expectedContent);



	}





}