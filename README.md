## Spring Boot를 활용한 쇼핑몰 구현
1. 시큐리티 활용하여 로그인, 회원가입 구현
2. JPA활용 DB오라클 
3. 관리자 - 상품등록
4. 상품 목록
5. 장바구니
6. 결제

* 영속성
    - 상품 엔티티
    - 레포지토리
    - Item 클래스
    - BaseEntity 클래스
    - 등록테스트
* JPA
    - 쿼리메소드 조회
    - 쿼리어노테이션 조회
    - Querydsl 조회

* 타임리프 화면 구현하기
    - header.html
    - footer.html
    - layout1.html

* 회원가입, 로그인, 로그아웃
* 상품등록(상품 사진), 상품 조회
* 장바구니 
* 주문
* 결제(내가 추가할 것)
---
## Entity 테이블 속성 및 제약조건
  1. Item  
     1)id - 상품번호, 기본키 - Bigint  
     2)itemNum - 상품명 - String   
     3)price - 가격 - int  
     4)stockNumber - 재고수량 - int     
     5)itemDetail - 상세설명 - String  
     6)itemSellStatus - 상품판매상태 - enum(SELL,SOLD_OUT)  
     +) 등록날짜 및 수정날짜 (BaseEntity)


## DB
* User
    1)id
    2)name
    3)email
    4)password
    5)address
    5)role

* Item 
    1)id
    2)itemNum
    3)price
    4)stockNum
    5)itemDetail
    6)itemSellStatus

영속성을 모두 구현한 뒤 비즈니스 레이아웃을 구현한다
2. 서비스 인터페이스, 서비스 클래스
3. 컨트롤러, 화면















