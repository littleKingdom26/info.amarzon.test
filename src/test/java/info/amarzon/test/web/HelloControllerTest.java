package info.amarzon.test.web;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest  {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testHello() throws Exception {
		String hello = "hello";
		mockMvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("hello"));
	}

	@Test
	public void testHelloDTO() throws Exception {
		String name = "test";
		int amount = 1000;
		mockMvc.perform(get("/hello/dto").param("name",name)
				.param("amount",String.valueOf(amount)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name",is(name)))
				.andExpect(jsonPath("$.amount",is(amount)));
	}
}