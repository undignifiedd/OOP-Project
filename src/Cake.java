public class Cake {

    private final int sequenceAmount=3;

    private CakeLayer[] cakeLayers;
    private int numPairs, size, position;
    private boolean isComplete, isTrashed;
    public Cake() {
        this.position = 0;
        this.size = 3;
        this.cakeLayers = new CakeLayer[3];
        cakeLayers[0] = new CakeLayer("BATTER");
        cakeLayers[1] = new CakeLayer("ICING");
        cakeLayers[2] = new CakeLayer("TOPPING");
        this.isComplete = false;
        this.isTrashed = false;
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
    public int matchesOrder(Cake other)  {
        int correct=0;
        for (int i=0; i<cakeLayers.length;i++) {
            if (this.cakeLayers[i].isCorrect(other.getCakeLayers()[i])) {
                correct++;
            }
        }
        return correct;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public int getPosition() {
        return position;
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
    public void setComplete(boolean complete) {
        isComplete = complete;
    }
    public void setTrashed(boolean trashed) {
        isTrashed = trashed;
    }
    public int getNumPairs(){
        return numPairs;
    }
}
