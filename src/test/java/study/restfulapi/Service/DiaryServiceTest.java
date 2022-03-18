package study.restfulapi.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import study.restfulapi.api.controller.dto.DiaryResponse;
import study.restfulapi.entity.Diary;
import study.restfulapi.entity.Member;
import study.restfulapi.repository.DiaryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({DiaryService.class, DiaryRepository.class, MemberService.class})
class DiaryServiceTest {

    @MockBean
    DiaryRepository diaryRepository;
    @MockBean
    MemberService memberService;

    @Autowired
    DiaryService diaryService;


    @Test
    @DisplayName("유저의 모든 일기 조회")
    void getDiaries() {

        ArrayList<Diary> response = new ArrayList<>();
        response.add(new Diary("diary 1", "diary 1 ...", new Member("memberA")));
        response.add(new Diary("diary 2", "diary 2 ...", new Member("memberA")));
        response.add(new Diary("diary 3", "diary 3 ...", new Member("memberA")));

        Mockito.when(diaryRepository.findDiariesByMemberId(1L))
                .thenReturn(response);

        List<DiaryResponse> diaries = diaryService.findDiaries(1L);

        assertThat(diaries.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("일기 조회")
    void getDiary() {

        Mockito.when(diaryRepository.findById(1L))
                .thenReturn(Optional.of(new Diary("diary 1", "diary 1 ...", new Member("memberA"))));

        DiaryResponse diaryResponse = diaryService.findOne(1L);

        assertThat(diaryResponse.getTitle()).isEqualTo("diary 1");
    }

    @Test
    @DisplayName("존재하지 않는 일기 조회")
    void getNotExistDiary() {

        Mockito.when(diaryRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> diaryService.findOne(10L));
    }
}