package com.se.sos;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootTest
@EnableJpaAuditing
class SosApplicationTests {

//	@Autowired
//	HospitalRepository hospitalRepository;
//	@Autowired
//	AmbulanceRepository ambulanceRepository;
//	@Autowired
//	PatientRepository patientRepository;
//	@Autowired
//	ReceptionRepository receptionRepository;
//
//	@Test
//	void contextLoads() {
//		Ambulance ambulance = Ambulance.builder()
//				.name("Ambulance Name")                  // 구급차 이름
//				.address("123 Main St")                  // 주소
//				.telephoneNumber("123-456-7890")         // 전화번호
//				.ambulanceId("AMB12345")                 // 구급차 ID
//				.password("securePassword")              // 비밀번호
//				.role(Role.AMB)                          // 역할 (예시로 AMB 역할 설정)
//				.location(new Location("37.5665", "126.9780")) // 구급차의 위치 (위도, 경도)
//				.imageUrl("http://example.com/image.png") // 이미지 URL
//				.build();
//		ambulanceRepository.save(ambulance);
//
//		Patient patient = Patient.builder()
//				.name("John Doe")                       // 환자 이름
//				.age(35)                                // 환자 나이
//				.phoneNumber("010-1234-5678")           // 전화번호
//				.symptom("Fever and Cough")             // 증상
//				.medication("Paracetamol, Cough Syrup") // 복용 약
//				.reference("Allergic to Penicillin")    // 특이사항
//				.gender(Gender.male)                    // 성별
//				.status(TransferStatus.MOVE)     // 이송 상태
//				.build();
//		patientRepository.save(patient);
//
//		String id = "bb9fc3d0-dd17-40e4-924c-9d6bbc09782f";
//		Optional<Hospital> optionalHospital = hospitalRepository.findById(UUID.fromString(id));
//
//		Reception reception = Reception.builder()
//				.startTime(LocalDateTime.now())    // 이송 시작 시간
//				.ambulance(ambulance)              // 연결된 구급차
//				.hospital(optionalHospital.get())                // 연결된 병원
//				.patient(patient)                  // 연결된 환자
//				.build();
//		receptionRepository.save(reception);
//		System.out.println("reception = " + reception);
//	}

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
