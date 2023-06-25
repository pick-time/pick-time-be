package com.teo15.picktimebe.target;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetFinalTargetResponse {
    private String consumerName;
    private GetFinalGiftResponse finalGift;

    public GetFinalTargetResponse(String consumerName, GetFinalGiftResponse finalGift) {
        this.consumerName = consumerName;
        this.finalGift = finalGift;
    }
}
