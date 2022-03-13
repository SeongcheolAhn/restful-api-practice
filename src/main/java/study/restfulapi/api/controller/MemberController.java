package study.restfulapi.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.restfulapi.Service.MemberService;
import study.restfulapi.api.Result;
import study.restfulapi.api.controller.dto.MemberDto;
import study.restfulapi.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 전체 멤버 조회
     */
    @GetMapping("/members")
    public Result<List<MemberDto>> getMembers() {
        return new Result<List<MemberDto>>(
                memberService.findAll().stream()
                        .map(MemberDto::new)
                        .collect(Collectors.toList()));
    }

    /**
     * 새로운 멤버 추가
     * @param memberDto
     */
    @PostMapping("/members")
    public Result<Long> addMember(@RequestBody MemberDto memberDto) {
        Long memberId = memberService.join(new Member(memberDto.getName()));
        return new Result<>(memberId);
    }

    /**
     * 특정 멤버 조회
     * @param id 찾고 싶은 멤버 ID
     */
    @GetMapping("/members/{id}")
    public Result<MemberDto> getMember(@PathVariable Long id) throws Throwable {
        Member member = memberService.findOne(id);
        return new Result<>(new MemberDto(member));
    }


    /**
     * 특정 멤버 이름 수정
     * @param id
     */
    @PutMapping("/members/{id}")
    public void EditMember(@PathVariable Long id,
                           @RequestBody MemberDto memberDto) throws Throwable {
        memberService.edit(new Member(memberDto.getName()), id);
    }

    /**
     * 특정 멤버 삭제
     * @param id
     */
    @DeleteMapping("/members/{id}")
    public void DeleteMember(@PathVariable Long id) throws Throwable {
        memberService.delete(id);
    }
}
