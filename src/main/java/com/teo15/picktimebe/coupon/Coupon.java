package com.teo15.picktimebe.coupon;

import com.teo15.picktimebe.target.Target;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String couponImageUrl;
    private String couponTitle;
    private String couponDescription;
    private Boolean isLike;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private Target target;

    public void likeToCoupon() {
        isLike = true;
    }

    public void initLike() {
        this.isLike = false;
    }
}