package com.se.sos.domain.comment.entity;

import com.se.sos.domain.reception.entity.Reception;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue
    Long id;

    String description; // 내용

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt; // 작성시간

    @ManyToOne
    @JoinColumn(name = "RECEPTION_ID")
    Reception reception;

    @Builder
    public Comment(String description, Reception reception) {
        this.description = description;
        this.reception = reception;
    }
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
