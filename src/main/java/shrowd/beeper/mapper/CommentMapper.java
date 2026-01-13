package shrowd.beeper.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import shrowd.beeper.dto.response.CommentResponse;
import shrowd.beeper.entity.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "author.email", target = "authorEmail")
    CommentResponse mapToResponse(Comment comment);
}

