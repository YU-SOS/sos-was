package com.se.sos.domain.ambulance.api;

import com.se.sos.domain.paramedic.dto.ParamedicReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Tag(name = "구급대 API", description = "구급대 관련 API")
public interface AmbulanceAPI {

    @Operation(summary = "구급대 정보 조회", description = "구급대 정보 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "구급대 정보 조회 성공",
                    content = @Content(
                            examples = @ExampleObject(value = """
                                    {
                                        "status": 200,
                                        "message": "요청이 성공했습니다.",
                                        "data": {
                                            "id": "375c11ce-abe6-4410-b919-45ec17513062",
                                            "name": "ambtest3",
                                            "address": "ambtest3",
                                            "telephoneNumber": "32123",
                                            "location": {
                                                "longitude": "32.123485",
                                                "latitude": "18.456347"
                                            },
                                            "imageUrl": "test.com/test.png"
                                        }
                                    }
                                    """)
                    )
            )
    })
    public ResponseEntity<?> getAmbulance(@PathVariable(name = "ambulanceId") UUID ambulanceId);


    @Operation(summary = "구급대원 정보 조회", description = "구급대원 정보 조회 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "구급대원 정보 조회 성공",
                    content = @Content(
                            examples = @ExampleObject(value = """
                                    {
                                          "status": 200,
                                          "message": "요청이 성공했습니다.",
                                          "data": [
                                              {
                                                  "id": "094140b7-1ff8-494c-9c36-f5b66fb1000c",
                                                  "name": "테스트",
                                                  "phoneNumber": "010-0000-0000"
                                              },
                                              {
                                                  "id": "cb649af9-fa2a-4ab8-9753-49ae74593f44",
                                                  "name": "테스트2",
                                                  "phoneNumber": "010-0000-00002"
                                              },
                                              {
                                                  "id": "61af4259-6c93-4324-a93d-f61723ee8d23",
                                                  "name": "테스트3",
                                                  "phoneNumber": "010-0000-00003"
                                              }
                                          ]
                                    }
                                    """)
                    )
            )
    })
    public ResponseEntity<?> getAllParamedic(@PathVariable(name = "ambulanceId") UUID ambulanceId);

    @Operation(summary = "구급대원 추가", description = "구급대원 추가 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "구급대원 추가 성공",
                    content = @Content(
                            examples = @ExampleObject( value = """
                                    {
                                        "status" : 200,
                                        "message" : "요청이 성공하였습니다",
                                    }
                                    """))
            )
    })
    public ResponseEntity<?> addParamedic(
            @PathVariable(name = "ambulanceId") UUID id,
            @Valid @RequestBody ParamedicReq paramedicReq
    );

    @Operation(summary = "구급대원 정보 수정", description = "구급대원 정보 수정 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "구급대원 정보 수정 성공",
                    content = @Content(
                            examples = @ExampleObject( value = """
                                    {
                                        "status" : 200,
                                        "message" : "요청이 성공하였습니다",
                                    }
                                    """))
            )
    })
    public ResponseEntity<?> updateParamedic(
            @PathVariable(name = "ambulanceId") UUID ambulanceId,
            @PathVariable(name = "memberId") UUID memberId,
            @Valid @RequestBody ParamedicReq paramedicReq
    );

    @Operation(summary = "구급대원 삭제", description = "구급대원 삭제 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "구급대원 삭제 성공",
                    content = @Content(
                            examples = @ExampleObject( value = """
                                    {
                                        "status" : 200,
                                        "message" : "요청이 성공하였습니다",
                                    }
                                    """))
            )
    })
    @DeleteMapping("/{ambulanceId}/member/{memberId}")
    public ResponseEntity<?> deleteParamedic(
            @PathVariable(name = "ambulanceId") UUID ambulanceId,
            @PathVariable(name = "memberId") UUID memberId
    );

}
