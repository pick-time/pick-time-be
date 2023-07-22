package com.teo15.picktimebe.gift.dto;

import com.teo15.picktimebe.gift.Gift;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UpdateGiftInfoRequest {

    private String giftTitle;
    private String giftDescription;

    public UpdateGiftInfoRequest(String giftTitle, String giftDescription){
        this.giftTitle = giftTitle;
        this.giftDescription = giftDescription;
    }

    public Gift toEntity(Gift gift){
        if (giftTitle != null) {
            gift.setGiftTitle(giftTitle);
        }
        if (giftDescription != null) {
            gift.setGiftDescription(giftDescription);
        }
        return gift;
    }
}
