package com.teo15.picktimebe.gift;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gift")
@RequiredArgsConstructor
public class GiftController {

//    private GiftService giftService;
//    private CouponService couponService;

//    @GetMapping("/{targetId}")
//    public ResponseEntity<GiftResponse> getGiftList(@PathVariable("targetId") Long targetId) {
//
//        List<Gift> giftList = giftService.getGiftListForTarget(targetId);
//        List<Coupon> couponList = couponService.getCouponListForTarget(targetId);
//
//        // GiftResponse 객체 생성
//        GiftResponse giftResponse = new GiftResponse();
//        giftResponse.setGiftTotal(giftList.size());
////        giftResponse.setProviderName(giftList.get(0).getProviderName());
////        giftResponse.setConsumerName(giftList.get(0).getConsumerName());
//
//        for (Gift gift : giftList) {
//            GiftData giftData = new GiftData();
//            giftData.setGiftId(gift.getId());
//            giftData.setGiftUrl(gift.getGiftUrl());
//            giftData.setGiftImage(gift.getGiftImageUrl());
//            giftData.setGiftTitle(gift.getGiftTitle());
//            giftData.setGiftDescription(gift.getGiftDescription());
//            giftResponse.addGift(giftData);
//        }
//
//        for (Coupon coupon : couponList) {
//            CouponData couponData = new CouponData();
//            couponData.setCouponTitle(coupon.getCouponTitle());
//            couponData.setCouponImage(coupon.getCouponImageUrl());
//            giftResponse.addCoupon(couponData);
//        }
//
//        return ResponseEntity.ok(giftResponse);
//    }
}
