package com.se.sos.domain.admin.api;

import com.se.sos.domain.admin.dto.RegApproveReq;
import com.se.sos.domain.admin.dto.RegSummaryRes;
import com.se.sos.domain.ambulance.dto.AmbulanceRegRes;
import com.se.sos.domain.hospital.dto.HospitalRegRes;
import com.se.sos.domain.user.entity.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Tag(name = "어드민 API", description = "어드민 관련 API")
public interface AdminAPI {

    @Operation(summary = "회원가입 요청 목록 조회", description = "회원가입 요청 목록을 조회하는 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RegSummaryRes.class),
                            examples = {
                                    @ExampleObject(value = """
                                        {
                                             "status": 200,
                                             "message": "요청이 성공했습니다.",
                                             "data": {
                                                 "hospitalList": [
                                                     {
                                                         "id": "38be4284-2da1-4b35-b8cc-682095f2498e",
                                                         "name": "아산병원",
                                                         "address": "아산시 강남구 아산로 123",
                                                         "telephoneNumber": "02-1234-5678"
                                                     }
                                                 ],
                                                 "ambulanceList": [
                                                     {
                                                         "id": "d14cb59f-bd10-4602-a068-4b2f700b1717",
                                                         "name": "ambtest3",
                                                         "address": "ambtest3",
                                                         "telephoneNumber": "32123"
                                                     }
                                                 ]
                                             }
                                         }
                                    """)
                            })
            )
    })
    public ResponseEntity<?> getAllRegistrations();


    @Operation(summary = "회원가입 요청 상세 조회", description = "회원가입 요청을 상세 조회하는 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "요청 조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {HospitalRegRes.class, AmbulanceRegRes.class}
                            ),
                            examples = {
                                    @ExampleObject(value = """
                                                 {
                                                    "status": 200,
                                                    "message": "요청이 성공했습니다.",
                                                    "data": {
                                                        "id": "e509c8ef-cd32-492a-842f-1dcf6b7ddfbd",
                                                        "name": "ambtest",
                                                        "address": "ambtest",
                                                        "telephoneNumber": "321232",
                                                        "ambulanceId": "ambtest",
                                                        "password": "$2a$10$hP9y/2bRCe6srjtEW0rEzuQRfowPLvllYbtFRHNC0.ABw0lEhB9M2",
                                                        "location": {
                                                            "longitude": "32.123485",
                                                            "latitude": "18.456347"
                                                        },
                                                        "categories" : ["내과", "정형외과"],
                                                        "imageUrl": "test.com/test.png"
                                                    }
                                                }
                                            """)
                            }
                    )
            )
    })
    public ResponseEntity<?> getRegistration(
            @RequestParam(name = "role", required = true) Role role,
            @RequestParam(name = "id", required = true) UUID id
    );

    @Operation(summary = "회원가입 요청 수락/거절", description = "회원가입 요청을 수락/거절하는 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원가입 요청 수락/거절 성공",
                    content = @Content(
                            examples = @ExampleObject(value = """
                                    {
                                        "status" : 200,
                                        "message" : "회원가입 요청을 수락하였습니다."
                                    }
                                    """)
                    )
            )
    })
    public ResponseEntity<?> approveRegistration(
            @RequestParam(name = "role") Role role,
            @RequestParam(name = "id") UUID id,
            @RequestBody RegApproveReq regApproveReq
    );

    @Operation(summary = "SOS 시스템 상태 조회", description = "SOS 가입자 수 및 접수 갯수를 조회하는 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회에 성공하였습니다.",
                    content = @Content(
                            examples = @ExampleObject(value = """
                                    {
                                        "status" : 200,
                                        "message" : "요청이 성공하였습니다.",
                                        "data": {
                                            "hospitalCount": 0,
                                            "ambulanceCount": 1,
                                            "userCount": 0,
                                            "receptionCount": 0
                                        } 
                                    }
                                    """)
                    )
            )
    })
    public ResponseEntity<?> getSystemStatus();


}

