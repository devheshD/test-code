# 단위 테스트
- 클래스 or 메서드를 코드 단위가 의도한대로 작동하는지를 독립적으로 검증하는 테스트
- 외부에 의존되지 않기 때문에 검증속도가 빠르고 안정적
- 안정적인 이유는 구조를 바꿔도 기존 테스트가 깨지지 않기 때문
- JUnit5
    - 단위 테스트를 위한 테스트 프레임워크
- AssertJ
    - 단위 테스트를 위한 단언 라이브러리
    - JUnit5와 함께 사용
    - 메서드 체이닝이 가능하고 풍부한 조건을 제공
- DisplayName은 상세하게 작성하자

# BDD (Behavior Driven Development)
- TDD의 발전된 형태
  - 함수 단위의 테스트에 집중하기 보단 시나리오 기반한 테스트 케이스 자체에 집중하여 테스트
  - 개발자가 아닌 사람이 봐도 이해할수있는 정도의 추상화 수준을 권장
- Given
    - 테스트를 시작하기 전의 상태
- When
    - 테스트를 시작하는 시점
- Then
    - 테스트를 마친 후의 상태
- 어떠한 환경에서(Given), 어떤 행동을 했을때(When), 어떤 상태변화가 일어난다(Then)

# 통합 테스트
- 여러 모듈이 통합되어 동작하는지 검증하는 테스트
- 일반적으로 작은 범위의 단위 테스트만으로는 기능 전체의 신뢰성을 보장하기 힘듬


# unit 패키지 요구 사항
1. 주문 목록에 음료 추가/삭제
2. 주문 목록 전체 삭제
3. 주문 목록 총 금액 계산
4. 주문 생성
5. 한 종류의 음료 여러잔을 한 번에 담는 기능
6. 가게 운영 시간(10:00 ~ 21:00) 외에는 주문을 생성할 수 없음

# 키오스크 요구 사항
### 상품
1. 키오스크 주문을 위한 상품 후보 리스트 조회
2. 상품의 판매 상태 : 판매중, 판매보류, 판매중지
    - 판매중, 판매보류인 상태의 상품을 화면에 보여준다.
3. 화면단에 보여줄 상품 정보 
    - Id, 상품 번호, 상품 타입, 판매 상태, 상품 이름, 가격

### 주문
1. 상품 번호 리스트를 받아 주문 생성
2. 주문은 주문 상태, 주문 등록 시간을 가진다.
3. 주문의 총 금액을 계산할 수 있다.

### 재고
1. 주문 생성시 재고 확인 및 개수 차감 후 생성
2. 재고는 상품번호를 가진다
3. 재고와 관련있는 상품 타입은 병 음료, 베이커리

### 관리자 페이지
1. 신규 상품을 등록할 수 있다.
2. 상품명, 상품 타입, 판매 상태, 가격 등을 입력받는다.
3. 하루 매출 통계 조회
