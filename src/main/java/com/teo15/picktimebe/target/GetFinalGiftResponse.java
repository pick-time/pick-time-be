package com.teo15.picktimebe.target;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetFinalGiftResponse {
    private String giftTitle;
    private String giftImageUrl;

    public GetFinalGiftResponse(String giftTitle, String giftImageUrl) {
        this.giftTitle = giftTitle;
        this.giftImageUrl = giftImageUrl;
    }
}
