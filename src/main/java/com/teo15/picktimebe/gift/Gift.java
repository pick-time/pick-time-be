package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.target.Target;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    
    public void setTarget(Target target) {
        this.target = target;
        target.getGiftList().add(this);
    }

    public Gift(String giftUrl, String giftImageUrl, String giftTitle, String giftDescription) {
        this.giftUrl = giftUrl;
        this.giftImageUrl = giftImageUrl;
        this.giftTitle = giftTitle;
        this.giftDescription = giftDescription;
        this.isLike = false;
    }


    public void likeToGift() {
        this.isLike = true;
    }

    public void initLike() {
        this.isLike = false;
    }
}
