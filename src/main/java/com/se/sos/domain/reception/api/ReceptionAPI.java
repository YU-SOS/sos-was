package com.se.sos.domain.reception.api;

import com.se.sos.domain.comment.dto.CommentReq;
import com.se.sos.domain.reception.dto.ReceptionApproveReq;
import com.se.sos.domain.reception.dto.ReceptionCreateReq;
import com.se.sos.domain.reception.dto.ReceptionReVisitReq;
import com.se.sos.global.response.success.SuccessRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@Tag(name = "접수 API", description = "접수 관련 API")
public interface ReceptionAPI {

    @Operation(summary = "접수 생성(요청)", description = "접수 요청 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "접수 생성 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 201,
                                "message" : "등록을 성공하였습니다.",
                                "data" : "2fc5e3cd-8ba2-4757-a88e-6ddaef86adac"
                            }
                            """))
            )
    })
    public ResponseEntity<?> createReception(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody ReceptionCreateReq receptionCreateDto
    );

    @Operation(summary = "접수 조회", description = "접수 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "접수 조회 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                 "status": 200,
                                 "message": "요청이 성공했습니다.",
                                 "data": {
                                     "id": "378f8f40-cc5f-46a9-8c91-992197e5f939",
                                     "startTime": "2024-10-03T15:30:00",
                                     "endTime": null,
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
                                     "hospital": {
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
                                     "patient": {
                                         "name": "홍길동2",
                                         "age": 21,
                                         "phoneNumber": "010-0900-0000",
                                         "medication": "need_to_type_change_list",
                                         "reference": "x",
                                         "gender": "male"
                                     },
                                     "comments": [],
                                     "receptionStatus": "PENDING",
                                     "paramedic": {
                                         "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                         "name": "테스트",
                                         "phoneNumber": "010-0000-0000"
                                     }
                                 }
                             }
                            """))
            )
    })
    public ResponseEntity<?> getReception(@PathVariable(name = "receptionId") UUID id);

    @Operation(summary = "게스트 접수 조회", description = "게스트 접수 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "접수 조회 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다.",
                                "data": {
                                    "hospital": {
                                        "name": "hostest2",
                                        "address": "hostest2",
                                        "imageUrl": "test.com/test.png",
                                        "location": {
                                            "longitude": "32.123485",
                                            "latitude": "18.456347"
                                        }
                                    },
                                    "ambulance": {
                                        "name": "ambtest",
                                        "address": "ambtest",
                                        "imageUrl": "test.com/test.png"
                                    }
                                }
                            }
                            """))
            )
    })
    public ResponseEntity<?> getReceptionForGuest(@PathVariable(name = "receptionId") UUID id);

    @Operation(summary = "접수 요청 수락/거절", description = "접수 요청 수락/거절 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "접수 요청 수락/거절 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다."
                            }
                            """))
            )
    })
    public ResponseEntity<?> acceptReceptionRequest(
            @PathVariable(name = "receptionId") UUID id,
            @Valid @RequestBody ReceptionApproveReq req
    );

    @Operation(summary = "접수 거절 후 재요청", description = "접수 거절 후 재요청 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "접수 거절 후 재요청 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다.",
                                "data" : "378f8f40-cc5f-46a9-8c91-992197e5f939"
                            }
                            """))
            )
    })
    public ResponseEntity<?> retryReceptionRequest(
            @PathVariable(name = "receptionId") UUID id,
            @Valid @RequestBody ReceptionReVisitReq req
    );

    @Operation(summary = "접수 종료(병원 도착)", description = "접수 종료(병원 도착) API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "접수 종료 요청 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 200,
                                "message": "요청이 성공했습니다."
                            }
                            """))
            )
    })
    public ResponseEntity<?> closeReception(
            @PathVariable(name = "receptionId") UUID id
    );

    @Operation(summary = "코멘트 작성", description = "코멘트 작성 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "코멘트 작성 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status": 201,
                                "message": "코멘트 등록에 성공했습니다.."
                            }
                            """))
            )
    })
    public ResponseEntity<?> addComment(
            @PathVariable(name = "receptionId") UUID id,
            @Valid @RequestBody CommentReq req
    );

}
