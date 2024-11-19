package com.se.sos.domain.auth.api;

import com.se.sos.domain.auth.dto.AdminLoginReq;
import com.se.sos.domain.auth.dto.AmbulanceSignupReq;
import com.se.sos.domain.auth.dto.HospitalSignupReq;
import com.se.sos.domain.auth.dto.UserSignupReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "인증 API", description = "인증 관련 API")
public interface AuthAPI {

    @Operation(summary = "구급대 회원가입", description = "구급대 회원가입 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "구급대 회원가입 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 201,
                                "message" : "구급대원 등록에 성공하였습니다"
                            }
                            """))
            )
    })
    public ResponseEntity<?> signup(@Valid @RequestBody AmbulanceSignupReq ambulanceSignupReq);


    @Operation(summary = "병원 회원가입", description = "병원 회원가입 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "병원 회원가입 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 201,
                                "message" : "병원 등록에 성공하였습니다"
                            }
                            """))
            )
    })
    public ResponseEntity<?> signup(@Valid @RequestBody HospitalSignupReq hospitalSignupReq);


    @Operation(summary = "유저 로그인 및 회원가입", description = "카카오톡 oauth2 서비스를 통해 인증받은 유저가 로그인(회원가입 포함)하는 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 200,
                                "message" : "로그인 성공"
                            }
                            """))
            )
    })
    public ResponseEntity<?> login(@Valid @RequestBody UserSignupReq userSignupReq);


    @Operation(summary = "어드민 로그인", description = "어드민 로그인 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 200,
                                "message" : "로그인 성공"
                            }
                            """))
            )
    })
    public ResponseEntity<?> loginForAdmin(@Valid @RequestBody AdminLoginReq adminLoginReq);


    @Operation(summary = "로그아웃", description = "로그아웃 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "로그아웃 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 200,
                                "message" : "로그아웃 성공"
                            }
                            """))
            )
    })
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader);


    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "토큰 재발급 성공",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 200,
                                "message" : "토큰 재발급에 성공하였습니다."
                            }
                            """))
            )
    })
    public ResponseEntity<?> reissueToken(@CookieValue("refreshToken") String refreshToken);

    @Operation(summary = "토큰 재발급", description = "토큰 재발급 API")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "사용가능한 아이디",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 200,
                                "message" : "사용가능한 아이디입니다."
                            }
                            """))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 사용 중인 아이디 존재",
                    content = @Content(examples = @ExampleObject(value = """
                            {
                                "status" : 409,
                                "message" : "이미 사용 중인 아이디입니다."
                            }
                            """))
            )
    })
    public ResponseEntity<?> dupCheck(
            @RequestParam(name = "id", required = true) String id,
            @RequestParam(name = "role", required = true) String role
    );


}
