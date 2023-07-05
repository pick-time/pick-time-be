package com.teo15.picktimebe.gift.dto;

import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.GiftType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostCouponRequest {
    public Long targetId;
    public String couponImageUrl;
    public String couponTitle;
    public String couponDescription;

    public Gift toEntity(){
        return Gift.builder()
                .giftType(GiftType.COUPON)
                .giftImageUrl(couponImageUrl)
                .giftTitle(couponTitle)
                .giftDescription(couponDescription)
                .build();
    }
}
