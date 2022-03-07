# DesignPattern_Study_Facade

# Notion Link
https://five-cosmos-fb9.notion.site/Facade-5692c8398e8c48db98d4cdf6a181a0b3

# 퍼사드 (Facade)

### 의도

한 서브시스템 내의 인터페이스 집합에 대한 획일화된 하나의 인터페이스를 제공하는 패턴

**서브시스템을 사용하기 쉽도록 상위 수준의 인터페이스를 정의한다.**

![image](https://user-images.githubusercontent.com/18654358/157132626-0f105b02-c8d3-45f8-bbc0-d6d87aad5c7e.png)


<aside>
🎈 건물의 앞면 처럼 뒷면의 복잡함은 숨겨두고 앞면만 제공하는 것

</aside>

**단순한 예.**

- 복잡하고 많은 라이브러리와 클래스들을 이용하여 Client가 기능을 제공하고 있을 때
- 퍼사드 패턴을 이용해 Client는 쉽게 이를 사용할 수 있도록 한다.

![image](https://user-images.githubusercontent.com/18654358/157132653-57b7f931-9767-4bee-9679-15c51268cbbd.png)

**예. 로켓은 Stage1과 Stage2, Capsule 로 구성되어 있다.**

- 로켓이 발사되면 Stage1 → Stage2 → Capsule 로 처리되는 일련의 과정이 있다.
- 이를 Client가 각각 Stage1, Stage2, Capsule 을 직접 처리한다면, 할 수 있겠지만 복잡하고 힘들다.
- 이럴 때 Rocket이라는 퍼사드 패턴의 인터페이스를 두고 쉽게 사용할 수 있도록 할 수 있다.

**Stage1, Stage2, Capsule 부품 정의**

```java
public class Stage1 {
    public void ignite(){
        // 점화
        System.out.println("1st ignite!");
    }
    public void liftOff(){
        // 이륙
        System.out.println("1st liftOff!");
    }
    public void eject(){
        // 분리
        System.out.println("1st eject!");
    }
    public void comBack(){
        // 귀환
        System.out.println("1st comback!");
    }
}
```

```java
public class Stage2 {
    public void ignite(){
        // 점화
        System.out.println("2st ignite!");
    }
    public void eject(){
        // 분리
        System.out.println("2st eject!");
    }
}
```

```java
public class Capsule {
    public void ignite(){
        // 점화
        System.out.println("capsule ignite!");
    }
    public void landing(){
        // 착륙
        System.out.println("capsule landing");
    }
}
```

**직접 사용해볼까?**

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("hi");

        // [stage1] 점화 -> 이륙 -> 분리
        // [stage2] 점화
        // [stage1] 귀환
        // [stage2] 분리
        // [capsule] 점화
        // [capsule] 착륙

        Stage1 stage1 = new Stage1();
        Stage2 stage2 = new Stage2();
        Capsule capsule = new Capsule();

        stage1.ignite();
        stage1.liftOff();
        stage1.eject();

        stage2.ignite();
        stage1.comBack();
        stage2.eject();

        capsule.ignite();
        capsule.landing();
    }
}
```

**Facade 패턴을 이용해보자!**

Rocket.java

```java
public class Rocket {
    private Stage1 stage1;
    private Stage2 stage2;
    private Capsule capsule;

    public Rocket(){
        this.stage1 = new Stage1();
        this.stage2 = new Stage2();
        this.capsule = new Capsule();
    }

    public void launch(){
        // [stage1] 점화 -> 이륙 -> 분리
        // [stage2] 점화
        // [stage1] 귀환
        // [stage2] 분리
        // [capsule] 점화
        // [capsule] 착륙
        stage1.ignite();
        stage1.liftOff();
        stage1.eject();

        stage2.ignite();
        stage1.comBack();
        stage2.eject();

        capsule.ignite();
        capsule.landing();
    }
}
```

Rocket을 이용해 발사 해보자

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("hi");

        Rocket rocket = new Rocket();
        rocket.launch();
    }
}
```

<aside>
🎈 클라이언트의 코드가 매우 간결!

</aside>

### 우리 제품에 적용시킬 수 있는 케이스는 무엇이 있을까?

- 가장 쉬운 케이스로는 종합점수를 판단해내는 케이스이지 않을까?
- 기본적으로 인사평가를 구성한 설계 정보를 바탕하여
- 차수별, 평가종류별 점수들을 추리고 계산하여
- 최종적으로 종합점수를 산출해내는 일련의 과정!
- **Client 기준에서는 아래와 같은 Flow를 따라갈 수 있을 듯**
    - 1차 성과점수, 1차 역량점수 ⇒ 계산 1차 종합점수
    - 2차 성과점수, 2차 역량점수 ⇒ 계산 2차 종합점수
    - n차.....
    - 계산된 각 차수별 종합점수를 원하는 비율에 따라 최종 종합점수 산출
- **일련의 Flow를 Facade 패턴을 이용한다면?**
    - Client는 그저 최종 종합점수 산출! 이라는 액션 하나로 원하는 결과를 얻어낸다.

---

### 활용성

- 복잡한 서브시스템에 대한 단순한 인터페이스 제공이 필요할 때
    - 시스템 범위가 확장되면, 또한 구체적으로 설계되면 서브 시스템은 계속 복잡해지게 된다.
    - 또한 패턴을 적용하면 확장성을 고려하여 설계하기 때문에, 작은 클래스들이 많이 만들어지게 된다.
    - 이런 과정은 서브시스템을 재사용 가능한 것으로 만들어주고 , 재정의할 수 있는 단위가 되도록 해주기도 하지만
    - 실제 이런 상세한 재설계나 정제의 내용까지 파악할 필요가 없는 개발자들에게는 복잡해진 각각의 클래스들을 다 이해하면서 버스 시스템을 사용하기란 쉽지 않다.
    - 이럴 때 “퍼사드 패턴”은 서브 시스템에 대한 단순하면서도 기본적인 인터페이스를 제공함으로써 대부분이 개발자들에게 적합한 클래스 형태를 제공한다.
- 추상 개념에 대한 구현 클래스와 사용자 사이에 너무 많은 종속성이 존재할 때 퍼사드의 사용을 통해 사용자와 다른 서브시스템 간의 결합도를 줄일 수 있다.
    - 즉, 서브시스템에 정의된 모든 인터페이스가 공개되면 빈번한 메서드 호출이 있을 수 있으나,
    - 이런 호출은 단순한 형태로 통합하여 제공하고 나머지 부분은 내부적으로 처리함으로써
    - 사용자와 서브시스템 사이의 호출 횟수는 실질적으로 감소하게 되는 효과를 가진다.
- 서브시스템을 계층화 시킬 때 퍼사드 패턴을 이용해 각 서브시스템으 ㅣ계층에 대한 접근점을 제공한다.
