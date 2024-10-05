package com.se.sos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.*;

@SpringBootApplication
//@EnableJpaAuditing  test파일에서 실행할 때는 주석 처리 필요
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class SosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SosApplication.class, args);
	}

}
