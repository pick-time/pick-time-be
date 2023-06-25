package com.teo15.picktimebe.gift;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PostGiftRequest {
    private String giftUrl;
    private String giftImageUrl;
    private String giftTitle;
    private String giftDescription;
}
