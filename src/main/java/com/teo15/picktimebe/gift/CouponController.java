package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.gift.dto.CouponResponse;
import com.teo15.picktimebe.gift.dto.PostCouponRequest;
import com.teo15.picktimebe.target.dto.PostTargetRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystemException;

@RestController
@RequestMapping("/coupon")
@CrossOrigin(originPatterns = {"https://pick-time.vercel.app", "http://192.168.219.101:3000", "http://localhost:3000"})
@ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@Api(tags = {"쿠폰 관리 API"})
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    /**
     * 쿠폰 생성 API
     * req : coupon title, image, description
     * res : couponId
     */
    @PostMapping
    @ApiOperation(value = "쿠폰 생성", notes = "쿠폰을 생성합니다.", response = Long.class)
    public ResponseEntity<Long> createCoupon(@ModelAttribute PostCouponRequest request, @RequestParam(value ="file", required=false) MultipartFile file) throws FileSystemException {
        Long couponId= couponService.createCoupon(request, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(couponId);
    }
}
