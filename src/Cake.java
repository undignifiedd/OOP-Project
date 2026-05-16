public class Cake {
    private CakeLayer[] cakeLayers;
    private String panShape;
    private int numPairs, size;
    private boolean isComplete, isTrashed;
    public Cake(String panShape, int numPairs){
        this.panShape=panShape;
        this.numPairs=numPairs;
        this.size=(numPairs*2)+1;
        this.cakeLayers=new CakeLayer[size];
        for (int i =0; i<numPairs;i++){
             cakeLayers[i*2]=new CakeLayer("BATTER");
             cakeLayers[i*2+1]=new CakeLayer("ICING");
        }
        cakeLayers[numPairs*2]=new CakeLayer("TOPPING");
        this.isComplete=false;
        this.isTrashed=false;
    }
    public void applyLayer(Ingredient ingredient){
        for (CakeLayer c: cakeLayers){
            if (!c.isApplied()){
                c.apply(ingredient);
                break;
            }
        }
        isComplete=true;
        for (CakeLayer c: cakeLayers){
            if (!c.isApplied()){
                isComplete=false;
                break;
            }
        }
    }
    public boolean matchesOrder(Cake other){
        if (!this.getPanShape().equalsIgnoreCase(other.getPanShape())){
            return false;
        }
        for (int i=0; i<cakeLayers.length;i++){
            if (!this.cakeLayers[i].isCorrect(other.getCakeLayers()[i])){
                return false;
            }
        }
        return true;
    }
    public CakeLayer[] getCakeLayers() {
        return cakeLayers;
    }
    public boolean isComplete() {
        return isComplete;
    }
    public boolean isTrashed() {
        return isTrashed;
    }
    public String getPanShape() {
        return panShape;
    }
    public void setComplete(boolean complete) {
        isComplete = complete;
    }
    public void setTrashed(boolean trashed) {
        isTrashed = trashed;
    }
}
