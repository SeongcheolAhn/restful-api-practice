package study.restfulapi;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.restfulapi.entity.Diary;
import study.restfulapi.entity.Member;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService {

        private final EntityManager em;

        public void init() {
            Member memberA = createMember("memberA");
            Member memberB = createMember("memberB");
            Member memberC = createMember("memberC");
            em.persist(memberA);
            em.persist(memberB);
            em.persist(memberC);

            Diary diary1 = createDiary("diary1", "day 1 ...", memberA);
            Diary diary2 = createDiary("diary2", "day 2 ...", memberA);
            Diary diary3 = createDiary("diary3", "day 3 ...", memberB);
            Diary diary4 = createDiary("diary4", "day 4 ...", memberC);
            em.persist(diary1);
            em.persist(diary2);
            em.persist(diary3);
            em.persist(diary4);
        }

        private Member createMember(String name) {
            return new Member(name);
        }

        private Diary createDiary(String title, String contents, Member member) {
            return new Diary(title, contents, member);
        }
    }
}
