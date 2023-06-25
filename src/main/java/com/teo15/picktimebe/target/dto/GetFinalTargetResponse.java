package com.teo15.picktimebe.target.dto;

import lombok.*;
import com.teo15.picktimebe.target.dto.GetFinalGiftResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
