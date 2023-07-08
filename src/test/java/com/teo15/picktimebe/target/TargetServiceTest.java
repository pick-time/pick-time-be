package com.teo15.picktimebe.target;

import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.GiftRepository;
import com.teo15.picktimebe.gift.GiftType;
import com.teo15.picktimebe.target.dto.*;
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
        PostTargetRequest postTargetRequest = new PostTargetRequest(cardImageUrl, cardMessage, null, null);

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
                .isInstanceOf(ResourceNotFoundException.class);
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
        Gift gift = giftRepository.save(getGiftEntity());

        Long id = targetService.likeGiftForTarget(targetId, gift.getId());
        Target target = targetRepository.findById(id).get();
        Gift likedGift = target.getLikedGift();

        assertThat(likedGift.getId()).isEqualTo(gift.getId());
        assertThat(likedGift.getIsLike()).isEqualTo(true);
    }

    @Test
    void 선물선택_실패() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);
        giftRepository.save(getGiftEntity());

        assertThatThrownBy(() -> {
            long unknownId = 2L;
            targetService.likeGiftForTarget(targetId, unknownId);
        })
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private Gift getGiftEntity() {
        return new Gift(GiftType.COUPON, "giftURL", "imageURL", "선물1", "선물쿠폰", null);
    }

    @Test
    void 최종결정된선물조회_성공() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);
        Gift gift = giftRepository.save(getGiftEntity());
        targetService.likeGiftForTarget(targetId, gift.getId());

        GetFinalTargetResponse response = targetService.getFinalGiftForTarget(targetId);
        assertThat(response.getFinalGift().getGiftTitle()).isEqualTo(gift.getGiftTitle());
        assertThat(response.getFinalGift().getGiftImageUrl()).isEqualTo(gift.getGiftImageUrl());
    }

    @Test
    void 최종결정된선물조회_실패() {
        CreateTargetRequest request = getRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);
        giftRepository.save(getGiftEntity());

        assertThatThrownBy(() -> {
            targetService.getFinalGiftForTarget(targetId);
        })
                .isInstanceOf(ResourceNotFoundException.class);
    }
}