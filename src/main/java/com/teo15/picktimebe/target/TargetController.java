package com.teo15.picktimebe.target;


import com.teo15.picktimebe.gift.dto.GiftResponse;
import com.teo15.picktimebe.target.dto.CreateTargetRequest;
import com.teo15.picktimebe.target.dto.GetFinalTargetResponse;
import com.teo15.picktimebe.target.dto.GetTargetResponse;
import com.teo15.picktimebe.target.dto.PostTargetRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystemException;

@RestController
@RequestMapping("/target")
@CrossOrigin(originPatterns = "https://pick-time.vercel.app")
@ApiResponses({
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 400, message = "Bad Request"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@Api(tags = {"타겟 관리 API"})
@RequiredArgsConstructor
public class TargetController {
    private final TargetService targetService;

    /**
     * Target 업데이트 API
     * 최종 생성 페이지에서 완료하기 버튼 클릭시 호출
     * req : card 관련 info, gift & coupon list
     * res : targetId
     */
    @PostMapping("/{targetId}")
    public ResponseEntity<Long> updateTarget(@PathVariable Long targetId, @ModelAttribute PostTargetRequest target, @RequestParam(value ="file", required=false) MultipartFile file) throws FileSystemException {
        return ResponseEntity.ok(targetService.updateTarget(targetId ,target, file));
    }

    /**
     * TargetId 생성 API
     * 주는 사람 메인 페이지 - “선물 고르러 가기” 클릭시 호출
     * req : providerName, consumerName
     * res : targetId
     */
    @PostMapping
    @ApiOperation(value = "타겟 생성", notes = "타겟을 생성하고, 생성된 ID를 응답", response = Long.class)
    public ResponseEntity<Long> createTarget(@RequestBody CreateTargetRequest request) {
        Long targetId = targetService.createTarget(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(targetId);
    }

    /**
     * Target 최종 링크 조회 API
     * 받는 사람의 랜딩페이지
     * req : targetId
     * res : providerName, coupon 정보
     */
    @GetMapping("/{targetId}")
    @ApiOperation(value = "타겟 조회", notes = "Target 최종 링크 조회 API, 받는 사람의 랜딩페이지", response = GetTargetResponse.class)
    public ResponseEntity<GetTargetResponse> getTarget(@PathVariable Long targetId){
        return ResponseEntity.ok(targetService.selectTarget(targetId));
    }

    /**
     * Target 최종 링크 생성 API
     * 받는 사람 최종 상품 1개 선택 후 이걸로 정했어요! 버튼 클릭 시 요청
     */
    @GetMapping("/{targetId}/pick")
    @ApiOperation(value = "타겟 최종 링크 생성", notes = "받는 사람 최종 상품 1개 선택 후 이걸로 정했어요! 버튼 클릭 시 요청", response = Long.class)
    public ResponseEntity<Long> likeGiftForTarget(@PathVariable("targetId") Long targetId,
                                               @RequestParam Long giftId) {

        return ResponseEntity.ok(targetService.likeGiftForTarget(targetId, giftId));
    }

    /**
     * Target 최종 결정된 선물 조회 API
     * 받는 사람이 결정한 한 개의 선물을 볼 수 있는, 동시에 주는 사람도 받는 사람이 결정한 선물을 확인할 때 요청되는 api ( isLiked )
     */
    @ApiOperation(value = "타겟 최종 결정된 선물 조회", notes = "받는 사람이 결정한 한 개의 선물을 볼 수 있는, 동시에 주는 사람도 받는 사람이 결정한 선물을 확인할 때 요청되는 api ( isLiked )", response = GetFinalTargetResponse.class)
    @GetMapping("/{targetId}/final")
    public ResponseEntity<GetFinalTargetResponse> getFinalGiftForTarget(@PathVariable("targetId") Long targetId) {
        return ResponseEntity.ok(targetService.getFinalGiftForTarget(targetId));
    }


}
