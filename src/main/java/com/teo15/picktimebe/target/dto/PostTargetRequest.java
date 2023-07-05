package com.teo15.picktimebe.target.dto;

import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.dto.PostGiftRequest;
import com.teo15.picktimebe.target.Target;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostTargetRequest {
    private String cardImageUrl;
    private String cardMessage;
    private List<Long> giftList;
    private List<Long> couponList;
    /*
    public Target toEntity() {
        Target target = Target.builder()
                .cardImageUrl(cardImageUrl)
                .message(cardMessage)
                .giftList(new ArrayList<>())
                .build();

        return target;
    }*/
}
