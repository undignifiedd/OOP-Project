public class BatterStation extends CakeStation {
    public BatterStation(Ingredient ingredient){
        super("BatterStation",ingredient);

    }
    @Override
    public void applyToCake(Cake cake) {
        cake.applyLayer(ingredient);

    }
}
