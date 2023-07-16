package com.teo15.picktimebe.target.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetFinalGiftResponse {
    private String giftTitle;
    private String giftImageUrl;
    private String giftUrl;

    public GetFinalGiftResponse(String giftTitle, String giftImageUrl, String giftUrl) {
        this.giftTitle = giftTitle;
        this.giftImageUrl = giftImageUrl;
        this.giftUrl = giftUrl;
    }
}
