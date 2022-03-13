package study.restfulapi.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class EntityTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("엔티티 저장 확인")
    void testEntity() {
        Member memberA = new Member("memberA");
        Member memberB = new Member("memberB");
        em.persist(memberA);
        em.persist(memberB);

        Diary diary1 = new Diary("diary1", "day 1...", memberA);
        Diary diary2 = new Diary("diary2", "day 2...", memberA);

        Diary diary3 = new Diary("diary3", "day 3...", memberB);
        Diary diary4 = new Diary("diary4", "day 4...", memberB);

        em.persist(diary1);
        em.persist(diary2);
        em.persist(diary3);
        em.persist(diary4);

        em.flush();
        em.clear();

        List<Diary> diaries = em.createQuery("select d from Diary d", Diary.class)
                .getResultList();

        assertThat(4).isEqualTo(diaries.size());
    }

    @Test
    @DisplayName("엔티티 연관관계 확인")
    void mappingTest() {

        Member member = new Member("member");
        em.persist(member);

        Diary diary1 = new Diary("diary1", "day 1...", member);
        Diary diary2 = new Diary("diary2", "day 2...", member);

        em.persist(diary1);
        em.persist(diary2);

        em.flush();
        em.clear();

        Diary result1 = em.find(Diary.class, diary1.getId());
        Diary result2 = em.find(Diary.class, diary2.getId());

        assertThat(result1.getMember().getId()).isEqualTo(member.getId());
        assertThat(result2.getMember().getId()).isEqualTo(member.getId());
        assertThat(member.getDiaries().size()).isEqualTo(2);
    }
}