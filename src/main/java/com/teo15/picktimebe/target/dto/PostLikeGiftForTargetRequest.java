package com.teo15.picktimebe.target.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostLikeGiftForTargetRequest {
    private Long targetId;
    private Long giftId;
    private Boolean isGift;
}
