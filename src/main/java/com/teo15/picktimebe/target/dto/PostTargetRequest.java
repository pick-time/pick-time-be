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
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostTargetRequest {
    private String cardImageUrl;
    private String cardMessage;
    private Optional<List<Long>> giftList;
    private Optional<List<Long>> couponList;
}