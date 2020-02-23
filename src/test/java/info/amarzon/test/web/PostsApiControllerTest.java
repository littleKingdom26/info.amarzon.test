package info.amarzon.test.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.amarzon.test.domain.posts.Posts;
import info.amarzon.test.domain.posts.PostsRepository;
import info.amarzon.test.web.dto.PostsSaveRequestDto;
import info.amarzon.test.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;


	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@Before
	public void	setup(){
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
	}


	@After
	public void tearDown() throws Exception {
		postsRepository.deleteAll();
	}

	@Test
	@WithMockUser(roles="USER")
	public void Posts_Insert() throws Exception {
		// given
		String title = "타이틀";
		String content = "컨텐츠";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder().title(title).content(content).author("계정").build();

		String url = "http://localhost:" + port + "/api/v1/posts";
		System.out.println("port >>> "+port);
		// when
		mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(requestDto)))
				.andExpect(status().isOk());


		//then
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);

	}

	@Test
	@WithMockUser(roles = "USER")
	public void Posts_Update() throws Exception {
		//given
		Posts savePosts = postsRepository.save(Posts.builder().title("title").content("content").author("author").build());

		Long updateId = savePosts.getId();

		String expectedTitle = "제목";
		String expectedContent = "컨텐츠";



		PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();

		String url = "http://localhost:"+port+"/api/v1/posts/"+updateId;
		HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);


		// when
		mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(requestDto))).andExpect(status().isOk());


		// then

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent()).isEqualTo(expectedContent);



	}





}