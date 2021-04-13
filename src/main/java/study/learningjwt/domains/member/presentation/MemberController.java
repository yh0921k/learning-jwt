package study.learningjwt.domains.member.presentation;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.learningjwt.domains.member.application.MemberService;
import study.learningjwt.domains.member.application.dto.MemberDto;
import study.learningjwt.domains.member.domain.MemberEntity;

@RestController
@RequestMapping("/api")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @PostMapping("/signup")
  public ResponseEntity<MemberEntity> signup(@Valid @RequestBody MemberDto memberDto) {
    return ResponseEntity.ok(memberService.signup(memberDto));
  }

  @GetMapping("/user")
  @PreAuthorize("hasAnyRole('MEMBER', 'ADMIN')")
  public ResponseEntity<MemberEntity> getUserInfo() {
    return ResponseEntity.ok(memberService.getMyUserWithAuthorities().get());
  }

  @GetMapping("/user/{username}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<MemberEntity> getUserInfo(@PathVariable String username) {
    return ResponseEntity.ok(memberService.getUserWithAuthorities(username).get());
  }
}
