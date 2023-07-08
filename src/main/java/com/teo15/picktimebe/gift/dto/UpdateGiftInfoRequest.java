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
    private String giftImage;

    public UpdateGiftInfoRequest(String giftTitle, String giftDescription, String giftImage){
        this.giftTitle = giftTitle;
        this.giftDescription = giftDescription;
        this.giftImage = giftImage;
    }

    public Gift toEntity(Gift gift){
        if (giftTitle != null) {
            gift.setGiftTitle(giftTitle);
        }
        if (giftDescription != null) {
            gift.setGiftDescription(giftDescription);
        }
        if (giftImage != null) {
            gift.setGiftImageUrl(giftImage);
        }
        return gift;
    }
}
