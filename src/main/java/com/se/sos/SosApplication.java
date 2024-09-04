package com.se.sos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
//@EnableJpaAuditing  test파일에서 실행할 때는 주석 처리 필요
public class SosApplication {

	public static void main(String[] args) {
		SpringApplication.run(SosApplication.class, args);
	}

}
