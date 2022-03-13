package study.restfulapi.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import study.restfulapi.entity.Member;

@AllArgsConstructor
@Getter @Setter
public class MemberDto {
    private String name;

    public MemberDto() {
    }

    public MemberDto(Member member) {
        this.name = member.getName();
    }
}
