package study.restfulapi.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.restfulapi.api.controller.dto.MemberDto;
import study.restfulapi.repository.MemberRepository;
import study.restfulapi.entity.Member;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 멤버 id 값을 이용해 특정 멤버를 찾는다.
     * 없다면 IllegalArgumentException
     */
    public Member findOne(Long id) throws Throwable {
        Optional<Member> result = memberRepository.findById(id);
        result.orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        return result.get();
    }

    /**
     * 전달받은 멤버를 추가한다.
     * @param member
     * @return
     */
    @Transactional
    public Long join(Member member) {
        log.info("join()");
        Member result = memberRepository.save(member);
        return result.getId();
    }

    @Transactional
    public Long edit(Member member, Long id) throws Throwable {
        Member editMember = findOne(id);
        editMember.changeMemberName(member.getName());
        log.info("memberName = {}", editMember.getName());
        return editMember.getId();
    }

    @Transactional
    public Long delete(Long id) throws Throwable {
        Member deleteMember = findOne(id);
        Long deleteId = deleteMember.getId();
        memberRepository.delete(deleteMember);
        return deleteId;
    }
}
