package com.teo15.picktimebe.target.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetFinalGiftResponse {
    private String giftTitle;
    private String giftImageUrl;

    public GetFinalGiftResponse(String giftTitle, String giftImageUrl) {
        this.giftTitle = giftTitle;
        this.giftImageUrl = giftImageUrl;
    }
}
