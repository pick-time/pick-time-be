package com.teo15.picktimebe.gift.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class GiftResponse {
    private int giftTotal;
    private String providerName;
    private String consumerName;
    private List<GiftData> giftList;
    private List<CouponData> couponList;

    public GiftResponse() {
        this.giftList = new ArrayList<>();
        this.couponList = new ArrayList<>();
    }

    public void addGift(GiftData gift) {
        this.giftList.add(gift);
    }

    public void addCoupon(CouponData coupon) {
        this.couponList.add(coupon);
    }

    @Builder
    public GiftResponse(int giftTotal, String providerName, String consumerName, List<GiftData> giftList, List<CouponData> couponList) {
        this.giftTotal = giftTotal;
        this.providerName = providerName;
        this.consumerName = consumerName;
        this.giftList = giftList;
        this.couponList = couponList;
    }
}
