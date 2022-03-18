package study.restfulapi.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import study.restfulapi.Service.DiaryService;
import study.restfulapi.api.Result;
import study.restfulapi.api.controller.dto.DiaryRequest;
import study.restfulapi.api.controller.dto.DiaryResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(DiaryController.class)
class DiaryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DiaryService diaryService;

    @Test
    @DisplayName("일기 리스트 조회")
    void getDiaries() throws Exception {

        List<DiaryResponse> diaryResponses = new ArrayList<>();
        diaryResponses.add(new DiaryResponse("memberA", "diary 1", "diary 1 ..."));
        diaryResponses.add(new DiaryResponse("memberA", "diary 2", "diary 2 ..."));
        diaryResponses.add(new DiaryResponse("memberA", "diary 3", "diary 3 ..."));

        Long memberId = 1L;

        BDDMockito.given(diaryService.findDiaries(memberId)).willReturn(diaryResponses);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/diaries")
                                .param("memberId", "1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].memberName").value("memberA"));

        BDDMockito.verify(diaryService).findDiaries(memberId);
    }

    @Test
    @DisplayName("일기 조회")
    void getDiary() throws Exception {

        BDDMockito.given(diaryService.findOne(1L))
                .willReturn(new DiaryResponse("memberA", "diary 1", "diary 1 ..."));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/diaries/" + "1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberName").value("memberA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("diary 1"))
                .andDo(print());

        BDDMockito.verify(diaryService).findOne(1L);
    }

    @Test
    @DisplayName("일기 저장")
    void saveDiary() throws Throwable {

        DiaryResponse response = new DiaryResponse("memberA", "diary 1", "diary 1 ...");
        BDDMockito.given(diaryService.create(any()))
                .willReturn(response);

        DiaryRequest request = new DiaryRequest(1L, "diary 1", "diary 1 ...");
        String content = new ObjectMapper().writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/diaries")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberName").exists())
                .andDo(print());

        BDDMockito.verify(diaryService).create(any());

    }

    @Test
    @DisplayName("일기 수정")
    void editDiary() throws Throwable {

        BDDMockito.given(diaryService.update(anyLong(), any()))
                .willReturn(new DiaryResponse("memberA", "diary 1 edit", "diary 1 edit ..."));

        DiaryRequest Request = new DiaryRequest(1L, "diary 1", "diary 1 ...");
        String content = new ObjectMapper().writeValueAsString(Request);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/diaries/" + "4")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.memberName").exists())
                .andDo(print());

        BDDMockito.verify(diaryService).update(any(), any());
    }

}