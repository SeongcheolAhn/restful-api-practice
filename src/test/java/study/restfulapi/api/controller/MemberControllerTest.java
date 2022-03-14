package study.restfulapi.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import study.restfulapi.Service.MemberService;
import study.restfulapi.entity.Member;
import study.restfulapi.exhandler.advice.ExControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("모든 회원 조회 테스트")
    void getMembers() throws Throwable {

        List<Member> members = new ArrayList<>();
        members.add(new Member("memberA"));
        members.add(new Member("memberB"));
        members.add(new Member("memberC"));

        given(memberService.findAll()).willReturn(members);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/members")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value("memberA"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].name").value("memberB"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[2].name").value("memberC"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[3]").doesNotExist())
                .andDo(MockMvcResultHandlers.print());

        verify(memberService).findAll();
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void getMember() throws Throwable {

        given(memberService.findOne(123L)).willReturn(
                new Member("memberA")
        );

        Long memberId = 123L;

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/members/" + memberId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").exists())
                .andDo(MockMvcResultHandlers.print());

        verify(memberService).findOne(123L);
    }
}