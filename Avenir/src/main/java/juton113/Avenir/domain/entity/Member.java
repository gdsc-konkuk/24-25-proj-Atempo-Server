package juton113.Avenir.domain.entity;

import jakarta.persistence.*;
import juton113.Avenir.domain.enums.Role;
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
    Long id;

    @Column(nullable = false)
    String provider;

    @Column(nullable = false)
    String providerId;

    @Column(nullable = false)
    String name;

    @Column
    String nickName;

    @Column(nullable = false)
    String email;

    @Column(nullable = false)
    String profileUrl;

    @Enumerated(EnumType.STRING)
    private Role role;
}
