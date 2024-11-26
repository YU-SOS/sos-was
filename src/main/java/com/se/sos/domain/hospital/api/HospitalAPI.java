package com.se.sos.domain.hospital.api;

import com.se.sos.domain.hospital.dto.HospitalUpdateReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Tag(name = "병원 API", description = "병원 관련 API")
public interface HospitalAPI {

    @Operation(summary = "병원 목록 조회", description = "병원 목록 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다.",
                                "data": {
                                    "content": [
                                        {
                                            "id": "2fc5e3cd-8ba2-4757-a88e-6ddaef86adac",
                                            "name": "hostest",
                                            "address": "hostest",
                                            "telephoneNumber": "12342345",
                                            "imageUrl": "test.com/test.png",
                                            "location": {
                                                "longitude": "32.123485",
                                                "latitude": "18.456347"
                                            },
                                            "categories": [],
                                            "emergencyRoomStatus": null
                                        },
                                        {
                                            "id": "2ca469cc-2c8a-4015-b0ca-25dfb094b33b",
                                            "name": "hostest2",
                                            "address": "hostest2",
                                            "telephoneNumber": "123423452",
                                            "imageUrl": "test.com/test.png",
                                            "location": {
                                                "longitude": "32.123485",
                                                "latitude": "18.456347"
                                            },
                                            "categories": [
                                                {
                                                    "id": 1,
                                                    "name": "내과"
                                                },
                                                {
                                                    "id": 2,
                                                    "name": "정형외과"
                                                }
                                            ],
                                            "emergencyRoomStatus": null
                                        }
                                    ],
                                    "page": {
                                        "size": 10,
                                        "number": 0,
                                        "totalElements": 2,
                                        "totalPages": 1
                                    }
                                }
                            }
                            
