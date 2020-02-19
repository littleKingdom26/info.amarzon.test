package info.amarzon.test.service.posts;

import info.amarzon.test.domain.posts.Posts;
import info.amarzon.test.domain.posts.PostsRepository;
import info.amarzon.test.web.dto.PostsListResponseDto;
import info.amarzon.test.web.dto.PostsResponseDto;
import info.amarzon.test.web.dto.PostsSaveRequestDto;
import info.amarzon.test.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostsService {

	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequestDto postsSaveRequestDto) {
		return postsRepository.save(postsSaveRequestDto.toEntity()).getId();

	}

	@Transactional
	public Long update(Long id,PostsUpdateRequestDto requestDto){
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id ="+id));
		posts.update(requestDto.getTitle(),requestDto.getContent());
		return id;
	}

	public PostsResponseDto findById(Long id){
		Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return new PostsResponseDto(entity);

	}

	@Transactional(readOnly = true)
	public List<PostsListResponseDto> findAllDesc(){
		return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());
	}

	@Transactional
	public void delete(Long id){
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		postsRepository.delete(posts);
	}
}
