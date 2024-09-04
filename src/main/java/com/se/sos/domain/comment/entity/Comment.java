package com.se.sos.domain.comment.entity;

import com.se.sos.domain.reception.entity.Reception;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Comment {
    @Id
    @GeneratedValue
    Long id;

    String description; // 내용
    LocalDateTime createdAt; // 작성시간

    @ManyToOne
    @JoinColumn(name = "RECEPTION_ID")
    Reception reception;

    @Builder
    public Comment(String description, LocalDateTime createdAt, Reception reception) {
        this.description = description;
        this.createdAt = createdAt;
        this.reception = reception;
    }
}
