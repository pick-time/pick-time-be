package com.teo15.picktimebe.target;

import com.teo15.picktimebe.config.S3Uploader;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.GiftType;
import com.teo15.picktimebe.gift.dto.CouponData;
import com.teo15.picktimebe.gift.dto.GiftData;
import com.teo15.picktimebe.gift.GiftRepository;
import com.teo15.picktimebe.gift.dto.GiftResponse;
import com.teo15.picktimebe.target.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TargetService {
    private final TargetRepository targetRepository;
    private final GiftRepository giftRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long createTarget(CreateTargetRequest request){
        Target target = request.toEntity();
        Target newTarget = targetRepository.save(target);
        return newTarget.getId();
    }

    @Transactional
    public Long updateTarget(Long targetId, PostTargetRequest request, MultipartFile file) throws FileSystemException {
        if (file != null) {
            try {
                String fileName = s3Uploader.upload(file, "images");
                log.info("fileName = {}", fileName);
                request.setCardImageUrl(fileName);
            } catch (IOException e) {
                throw new FileSystemException("Failed to save the card image file.");
            }
        }

        Target target = getTargetEntity(targetId);
        target.update(request.getCardMessage(), request.getCardImageUrl());
        target.resetGifts();

        /* 아래코드는 타겟 업데이트시 전달받은 coupon, gift list를 추가하는 형태라 아예 업데이트 치는 걸로 수정하고자 합니다.
        List<Long> giftList = new ArrayList<>();
        request.getGiftList().ifPresent(giftList::addAll);
        request.getCouponList().ifPresent(giftList::addAll);

        if (!giftList.isEmpty()) {
            List<Gift> gifts = giftList.stream()
                    .map(giftId -> {
                        Gift gift = giftRepository.findById(giftId)
                                .orElseThrow(() -> new ResourceNotFoundException("Invalid gift ID: " + giftId));
                        target.addGift(gift);
                        return gift;
                    })
                    .collect(Collectors.toList());
        }

        Target saveTarget = targetRepository.save(target);

        return saveTarget.getId();
        */
        List<Long> giftList = request.getGiftList().orElse(Collections.emptyList());
        List<Long> couponList = request.getCouponList().orElse(Collections.emptyList());

        List<Long> combinedList = new ArrayList<>(giftList);
        combinedList.addAll(couponList);

        List<Gift> gifts = new ArrayList<>();
        for (Long giftId : combinedList) {
            Gift gift = giftRepository.findById(giftId)
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid gift ID: " + giftId));
            target.addGift(gift);
            gifts.add(gift);
        }

        Target saveTarget = targetRepository.save(target);
        return saveTarget.getId();
    }


    public GetTargetResponse selectTarget(Long targetId) {
        Target target = getTargetEntity(targetId);

        return new GetTargetResponse(target.getProviderName(), target.getCardImageUrl(), target.getMessage());
    }
    public Target getTargetEntity(Long targetId){
        return targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid target ID: " + targetId));
    }

    @Transactional
    public Long likeGiftForTarget(Long targetId, Long giftId) {
        Target target = getTargetEntity(targetId);

        target.giftLikeChange(giftId);
        return target.getId();
    }

    public GetFinalTargetResponse getFinalGiftForTarget(Long targetId) {
        Target target = getTargetEntity(targetId);

        Gift likedGift = target.getLikedGift();
        return new GetFinalTargetResponse(target.getConsumerName(),
                new GetFinalGiftResponse(likedGift.getGiftTitle(), likedGift.getGiftImageUrl(), likedGift.getGiftUrl()));
    }

    public GetTargetUserName getTargetUserName(Long targetId) {
        Target target = getTargetEntity(targetId);
        return new GetTargetUserName(target.getProviderName(), target.getConsumerName());
    }

    public GiftResponse selectByTargetIdGiftList(Long targetId, GiftType giftType) {
        Target target = getTargetEntity(targetId);
        List<GiftData> products = target.getGiftList()
                .stream()
                .filter(gift -> gift.getGiftType().equals(GiftType.PRODUCT))
                .map(gift -> new GiftData(gift))
                .distinct()
                .collect(Collectors.toList());

        List<CouponData> coupons = target.getGiftList()
                .stream()
                .filter(gift -> gift.getGiftType().equals(GiftType.COUPON))
                .map(gift -> new CouponData(gift))
                .distinct()
                .collect(Collectors.toList());

        switch (giftType) {
            case PRODUCT:
                return new GiftResponse(products.size(), target.getProviderName(), target.getConsumerName(), products, Collections.emptyList());
            case COUPON:
                return new GiftResponse(coupons.size(), target.getProviderName(), target.getConsumerName(), Collections.emptyList(), coupons);
            default:
                return new GiftResponse(target.getGiftList().size(), target.getProviderName(), target.getConsumerName(), products, coupons);
        }
    }



}
