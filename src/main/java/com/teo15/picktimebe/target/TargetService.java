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
import java.util.List;
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

    // target이 2개가 생기는 버그 존재
    @Transactional
    public Long updateTarget(Long targetId, PostTargetRequest request, MultipartFile file) throws FileSystemException {
        String fileName = "";

        if(file != null){
            try {
                fileName = s3Uploader.upload(file, "images"); // S3 버킷의 images 디렉토리 안에 저장됨
                log.info("fileName = {}", fileName);
                request.setCardImageUrl(fileName);
            } catch (IOException e){
                throw new FileSystemException("Failed to save the card image file.");
            }
        }

        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid target ID: " + targetId));

        target.update(request.getCardMessage(), request.getCardImageUrl());

        List<Long> giftList = new ArrayList<>(request.getGiftList());
        giftList.addAll(request.getCouponList());

        if (!giftList.isEmpty()){
            List<Gift> gifts = giftList.stream()
                    .map(giftId -> {
                        Gift gift = giftRepository.findById(giftId)
                                .orElseThrow(() -> new ResourceNotFoundException("Invalid gift ID: " + giftId));
                        gift.setTarget(target);
                        target.addGift(gift);
                        return gift;
                    })
                    .collect(Collectors.toList());
        }

        Target saveTarget = targetRepository.save(target);

        return saveTarget.getId();
    }


    public GetTargetResponse selectTarget(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Target."));

        return new GetTargetResponse(target.getProviderName(), target.getCardImageUrl(), target.getMessage());
    }

    @Transactional
    public Long likeGiftForTarget(Long targetId, Long giftId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Target."));

        target.giftLikeChange(giftId);
        return target.getId();
    }

    public GetFinalTargetResponse getFinalGiftForTarget(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Target."));

        Gift likedGift = target.getLikedGift();
        return new GetFinalTargetResponse(target.getConsumerName(),
                new GetFinalGiftResponse(likedGift.getGiftTitle(), likedGift.getGiftImageUrl()));
    }

    public GetTargetUserName getTargetUserName(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Target."));

        return new GetTargetUserName(target.getProviderName(), target.getConsumerName());
    }

    public GiftResponse selectByTargetIdGitList(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Target."));

        return new GiftResponse(target.getGiftList().size(), target.getProviderName(), target.getConsumerName(),
                target.getGiftList()
                        .stream()
                        .filter(gift -> gift.getGiftType().equals(GiftType.PRODUCT))
                        .map(gift -> new GiftData(gift))
                        .collect(Collectors.toList()),
                target.getGiftList()
                        .stream()
                        .filter(gift -> gift.getGiftType().equals(GiftType.COUPON))
                        .map(gift -> new CouponData(gift))
                        .collect(Collectors.toList()));

    }
}
