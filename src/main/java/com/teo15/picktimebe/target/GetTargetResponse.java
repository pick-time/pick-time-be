package com.teo15.picktimebe.target;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class GetTargetResponse {
    private String providerName;
    private String cardImageUrl;
    private String cardMeesage;

    public GetTargetResponse(String providerName, String cardImageUrl, String cardMeesage) {
        this.providerName = providerName;
        this.cardImageUrl = cardImageUrl;
        this.cardMeesage = cardMeesage;
    }
}
