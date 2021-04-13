package study.learningjwt.domains.member.application;

import java.util.Collections;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.learningjwt.commons.util.SecurityUtil;
import study.learningjwt.domains.member.application.dto.MemberDto;
import study.learningjwt.domains.member.domain.Authority;
import study.learningjwt.domains.member.domain.MemberEntity;
import study.learningjwt.domains.member.domain.MemberRepository;

@Service
public class MemberService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public MemberEntity signup(MemberDto memberDto) {
    if (memberRepository.findOneWithAuthoritiesByMemberName(memberDto.getUsername()).orElse(null)
        != null) {
      throw new RuntimeException("이미 가입된 유저입니다.");
    }

    Authority authority = Authority.builder().authorityName("ROLE_MEMBER").build();
    MemberEntity memberEntity =
        MemberEntity.builder()
            .memberName(memberDto.getUsername())
            .password(passwordEncoder.encode(memberDto.getPassword()))
            .nickname(memberDto.getNickname())
            .authorities(Collections.singleton(authority))
            .activated(true)
            .build();

    return memberRepository.save(memberEntity);
  }

  @Transactional(readOnly = true)
  public Optional<MemberEntity> getUserWithAuthorities(String username) {
    return memberRepository.findOneWithAuthoritiesByMemberName(username);
  }

  @Transactional(readOnly = true)
  public Optional<MemberEntity> getMyUserWithAuthorities() {
    return SecurityUtil.getCurrentUsername()
        .flatMap(memberRepository::findOneWithAuthoritiesByMemberName);
  }
}
