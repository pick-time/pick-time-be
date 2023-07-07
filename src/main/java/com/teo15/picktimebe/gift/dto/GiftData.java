package com.teo15.picktimebe.gift.dto;

import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.GiftType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GiftData {
    private Long giftId;
    private String giftUrl;
    private String giftImage;
    private String giftTitle;
    private String giftDescription;

    public GiftData(Gift gift) {
        this.giftId = gift.getId();
        this.giftUrl = gift.getGiftUrl();
        this.giftImage = gift.getGiftImageUrl();
        this.giftTitle = gift.getGiftTitle();
        this.giftDescription = gift.getGiftDescription();
    }

    public Gift toEntity(){
        return Gift.builder()
                .giftDescription(giftDescription)
                .giftTitle(giftTitle)
                .giftUrl(giftUrl)
                .giftImageUrl(giftImage)
                .giftType(GiftType.PRODUCT).build();

    }
}