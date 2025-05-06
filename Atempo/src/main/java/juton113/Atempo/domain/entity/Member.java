package juton113.Atempo.domain.entity;

import jakarta.persistence.*;
import juton113.Atempo.auditing.BaseTimeEntity;
import juton113.Atempo.domain.enums.CertificationType;
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
public class Member extends BaseTimeEntity {
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
    CertificationType certificationType;

    String certificationNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void updateProfile(String name,
                              String nickName,
                              String profileUrl) {
        this.name = name;
        this.nickName = nickName;
        this.profileUrl = profileUrl;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void updateCertificationInfo(CertificationType certificationType, String certificationNumber) {
        this.certificationType = certificationType;
        this.certificationNumber = certificationNumber;
    }
}
