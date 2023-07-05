package com.teo15.picktimebe.gift;

import com.teo15.picktimebe.config.S3Uploader;
import com.teo15.picktimebe.exception.ResourceNotFoundException;
import com.teo15.picktimebe.gift.dto.GiftData;
import com.teo15.picktimebe.gift.dto.GiftResponse;
import com.teo15.picktimebe.gift.dto.PostGiftRequest;
import com.teo15.picktimebe.gift.dto.UpdateGiftInfoRequest;
import com.teo15.picktimebe.target.dto.GetTargetUserName;
import com.teo15.picktimebe.target.TargetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.Document;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftService {
    private final GiftRepository giftRepository;
    private final TargetService targetService;
    private final S3Uploader s3Uploader;

    public GiftResponse selectByTargetIdGitList(Long targetId) {
        return targetService.selectByTargetIdGitList(targetId);
    }

    public GiftResponse updateAndgetList(Long giftId, UpdateGiftInfoRequest request, MultipartFile file) throws FileSystemException {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to find the Gift."));

        String fileName = "";
        if(file != null){
            try {
                fileName = s3Uploader.upload(file, "images");
                request.setGiftImage(fileName);
            } catch (IOException e){
                throw new FileSystemException("Failed to save the card image file.");
            }
        }
        Gift updatedGift = request.toEntity(gift);
        giftRepository.save(updatedGift);

        // gift id type이 coupon이면 쿠폰만 조회후 반환 필요한지 check *****
        return targetService.selectByTargetIdGitList(updatedGift.getTarget().getId());
    }

    public GiftResponse createAndgetList(Long targetId, PostGiftRequest request) {

        // https://github.com/siyoon210/ogparser4j/blob/develop/src/main/java/com/github/siyoon210/ogparser4j/htmlparser/OgMetaElement.java
        try {
        } catch (Exception e) {
//            logger.error("Open Graph Error :" , e.getMessage());
        }
        return new GiftResponse();
    }
}
