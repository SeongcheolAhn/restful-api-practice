package study.restfulapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.restfulapi.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
