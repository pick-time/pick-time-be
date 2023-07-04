package com.teo15.picktimebe.target;

import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "target")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "target_id")
    private Long id;

    private String cardImageUrl;
    private String message;
    private String senderUrl;
    private String recipientUrl;
    private String providerName;
    private String consumerName;
    @OneToMany(mappedBy = "target", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Gift> giftList;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Target(String providerName, String consumerName){
        this.providerName = providerName;
        this.consumerName = consumerName;
    }

    @Builder
    public Target(String cardImageUrl, String message, String providerName, String consumerName, List<Gift> giftList) {
        this.cardImageUrl = cardImageUrl;
        this.message = message;
        this.providerName = providerName;
        this.consumerName = consumerName;
        this.giftList = (giftList != null) ? giftList : new ArrayList<>();

        for (Gift gift : giftList) {
            gift.setTarget(this);
        }
    }

    public void addGift(Gift gift) {
        giftList.add(gift);
        gift.setTarget(this);
    }

    public void giftLikeChange(Long id) {
        for (Gift gift : giftList) {
            gift.initLike();;
        }

        Gift gift = giftList.stream()
                .filter(filterGift -> filterGift.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("The giftId does not exist for the given Target."));

        gift.likeToGift();
        /*for (Coupon coupon : couponList) {
            coupon.initLike();
        }

        if(isGift) {
            Gift gift = giftList.stream()
                    .filter(filterGift -> filterGift.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("해당 Target에 없는 couponId 입니다."));

            gift.likeToGift();
        }else {
            Coupon coupon = couponList.stream()
                    .filter(filterCoupon -> filterCoupon.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("해당 Target에 없는 couponId 입니다."));

            coupon.likeToCoupon();
        }*/

    }
    public Target update(String message, String cardImageUrl) {
        if (message != null) {
            this.message = message;
        }
        if (cardImageUrl != null) {
            this.cardImageUrl = cardImageUrl;
        }
        return this;
    }


    public Gift getLikedGift() {
        return giftList.stream()
                .filter(gift -> gift.getIsLike())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("해당 Target에 없는 giftId 입니다."));
    }

}
