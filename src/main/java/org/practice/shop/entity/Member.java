package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;
import org.practice.shop.constant.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name="member")
@Entity
@SequenceGenerator(name="myMemberSeq", sequenceName = "member_seq", initialValue = 1, allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myMemberSeq")
    @Column(name="member_id")
    private Long id;

    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING) //Enum타입에 String으로 넣겠다 라는 의미
    private Role role;
}
