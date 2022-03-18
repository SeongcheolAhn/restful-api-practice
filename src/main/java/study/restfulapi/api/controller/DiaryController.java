package study.restfulapi.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import study.restfulapi.Service.DiaryService;
import study.restfulapi.api.Result;
import study.restfulapi.api.controller.dto.DiaryRequest;
import study.restfulapi.api.controller.dto.DiaryResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    /**
     * 멤버의 모든 일기 조회
     *
     * @param memberId
     */
    @GetMapping("/diaries")
    public Result<List<DiaryResponse>> getDiaries(@RequestParam Long memberId) {
        return new Result<>(diaryService.findDiaries(memberId));
    }

    /**
     * 일기 조회
     *
     * @param diaryId
     * @return
     */
    @GetMapping("/diaries/{diaryId}")
    public Result<DiaryResponse> getDiary(@PathVariable Long diaryId) {
        return new Result<>(diaryService.findOne(diaryId));
    }

    /**
     * 일기 저장
     *
     * @param diaryRequest 일기 요청 DTO
     * @return
     * @throws Throwable
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/diaries")
    public Result<DiaryResponse> createDiary(@RequestBody DiaryRequest diaryRequest) throws Throwable {
        return new Result<>(diaryService.create(diaryRequest));
    }

    /**
     * 일기 수정
     *
     * @param diaryId 일기 ID
     * @param diaryRequest 일기 요청 DTO
     * @return 수정된 일기 응답 DTO
     */
    @PatchMapping("/diaries/{diaryId}")
    public Result<DiaryResponse> updateDiary(@PathVariable Long diaryId,
                                             @RequestBody DiaryRequest diaryRequest) {
        DiaryResponse diaryResponse = diaryService.update(diaryId, diaryRequest);
        return new Result<>(diaryResponse);

    }
}
