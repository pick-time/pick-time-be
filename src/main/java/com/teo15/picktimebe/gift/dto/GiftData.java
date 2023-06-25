package com.teo15.picktimebe.gift.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiftData {
    private Long giftId;
    private String giftUrl;
    private String giftImage;
    private String giftTitle;
    private String giftDescription;
}