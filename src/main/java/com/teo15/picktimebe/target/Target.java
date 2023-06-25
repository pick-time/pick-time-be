package com.teo15.picktimebe.target;

import com.teo15.picktimebe.coupon.Coupon;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.target.dto.GetFinalGiftResponse;
import com.teo15.picktimebe.target.dto.GetFinalTargetResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "target")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private Boolean isGift;

    @OneToMany(mappedBy = "target", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Gift> giftList;

    /*@OneToMany(mappedBy = "target", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Coupon> couponList;*/

    public void giftLikeChange(Long id) {
        for (Gift gift : giftList) {
            gift.initLike();;
        }

        Gift gift = giftList.stream()
                .filter(filterGift -> filterGift.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("해당 Target에 없는 couponId 입니다."));

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

    public Gift getLikedGift() {
        return giftList.stream()
                .filter(gift -> gift.getIsLike())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("해당 Target에 없는 giftId 입니다."));
    }
}
