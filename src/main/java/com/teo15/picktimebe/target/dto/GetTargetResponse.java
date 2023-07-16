package com.teo15.picktimebe.target.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class GetTargetResponse {
    private String providerName;
    private String cardImageUrl;
    private String cardMessage;

    public GetTargetResponse(String providerName, String cardImageUrl, String cardMessage) {
        this.providerName = providerName;
        this.cardImageUrl = cardImageUrl;
        this.cardMessage = cardMessage;
    }
}
