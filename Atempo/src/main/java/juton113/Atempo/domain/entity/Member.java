package juton113.Atempo.domain.entity;

import jakarta.persistence.*;
import juton113.Atempo.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long memberId;

    String provider;
    String providerId;
    String name;
    String nickName;
    String email;
    String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;
}
