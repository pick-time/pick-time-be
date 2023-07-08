package com.teo15.picktimebe.gift.dto;

import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.GiftType;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostCouponRequest {

    @Parameter(name = "타겟 아이디", required = true, description = "pathvariable로 주소창에 입력")
    public Long targetId;
    @Parameter(name = "쿠폰 이미지 URL", required = true, description = "쿠폰의 이미지 URL입니다.")
    public String couponImageUrl;
    @Parameter(name = "쿠폰 제목", required = true, description = "쿠폰의 제목입니다.",  example = "안마쿠폰")
    public String couponTitle;
    @Parameter(name = "쿠폰 설명", required = true, description = "쿠폰의 설명입니다.",  example = "안마를 1시간 받을 수 있다.")
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
