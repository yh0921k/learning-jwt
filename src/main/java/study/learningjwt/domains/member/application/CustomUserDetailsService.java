package study.learningjwt.domains.member.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.learningjwt.domains.member.domain.MemberEntity;
import study.learningjwt.domains.member.domain.MemberRepository;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
  private final MemberRepository memberRepository;

  public CustomUserDetailsService(MemberRepository userRepository) {
    this.memberRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String username) {
    return memberRepository.findOneWithAuthoritiesByMemberName(username)
        .map(user -> createUser(username, user))
        .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
  }

  private org.springframework.security.core.userdetails.User createUser(String username, MemberEntity memberEntity) {
    if (!memberEntity.isActivated()) {
      throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
    }
    List<GrantedAuthority> grantedAuthorities = memberEntity.getAuthorities().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
        .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(memberEntity.getMemberName(),
        memberEntity.getPassword(),
        grantedAuthorities);
  }
}