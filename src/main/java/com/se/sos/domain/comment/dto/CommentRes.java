package com.se.sos.domain.comment.dto;


import com.se.sos.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

public record CommentRes(
        UUID id,
        String content,
        LocalDateTime createdAt
) {
    public static CommentRes from(Comment comment) {
        return new CommentRes(
                comment.getId(),
                comment.getDescription(),
                comment.getCreatedAt()
        );
    }
}
