package com.se.sos;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.comment.entity.Comment;
import com.se.sos.domain.comment.repository.CommentRepository;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.TransferStatus;
import com.se.sos.domain.reception.repository.ReceptionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.format.DateTimeFormatter;

@SpringBootTest
@EnableJpaAuditing
class SosApplicationTests {

// 	@Test
// 	void contextLoads() {
// 		Ambulance ambulance = Ambulance.builder()
// 				.name("hi")
// 				.address("hello")
// 				.build();
// 		System.out.println("ambulance.getName() = " + ambulance.getName());

// 		Reception reception = Reception.builder()
// 				.status(TransferStatus.MOVE)
// 				.ambulance(ambulance)
// 				.build();
// 		System.out.println("reception = " + reception);


// 	}

    //	@Test
//	void contextLoads() {
//		Ambulance ambulance = Ambulance.builder()
//				.name("hi")
//				.address("hello")
//				.build();
//		System.out.println("ambulance.getName() = " + ambulance.getName());
//
//
//	}


    /*@Test()
    void commentTest() {

        Reception reception = Reception.builder().build();
        receptionRepository.save(reception);

        Comment comment = Comment.builder()
                .description("gigigigi")
                .reception(reception)
                .build();
        commentRepository.save(comment);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("comment.getCreatedAt() = " + comment.getCreatedAt());
        System.out.println("comment yyyy-MM-dd HH:mm = " + comment.getCreatedAt().format(formatter));
        Assertions.assertThat(comment.getCreatedAt()).isNotNull();

    }*/
}
