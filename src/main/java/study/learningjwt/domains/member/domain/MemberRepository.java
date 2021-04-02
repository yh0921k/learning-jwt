package study.learningjwt.domains.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
  @EntityGraph(attributePaths = "authorities")
  Optional<MemberEntity> findOneWithAuthoritiesByMemberName(String membername);
}
