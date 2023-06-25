package com.teo15.picktimebe.target;

import com.teo15.picktimebe.config.S3Uploader;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.gift.GiftData;
import com.teo15.picktimebe.gift.GiftResponse;
import com.teo15.picktimebe.target.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TargetService {
    private final TargetRepository targetRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long createTarget(PostTargetRequest request, MultipartFile file) throws FileSystemException {
        String fileName = "";
        if(file != null){ // 파일 업로드한 경우에만
            try{// 파일 업로드
                fileName = s3Uploader.upload(file, "images"); // S3 버킷의 images 디렉토리 안에 저장됨
                log.info("fileName = {}", fileName);
                request.setCardImageUrl(fileName);
            }catch (IOException e){
                throw new FileSystemException("Failed to save the card image file.");
            }
        }

        Target target = request.toEntity();
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
                        .map(gift -> new GiftData(gift))
                        .collect(Collectors.toList()));
    }
}
