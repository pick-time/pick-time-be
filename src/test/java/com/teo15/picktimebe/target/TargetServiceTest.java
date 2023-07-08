package com.teo15.picktimebe.target;

import com.teo15.picktimebe.DatabaseCleanup;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.GiftRepository;
import com.teo15.picktimebe.gift.GiftType;
import com.teo15.picktimebe.target.dto.*;
import io.restassured.RestAssured;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
class TargetServiceTest {
    @Autowired
    private TargetService targetService;
    @Autowired
    private TargetRepository targetRepository;
    @Autowired
    private GiftRepository giftRepository;
    @Autowired
    private DatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        databaseCleanup.afterPropertiesSet();
        databaseCleanup.execute();
    }

    final String providerName = "Provider1";
    final String consumerName = "Consumer1";

    @Test
    void 선물고르러가기_성공() {
        final String providerName = "Provider1";
        final String consumerName = "Consumer1";

        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);

        assertThat(targetId).isEqualTo(1L);

        Target target = targetRepository.findById(targetId).get();
        assertThat(target.getConsumerName()).isEqualTo(consumerName);
        assertThat(target.getProviderName()).isEqualTo(providerName);
    }

    private CreateTargetRequest getRequest(String providerName, String consumerName) {
        return new CreateTargetRequest(providerName, consumerName);
    }

    @Test
    void 타겟업데이트_성공() throws FileSystemException {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);

        final String cardImageUrl = "테스트 카드 URL";
        final String cardMessage = "테스트 카드 메시지";
        PostTargetRequest postTargetRequest = new PostTargetRequest(cardImageUrl, cardMessage, Optional.of(new ArrayList<Long>()), Optional.of(new ArrayList<Long>()));

        Long updateId = targetService.updateTarget(targetId, postTargetRequest, null);
        assertThat(targetId).isEqualTo(updateId);

        Target target = targetRepository.findById(updateId).get();
        assertThat(target.getCardImageUrl()).isEqualTo(cardImageUrl);
        assertThat(target.getMessage()).isEqualTo(cardMessage);
    }

    @Test
    void 타겟업데이트실패() throws FileSystemException {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);

        final String cardImageUrl = "테스트 카드 URL";
        final String cardMessage = "테스트 카드 메시지";
        ArrayList<Long> longs = new ArrayList<>();
        longs.add(1L);

        Optional<List<Long>> giftlist = Optional.of(longs);
        PostTargetRequest postTargetRequest = new PostTargetRequest(cardImageUrl, cardMessage, giftlist, null);

        assertThatThrownBy(() -> targetService.updateTarget(targetId, postTargetRequest, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void 타겟조회_성공() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);

        GetTargetResponse response = targetService.selectTarget(targetId);
        assertThat(response.getProviderName()).isEqualTo(providerName);
    }
    @Test
    void 타겟조회_실패() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        targetService.createTarget(request);

        assertThatThrownBy(() -> {
            long unknownId = 2L;
            targetService.selectTarget(unknownId);
        })
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 선물선택_성공() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);
        Target targetEntity = targetService.getTargetEntity(targetId);
        Gift gift = giftRepository.save(getGiftEntity(targetEntity));

        Long id = targetService.likeGiftForTarget(targetId, gift.getId());
        List<Gift> GiftList = giftRepository.findByTargetId(id);
        Gift likedGift = GiftList.get(0);

        assertThat(likedGift.getId()).isEqualTo(gift.getId());
        assertThat(likedGift.getIsLike()).isEqualTo(true);
    }

    @Test
    void 선물선택_실패() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);
        Target targetEntity = targetService.getTargetEntity(targetId);
        giftRepository.save(getGiftEntity(targetEntity));

        assertThatThrownBy(() -> {
            long unknownId = 2L;
            targetService.likeGiftForTarget(targetId, unknownId);
        })
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private Gift getGiftEntity(Target target) {
        return new Gift(GiftType.COUPON, "giftURL", "imageURL", "선물1", "선물쿠폰", target);
    }

    @Test
    void 최종결정된선물조회_성공() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);
        Target targetEntity = targetService.getTargetEntity(targetId);
        Gift gift = giftRepository.save(getGiftEntity(targetEntity));
        targetService.likeGiftForTarget(targetId, gift.getId());

        GetFinalTargetResponse response = targetService.getFinalGiftForTarget(targetId);
        assertThat(response.getFinalGift().getGiftTitle()).isEqualTo(gift.getGiftTitle());
        assertThat(response.getFinalGift().getGiftImageUrl()).isEqualTo(gift.getGiftImageUrl());
    }

    @Test
    void 최종결정된선물조회_실패() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);
        Target targetEntity = targetService.getTargetEntity(targetId);
        giftRepository.save(getGiftEntity(targetEntity));

        assertThatThrownBy(() -> {
            targetService.getFinalGiftForTarget(targetId);
        })
                .isInstanceOf(ResourceNotFoundException.class);
    }
}