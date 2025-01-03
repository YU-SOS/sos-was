package com.se.sos.domain.comment.entity;

import com.se.sos.domain.hospital.entity.Hospital;
import com.se.sos.domain.reception.entity.Reception;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotNull
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
