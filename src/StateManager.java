public class StateManager {

    private static StateManager instance;
    private String state;

    private StateManager(String state) {
        this.state = state;
    }

    public static StateManager getInstance(){
        if (instance==null){
            instance= new StateManager("Menu");
        }
        return instance;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

}
