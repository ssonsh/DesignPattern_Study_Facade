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
