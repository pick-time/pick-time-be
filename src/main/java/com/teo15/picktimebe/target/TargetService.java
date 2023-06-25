package com.teo15.picktimebe.target;

import com.teo15.picktimebe.config.S3Uploader;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.Gift;
import com.teo15.picktimebe.target.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystemException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TargetService {
    private final TargetRepository targetRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public Long createTarget(PostTargetRequest request) throws FileSystemException {
        MultipartFile multipartFile = request.getCardImage();
        String fileName = "";
        if(multipartFile != null){ // 파일 업로드한 경우에만
            try{// 파일 업로드
                fileName = s3Uploader.upload(multipartFile, "images"); // S3 버킷의 images 디렉토리 안에 저장됨
                log.info("fileName = {}", fileName);
                request.setCardImageUrl(fileName);
            }catch (IOException e){
                throw new FileSystemException("카드 이미지 파일 저장에 실패하였습니다.");
            }
        }

        Target target = request.toEntity();
        Target saveTarget = targetRepository.save(target);

        return saveTarget.getId();
    }

    public GetTargetResponse selectTarget(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        return new GetTargetResponse(target.getProviderName(), target.getCardImageUrl(), target.getMessage());
    }

    @Transactional
    public Long likeGiftForTarget(Long targetId, Long giftId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        target.giftLikeChange(giftId);
        return target.getId();
    }

    public GetFinalTargetResponse getFinalGiftForTarget(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        Gift likedGift = target.getLikedGift();
        return new GetFinalTargetResponse(target.getConsumerName(),
                new GetFinalGiftResponse(likedGift.getGiftTitle(), likedGift.getGiftImageUrl()));
    }

    public GetTargetUserName getTargetUserName(Long targetId) {
        Target target = targetRepository.findById(targetId)
                .orElseThrow(() -> new ResourceNotFoundException("카드를 찾을 수 없습니다."));

        return new GetTargetUserName(target.getProviderName(), target.getConsumerName());
    }
}
