package study.restfulapi.api.controller.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class DiaryRequest {

    private Long memberId;
    private String title;
    private String contents;
}
