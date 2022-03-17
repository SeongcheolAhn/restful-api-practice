package study.restfulapi.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.restfulapi.api.controller.dto.DiaryRequest;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id @GeneratedValue
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;
    private String contents;

    public Diary(String title, String contents, Member member) {
        this.title = title;
        this.contents = contents;
        this.member = member;
        member.getDiaries().add(this);
    }

    public void edit(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
