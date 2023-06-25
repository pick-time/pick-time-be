package com.teo15.picktimebe.target;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "target")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardImageUrl;
    private String message;
    private String senderUrl;
    private String recipientUrl;
    private String providerName;
    private String consumerName;

    public void giftLikeChange(Long giftId) {
        throw new UnsupportedOperationException("Unsupported giftLikeChange");
    }

    public void getLikedGift() {
        throw new UnsupportedOperationException("Unsupported getLikedGift");
    }
}
