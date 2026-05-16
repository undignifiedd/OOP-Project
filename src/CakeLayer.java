public class CakeLayer {
    private String layerType;
    private Ingredient ingredient;
    private boolean isApplied;

    public CakeLayer(String layerType) {
        this.layerType = layerType;
        this.ingredient = null;
        this.isApplied = false;
    }

    public void apply(Ingredient ingredient) {
        this.ingredient = ingredient;
        isApplied = true;
    }

    public boolean isCorrect(CakeLayer other) {
        if (this.ingredient.getName().equalsIgnoreCase(other.ingredient.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public String getLayerType() {
        return layerType;
    }

    public boolean isApplied() {
        return isApplied;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}