package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.config.S3Uploader;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.dto.CouponResponse;
import com.teo15.picktimebe.gift.dto.PostCouponRequest;
import com.teo15.picktimebe.target.Target;
import com.teo15.picktimebe.target.TargetRepository;
import com.teo15.picktimebe.target.TargetService;
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
public class CouponService {
    private final GiftRepository giftRepository;
    private final TargetRepository targetRepository;
    private final TargetService targetService;
    private final S3Uploader s3Uploader;
    @Transactional
    public Long createCoupon(PostCouponRequest request, MultipartFile file) throws FileSystemException {
        String fileName = "";

        if(file != null){
            try {
                fileName = s3Uploader.upload(file, "images"); // S3 버킷의 images 디렉토리 안에 저장됨
                log.info("fileName = {}", fileName);
                request.setCouponImageUrl(fileName);
            } catch (IOException e){
                throw new FileSystemException("Failed to save the card image file.");
            }
        }

        Target target = targetService.getTargetEntity(request.targetId);

        Gift gift = request.toEntity();
        gift.setTarget(target);

        Gift newGift = giftRepository.save(gift);
        return newGift.getId();
    }
}
