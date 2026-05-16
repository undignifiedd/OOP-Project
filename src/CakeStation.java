abstract public class CakeStation {
    protected String stationName;
    protected Ingredient ingredient;
    public CakeStation(String stationName, Ingredient ingredient){
        this.stationName=stationName;
        this.ingredient=ingredient;
    }
    public abstract void applyToCake(Cake cake);
    public Ingredient getIngredient() {
        return ingredient;
    }
    public String getStationName() {
        return stationName;
    }
}
