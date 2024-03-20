package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cart")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SequenceGenerator(name ="cart_seq", sequenceName = "cart_seq", initialValue = 1, allocationSize = 1)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cart_seq")
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch=FetchType.LAZY) // 연관관계는 외래키를 지정할 곳에 어노테이션을 적용한다.
    @JoinColumn(name="member_id") // 어떤 컬럼을 기준으로 join을 진행할 건지 설정
    private Member member;

}
