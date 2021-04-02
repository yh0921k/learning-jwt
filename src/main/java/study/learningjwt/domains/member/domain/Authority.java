package study.learningjwt.domains.member.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "auth")
@Entity
public class Authority {

  @Id
  @Column(name = "auth_name", length = 50)
  private String authorityName;
}