                            """))

            )
    })
    public ResponseEntity<?> getAllHospitals(@RequestParam(name = "categories", required = false) List<String> categories,
                                          @PageableDefault(size = 10) Pageable pageable
    );

    @Operation(summary = "병원 목록 조회", description = "병원 목록 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다.",
                                "data": [
                                    {
                                            "id": "2ca469cc-2c8a-4015-b0ca-25dfb094b33b",
                                            "name": "hostest2",
                                            "address": "hostest2",
                                            "telephoneNumber": "123423452",
                                            "imageUrl": "test.com/test.png",
                                            "location": {
                                                "longitude": "32.123485",
                                                "latitude": "18.456347"
                                            },
                                            "categories": [
                                                {
                                                    "id": 1,
                                                    "name": "내과"
                                                },
                                                {
                                                    "id": 2,
                                                    "name": "정형외과"
                                                }
                                            ],
                                            "emergencyRoomStatus": null
                                    },
                                    {
                                            "id": "2ca469cc-2c8a-4015-b0ca-25dfb094123",
                                            "name": "hostest",
                                            "address": "hostest",
                                            "telephoneNumber": "123423452",
                                            "imageUrl": "test.com/test.png",
                                            "location": {
                                                "longitude": "32.123485",
                                                "latitude": "18.456347"
                                            },
                                            "categories": [
                                                {
                                                    "id": 3,
                                                    "name": "산부인과"
                                                },
                                                {
                                                    "id": 2,
                                                    "name": "정형외과"
                                                }
                                            ],
                                            "emergencyRoomStatus": null
                                    }
                                ]
                            }
                            """))

            )
    })
    public ResponseEntity<?> getAllHospitalsForUser();


    @Operation(summary = "병원 상세 정보 조회", description = "병원 상세 정보 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다.",
                                "data": {
                                    "id": "2fc5e3cd-8ba2-4757-a88e-6ddaef86adac",
                                    "name": "hostest",
                                    "address": "hostest",
                                    "telephoneNumber": "12342345",
                                    "imageUrl": "test.com/test.png",
                                    "location": {
                                        "longitude": "32.123485",
                                        "latitude": "18.456347"
                                    },
                                    "categories": [],
                                    "emergencyRoomStatus": null
                                }
                            }
                            """))
            )
    })
    public ResponseEntity<?> getHospitalDetails(@PathVariable(name = "hospitalId") UUID id);

    @Operation(summary = "수락한 접수 목록 조회", description = "수락한 접수 목록 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다.",
                                "data": [
                                        {
                                            "id": "e622fb61-5f1d-4491-9536-8accf674ea1e",
                                            "startTime": "2024-10-03T15:30:00",
                                            "ambulance": {
                                                "id": "e509c8ef-cd32-492a-842f-1dcf6b7ddfbd",
                                                "name": "ambtest",
                                                "address": "ambtest",
                                                "telephoneNumber": "321232",
                                                "location": {
                                                    "longitude": "32.123485",
                                                    "latitude": "18.456347"
                                                },
                                                "imageUrl": "test.com/test.png"
                                            },
                                            "patient": {
                                                "name": "홍길동2",
                                                "age": 21,
                                                "phoneNumber": "010-0900-0000",
                                                "medication": "need_to_type_change_list",
                                                "reference": "x",
                                                "gender": "male"
                                            },
                                            "receptionStatus": "PENDING",
                                            "paramedicRes": {
                                                "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                                "name": "테스트",
                                                "phoneNumber": "010-0000-0000"
                                            }
                                        },
                                        {
                                            "id": "f4c020d4-6688-4ce5-9303-7a0a32a9e048",
                                            "startTime": "2024-10-03T15:30:00",
                                            "ambulance": {
                                              "id": "e509c8ef-cd32-492a-842f-1dcf6b7ddfbd",
                                              "name": "ambtest",
                                              "address": "ambtest",
                                              "telephoneNumber": "321232",
                                              "location": {
                                                "longitude": "32.123485",
                                                "latitude": "18.456347"
                                              },
                                              "imageUrl": "test.com/test.png"
                                            },
                                            "patient": {
                                              "name": "홍길동2",
                                              "age": 21,
                                              "phoneNumber": "010-0900-0000",
                                              "medication": "need_to_type_change_list",
                                              "reference": "x",
                                              "gender": "male"
                                            },
                                            "receptionStatus": "PENDING",
                                            "paramedicRes": {
                                              "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                              "name": "테스트",
                                              "phoneNumber": "010-0000-0000"
                                            }
                                          },
                                          {
                                            "id": "79b78003-ddf3-46db-952a-eacf48680eb1",
                                            "startTime": "2024-10-03T15:30:00",
                                            "ambulance": {
                                              "id": "e509c8ef-cd32-492a-842f-1dcf6b7ddfbd",
                                              "name": "ambtest",
                                              "address": "ambtest",
                                              "telephoneNumber": "321232",
                                              "location": {
                                                "longitude": "32.123485",
                                                "latitude": "18.456347"
                                              },
                                              "imageUrl": "test.com/test.png"
                                            },
                                            "patient": {
                                              "name": "홍길동2",
                                              "age": 21,
                                              "phoneNumber": "010-0900-0000",
                                              "medication": "need_to_type_change_list",
                                              "reference": "x",
                                              "gender": "male"
                                            },
                                            "receptionStatus": "PENDING",
                                            "paramedicRes": {
                                              "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                              "name": "테스트",
                                              "phoneNumber": "010-0000-0000"
                                            }
                                          }
                                ]
                            }
                            """))
            )
    })
    public ResponseEntity<?> getAllHospitalReceptionAccept(@PathVariable(name = "hospitalId") UUID id);


    @Operation(summary = "병원 대상 접수 요청 목록 조회", description = "병원 대상 접수 요청 목록 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다.",
                                "data": {
                                    "content": [
                                        {
                                            "id": "e622fb61-5f1d-4491-9536-8accf674ea1e",
                                            "startTime": "2024-10-03T15:30:00",
                                            "ambulance": {
                                                "id": "e509c8ef-cd32-492a-842f-1dcf6b7ddfbd",
                                                "name": "ambtest",
                                                "address": "ambtest",
                                                "telephoneNumber": "321232",
                                                "location": {
                                                    "longitude": "32.123485",
                                                    "latitude": "18.456347"
                                                },
                                                "imageUrl": "test.com/test.png"
                                            },
                                            "patient": {
                                                "name": "홍길동2",
                                                "age": 21,
                                                "phoneNumber": "010-0900-0000",
                                                "medication": "need_to_type_change_list",
                                                "reference": "x",
                                                "gender": "male"
                                            },
                                            "receptionStatus": "PENDING",
                                            "paramedicRes": {
                                                "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                                "name": "테스트",
                                                "phoneNumber": "010-0000-0000"
                                            }
                                        },
                                        {
                                            "id": "f4c020d4-6688-4ce5-9303-7a0a32a9e048",
                                            "startTime": "2024-10-03T15:30:00",
                                            "ambulance": {
                                                "id": "e509c8ef-cd32-492a-842f-1dcf6b7ddfbd",
                                                "name": "ambtest",
                                                "address": "ambtest",
                                                "telephoneNumber": "321232",
                                                "location": {
                                                    "longitude": "32.123485",
                                                    "latitude": "18.456347"
                                                },
                                                "imageUrl": "test.com/test.png"
                                            },
                                            "patient": {
                                                "name": "홍길동2",
                                                "age": 21,
                                                "phoneNumber": "010-0900-0000",
                                                "medication": "need_to_type_change_list",
                                                "reference": "x",
                                                "gender": "male"
                                            },
                                            "receptionStatus": "PENDING",
                                            "paramedicRes": {
                                                "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                                "name": "테스트",
                                                "phoneNumber": "010-0000-0000"
                                            }
                                        },
                                        {
                                            "id": "79b78003-ddf3-46db-952a-eacf48680eb1",
                                            "startTime": "2024-10-03T15:30:00",
                                            "ambulance": {
                                                "id": "e509c8ef-cd32-492a-842f-1dcf6b7ddfbd",
                                                "name": "ambtest",
                                                "address": "ambtest",
                                                "telephoneNumber": "321232",
                                                "location": {
                                                    "longitude": "32.123485",
                                                    "latitude": "18.456347"
                                                },
                                                "imageUrl": "test.com/test.png"
                                            },
                                            "patient": {
                                                "name": "홍길동2",
                                                "age": 21,
                                                "phoneNumber": "010-0900-0000",
                                                "medication": "need_to_type_change_list",
                                                "reference": "x",
                                                "gender": "male"
                                            },
                                            "receptionStatus": "PENDING",
                                            "paramedicRes": {
                                                "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                                "name": "테스트",
                                                "phoneNumber": "010-0000-0000"
                                            }
                                        }
                                    ],
                                    "page": {
                                        "size": 10,
                                        "number": 0,
                                        "totalElements": 3,
                                        "totalPages": 1
                                    }
                                }
                            }
                            """))
            )
    })
    public ResponseEntity<?> getAllReceptionsByHospitalId(
            @PathVariable(name = "hospitalId") UUID id,
            @PageableDefault(size = 10) Pageable pageable
    );


    @Operation(summary = "병원 정보 수정", description = "병원 정보 수정 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "수정 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다."
                            }
                            """))
            )
    })
    public ResponseEntity<?> updateHospital(
            @PathVariable(name = "hospitalId") UUID id,
            @RequestBody HospitalUpdateReq hospitalUpdateReq
    );

    @Operation(summary = "응급실 수용 여부 상태 변경", description = "응급실 수용 여부 상태 변경 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "변경 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다."
                            }
                            """))
            )
    })
    public ResponseEntity<?> updateStatus(
            @PathVariable(name = "hospitalId") UUID id,
            @RequestParam(name = "emergencyStatus") boolean emergencyStatus
    );





}
