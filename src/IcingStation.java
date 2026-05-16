public class IcingStation extends CakeStation{
    public IcingStation(Ingredient ingredient){
        super("IcingStation",ingredient);
    }

    @Override
    public void applyToCake(Cake cake) {
        cake.applyLayer(ingredient);
    }
}
