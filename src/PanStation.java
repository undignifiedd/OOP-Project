public class PanStation {
    private String shape;
    public PanStation(String shape){
        this.shape=shape;
    }
    public void applyToCake(Cake cake){
        System.out.println("Pan shape applied: "+shape);
    }
    public String getShape() {
        return shape;
    }
}
