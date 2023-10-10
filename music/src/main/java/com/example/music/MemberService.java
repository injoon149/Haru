package com.example.music;

import com.example.music.config.exception.BaseException;
import com.example.music.config.exception.errorcode.MemberErrorCode;
import com.example.music.domain.Member;
import com.example.music.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional //변경
    public void join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
    }
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers =
                memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new BaseException(MemberErrorCode.ALREADY_EXIST);
        }
    }
    public Member findOne(int memberId) {
        return memberRepository.findById(memberId).get();
    }
}
