package com.teo15.picktimebe;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@Api(tags = {"Health Check API"})
@RequiredArgsConstructor
public class TestController {

    @PostMapping(value = "/api/v1/test")
    public ResponseEntity<?> AddToCart( ) {
//        var response = cartService.AddToCart(token, request);
        var response = "test resonse";
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/api/v1/test")
    public ResponseEntity<String> Test() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
