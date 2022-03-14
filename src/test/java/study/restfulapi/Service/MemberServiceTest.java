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
import study.restfulapi.entity.Member;
import study.restfulapi.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import({MemberService.class, MemberRepository.class})
class MemberServiceTest {

    @MockBean
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("모든 회원 조회 테스트")
    void getMembers() {

        List<Member> members = new ArrayList<>();
        members.add(new Member("memberA"));
        members.add(new Member("memberB"));
        members.add(new Member("memberC"));

        Mockito.when(memberRepository.findAll())
                .thenReturn(members);

        List<Member> result = memberService.findAll();
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void getMember() throws Throwable {

        Mockito.when(memberRepository.findById(1L))
                .thenReturn(Optional.of(new Member("memberA")));

        Member result = memberService.findOne(1L);
        assertThat(result.getName()).isEqualTo("memberA");
    }

    @Test
    @DisplayName("존재하지 않는 회원 조회 테스트")
    void getNoExistMember() throws Throwable {

        Mockito.when(memberRepository.findById(1L))
                .thenReturn(Optional.ofNullable(null));

        assertThrows(IllegalArgumentException.class, () -> memberService.findOne(1L));
    }
}