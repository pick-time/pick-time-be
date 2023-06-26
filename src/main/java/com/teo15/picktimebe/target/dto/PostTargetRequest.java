package com.teo15.picktimebe.target.dto;

import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.PostGiftRequest;
import com.teo15.picktimebe.target.Target;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

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
    private String providerName;
    private String consumerName;
    private List<PostGiftRequest> giftList;

    public Target toEntity() {
        Target target = new Target(cardImageUrl, cardMessage, providerName, consumerName, new ArrayList<>());

        for (PostGiftRequest postGiftRequest : giftList) {
            Gift gift = new Gift(postGiftRequest.getGiftUrl(), postGiftRequest.getGiftImageUrl(),
                    postGiftRequest.getGiftTitle(), postGiftRequest.getGiftDescription());
            gift.setTarget(target);
            target.getGiftList().add(gift);
        }

        return target;
    }
}
