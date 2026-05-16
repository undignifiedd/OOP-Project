public class PanStation extends CakeStation {
    public PanStation(Ingredient ingredient){
        super("PanStation",ingredient);
    }
    @Override
    public void applyToCake(Cake cake) {
        cake.applyLayer(ingredient);
    }
}
