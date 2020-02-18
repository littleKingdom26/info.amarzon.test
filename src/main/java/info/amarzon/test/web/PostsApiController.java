package info.amarzon.test.web;

import info.amarzon.test.service.posts.PostsService;
import info.amarzon.test.web.dto.PostsResponseDto;
import info.amarzon.test.web.dto.PostsSaveRequestDto;
import info.amarzon.test.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

	private final PostsService postsService;

	@PostMapping("api/v1/posts")
	public Long save(@RequestBody
					 PostsSaveRequestDto postsSaveRequestDto){
		return postsService.save(postsSaveRequestDto);
	}

	@PutMapping("api/v1/posts/{id}")
	public long update(@PathVariable Long id,@RequestBody PostsUpdateRequestDto requestDto){
		return postsService.update(id, requestDto);
	}

	@GetMapping("api/v1/posts/{id}")
	public PostsResponseDto findbyId(@PathVariable Long id){
		return postsService.findById(id);
	}


}
