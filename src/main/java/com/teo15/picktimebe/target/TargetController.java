package com.teo15.picktimebe.target;


import com.teo15.picktimebe.target.dto.GetFinalTargetResponse;
import com.teo15.picktimebe.target.dto.GetTargetResponse;
import com.teo15.picktimebe.target.dto.PostLikeGiftForTargetRequest;
import com.teo15.picktimebe.target.dto.PostTargetRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileSystemException;

@RestController
@RequestMapping("/target")
@RequiredArgsConstructor
public class TargetController {
    private final TargetService targetService;

    @PostMapping
    public ResponseEntity<Long> addTarget(@RequestPart(value="target") PostTargetRequest request) throws FileSystemException {
        Long targetId = targetService.createTarget(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(targetId);
    }

    @GetMapping("/{targetId}")
    public ResponseEntity<GetTargetResponse> getTarget(@PathVariable Long targetId){
        return ResponseEntity.ok(targetService.selectTarget(targetId));
    }

    @PostMapping("/target/{targetId}")
    public ResponseEntity<Long> likeGiftForTarget(@PathVariable("targetId") Long targetId,
                                               @RequestParam Long giftId) {

        return ResponseEntity.ok(targetService.likeGiftForTarget(targetId, giftId));
    }

    @GetMapping("/{targetId}/final")
    public ResponseEntity<GetFinalTargetResponse> getFinalGiftForTarget(@PathVariable("targetId") Long targetId) {
        return ResponseEntity.ok(targetService.getFinalGiftForTarget(targetId));
    }
}
