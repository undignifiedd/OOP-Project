public class StateManager {

    private static StateManager instance;
    private int state; // state 0: Menu, state 1: Cafe, state 2: Boss Fight
    private int score; // score log
    private int sequenceScore; // resets every sequence
    private int sequenceStartScore = 0; // sequenceScore-sequenceStartScore should not be less that threshHold.
    private int sequenceNo = 1;
    private int cakeNo = 1;
    private int threshHold;
    private int timer; // calculated based on difficulty. will implement later in fileManager

    private Cake currentOrder;
    private Cake playerCake;

    private static final String[] BATTERS = { "Chocolate", "Vanilla", "Strawberry" };
    private static final String[] ICINGS = { "Cream", "Fondant", "Glaze" };
    private static final String[] TOPPINGS = { "Sprinkles", "Cherry", "Candle" };

    private FileManager fileManager;

    private StateManager(int state) {
        fileManager = FileManager.getInstance();
        this.state = state;
    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager(2);
        }
        return instance;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public Cake generateOrder() {
        int numPairs = (int) (Math.random() * 3);
        Cake order = new Cake(numPairs);
        for (int i = 0; i < order.getCakeLayers().length; i++) {
            int randomizer = (int) (Math.random() * 3);
            if (order.getCakeLayers()[i].getLayerType().equals("BATTER")) {
                order.applyLayer(new Ingredient(BATTERS[randomizer], "BATTER"));
            } else if (order.getCakeLayers()[i].getLayerType().equals("ICING")) {
                order.applyLayer(new Ingredient(ICINGS[randomizer], "ICING"));
            } else if (order.getCakeLayers()[i].getLayerType().equals("TOPPING")) {
                order.applyLayer(new Ingredient(TOPPINGS[randomizer], "TOPPING"));
            } else if (order.getCakeLayers()[i].getLayerType().equals("PAN")) {
                order.applyLayer(new Ingredient("Pan", "PAN"));
            }
        }
        return order;
    }

    public void checkBossFightTrigger() {
        if (sequenceScore <= threshHold) {
            setState(2);
        } else if (cakeNo >= 3) {
            score += 5;
            sequenceNo++;
            cakeNo = 0;
            sequenceScore = 0;
            sequenceStartScore = score;
        }
    }

    public void submitCake(Cake playerCake) {
        this.playerCake = playerCake;
        int correct = playerCake.matchesOrder(currentOrder);
        int cakeScore = correct - ((playerCake.getCakeLayers().length - correct) * 3);
        score += cakeScore;
        sequenceScore += cakeScore;
        threshHold = sequenceStartScore - 9;
        cakeNo++;
        fileManager.log(sequenceNo, cakeNo, score, sequenceScore, sequenceStartScore, threshHold);
        if (correct == 0) {
            instance.setState(2);
        } else {
            checkBossFightTrigger();
        }
        currentOrder = generateOrder();
    }

}
