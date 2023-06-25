package com.teo15.picktimebe.gift;

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
    private String cardId;
    private String giftUrl;
    private String giftImageUrl;
    private String giftTitle;
    private String giftDescription;
    private boolean isLike;
}
