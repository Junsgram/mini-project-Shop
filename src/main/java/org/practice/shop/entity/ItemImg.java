package org.practice.shop.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="item_img")
@Entity
@SequenceGenerator(name="itemimg_seq", sequenceName = "itemimg_seq", initialValue = 1, allocationSize = 1)
public class ItemImg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "itemimg_seq")
    private Long id;
    private String imgName;                 // 이미지 이름
    private String oriImgName;              // 원본 이미지 이름
    private String imgUrl;                 // 이미지 경로
    private String repimgYn;                // 대표이미지 지정(Y/N)

    @ManyToOne(fetch = FetchType.LAZY)      // 다대일 관계
    @JoinColumn(name="item_id")
    private Item item;


    public void update(String oriImgName, String imgName, String imgUrl){
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
