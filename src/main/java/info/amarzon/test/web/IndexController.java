package info.amarzon.test.web;

import info.amarzon.test.service.posts.PostsService;
import info.amarzon.test.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	/**
	 * Index string.
	 *
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/")
	public String index(Model model){

		model.addAttribute("posts", postsService.findAllDesc());
		return "index";
	}

	/**
	 * Posts save string.
	 *
	 * @return the string
	 */
	@GetMapping("/posts/save")
	public String postsSave(){
		return "posts-save";
	}


	/**
	 * Posts update string.
	 *
	 * @param id    the id
	 * @param model the model
	 * @return the string
	 */
	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id , Model model){
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);
		return "posts-update";
	}


}
