package study.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.restfulapi.entity.Diary;

import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    /**
     * 특정 멤버의 모든 일기를 가져온다.
     * @param memberId 멤버 ID
     * @return 일기 리스트
     */
    @Query("select d from Diary d where d.member.id = :memberId")
    List<Diary> findDiariesByMemberId(@Param("memberId") Long memberId);

}
