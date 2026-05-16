public class ToppingStation extends CakeStation{
    public ToppingStation(Ingredient ingredient){
        super("ToppingStation",ingredient);
    }

    @Override
    public void applyToCake(Cake cake) {
        cake.applyLayer(ingredient);
    }
}
