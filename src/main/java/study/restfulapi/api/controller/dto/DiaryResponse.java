package study.restfulapi.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import study.restfulapi.entity.Diary;

@AllArgsConstructor
@Getter @Setter
public class DiaryResponse {

    private String memberName;
    private String title;
    private String contents;

    public DiaryResponse(Diary diary) {
        memberName = diary.getMember().getName();
        title = diary.getTitle();
        contents = diary.getContents();
    }
}
