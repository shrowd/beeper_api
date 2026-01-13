package shrowd.beeper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shrowd.beeper.dto.response.PostResponse;
import shrowd.beeper.entity.Post;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "author.email", target = "authorEmail")
    PostResponse mapToResponse(Post post);
}

