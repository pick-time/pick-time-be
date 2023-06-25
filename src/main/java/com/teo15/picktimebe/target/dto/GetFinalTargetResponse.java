package com.teo15.picktimebe.target.dto;


import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetFinalTargetResponse {
    private String consumerName;
    private GetFinalGiftResponse finalGift;

    public GetFinalTargetResponse(String consumerName, GetFinalGiftResponse finalGift) {
        this.consumerName = consumerName;
        this.finalGift = finalGift;
    }
}
