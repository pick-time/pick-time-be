package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.gift.dto.GiftData;
import com.teo15.picktimebe.target.Target;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "gift")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String giftUrl;
    private String giftImageUrl;
    private String giftTitle;
    private String giftDescription;
    private Boolean isLike;

    @ManyToOne
    @JoinColumn(name = "target_id")
    private Target target;

    public void likeToGift() {
        this.isLike = true;
    }

    public void initLike() {
        this.isLike = false;
    }
}