package com.teo15.picktimebe.target;

import com.teo15.picktimebe.config.S3Uploader;
import com.teo15.picktimebe.gift.GiftRepository;
import com.teo15.picktimebe.target.dto.CreateTargetRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TargetServiceTest {
    @Autowired
    private TargetService targetService;
    @Autowired
    private TargetRepository targetRepository;



    @Test
    @DisplayName("선물 고르러 가기 성공")
    void createTarget() {
        final String providerName = "Provider1";
        final String consumerName = "Consumer1";

        CreateTargetRequest request = new CreateTargetRequest(providerName, consumerName);
        Long targetId = targetService.createTarget(request);

        assertThat(targetId).isEqualTo(1L);

        Target target = targetRepository.findById(targetId).get();
        assertThat(target.getConsumerName()).isEqualTo(consumerName);
        assertThat(target.getProviderName()).isEqualTo(providerName);
    }

    @Test
    void 타겟업데이트성공() {

    }

    @Test
    void 타겟업데이트실패() {

    }

    @Test
    void selectTarget() {
    }

    @Test
    void getTargetEntity() {
    }

    @Test
    void likeGiftForTarget() {
    }

    @Test
    void getFinalGiftForTarget() {
    }

    @Test
    void getTargetUserName() {
    }

    @Test
    void selectByTargetIdGiftList() {
    }
}