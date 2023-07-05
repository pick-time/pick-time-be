package com.teo15.picktimebe.gift.dto;

import com.teo15.picktimebe.gift.Gift;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponData {
    private String couponTitle;
    private String couponImage;
    private String couponDescription;

    public CouponData(Gift gift){
        this.couponTitle = gift.getGiftTitle();
        this.couponImage = gift.getGiftImageUrl();
        this.couponDescription = gift.getGiftDescription();
    }
}