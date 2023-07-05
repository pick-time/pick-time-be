package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.gift.dto.CouponResponse;
import com.teo15.picktimebe.gift.dto.PostCouponRequest;
import com.teo15.picktimebe.target.dto.PostTargetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystemException;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    /**
     * 쿠폰 생성 API
     * req : coupon title, image, description
     * res : couponId
     */
    @PostMapping
    public ResponseEntity<Long> createCoupon(@ModelAttribute PostCouponRequest request, @RequestParam(value ="file", required=false) MultipartFile file) throws FileSystemException {
        Long couponId= couponService.createCoupon(request, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(couponId);
    }
}
