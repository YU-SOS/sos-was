package com.se.sos;

import com.se.sos.domain.ambulance.entity.Ambulance;
import com.se.sos.domain.reception.entity.Reception;
import com.se.sos.domain.reception.entity.TransferStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SosApplicationTests {

	@Test
	void contextLoads() {
		Ambulance ambulance = Ambulance.builder()
				.name("hi")
				.address("hello")
				.build();
		System.out.println("ambulance.getName() = " + ambulance.getName());

		Reception reception = Reception.builder()
				.status(TransferStatus.MOVE)
				.ambulance(ambulance)
				.build();
		System.out.println("reception = " + reception);


	}

}
