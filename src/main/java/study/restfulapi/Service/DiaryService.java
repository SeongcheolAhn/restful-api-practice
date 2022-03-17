package study.restfulapi.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.restfulapi.api.controller.dto.DiaryRequest;
import study.restfulapi.api.controller.dto.DiaryResponse;
import study.restfulapi.entity.Diary;
import study.restfulapi.entity.Member;
import study.restfulapi.repository.DiaryRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Member API의 경우 엔티티 <-> DTO 변환은 컨트롤러 계층에서 했다.
 * 이번 Diary API의 경우 엔티티 <-> DTO 변환을 서비스 계층에서 해볼 예정!!
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberService memberService;

    /**
     * 유저의 모든 일기 조회
     *
     * @param memberId 유저 ID
     * @return 일기 내용 리스트
     */
    public List<DiaryResponse> findDiaries(Long memberId) {
        return diaryRepository.findDiariesByMemberId(memberId)
                .stream()
                .map(DiaryResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * 일기 조회
     *
     * @param diaryId
     * @return
     */
    public DiaryResponse findOne(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("일기가 존재하지 않습니다."));

        return new DiaryResponse(diary);
    }

    /**
     * 일기 생성
     *
     * @param diaryRequest 일기 요청 DTO
     * @return 일기 응답 DTO
     * @throws Throwable
     */
    @Transactional
    public DiaryResponse create(DiaryRequest diaryRequest) throws Throwable {
        Member member = memberService.findOne(diaryRequest.getMemberId());
        Diary saveDiary = diaryRepository.save(new Diary(diaryRequest.getTitle(), diaryRequest.getContents(), member));
        return new DiaryResponse(saveDiary);
    }

    /**
     * 일기 수정
     *
     * @param diaryId 일기 ID
     * @param diaryRequest 일기 요청 DTO
     * @return 일기 응답 DTO
     */
    @Transactional
    public DiaryResponse update(Long diaryId, DiaryRequest diaryRequest) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("일기가 존재하지 않습니다."));

        diary.edit(diaryRequest.getTitle(), diaryRequest.getContents());
        return new DiaryResponse(diary);
    }
}
