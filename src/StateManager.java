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
    private float fadeAlpha = 1f;
    private boolean fading = false;

    private SoundManager soundManager;

    private Cake currentOrder;
    private Cake playerCake;

    private static final String[] BATTERS = {"Chocolate", "Vanilla", "Strawberry"};
    private static final String[] ICINGS = {"Cream", "Fondant", "Glaze"};
    private static final String[] TOPPINGS = {"Sprinkles", "Cherry", "Candle"};

    private FileManager fileManager;

    private StateManager(int state) {
        fileManager = FileManager.getInstance();
        soundManager = new SoundManager();
        setState(state);
    }

    public static StateManager getInstance() {
        if (instance == null) {
            instance = new StateManager(0);
        }
        return instance;
    }

    public void setState(int state) {
        startFade();
        soundManager.stopMusic();
        if (state == 0) {
            soundManager.playMusic("/Sounds/menuMusic.wav");
        } else if (state == 1) {
            soundManager.playMusic("/Sounds/cafeMusic.wav");
        } else if (state == 2) {
            new Thread(() -> {
                try {
                    Thread.sleep(2100);
                    soundManager.playOnce("/Sounds/gunShot.wav", () -> {
                        soundManager.playMusic("/Sounds/bossFightMusic.wav");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        else if (state == 3){
            soundManager.playMusic("/Sounds/gameOverMusic.wav");
        }
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

    public void startFade() {
        fadeAlpha = 1f;
        fading = true;
    }

    public float getFadeAlpha() {
        return fadeAlpha;
    }

    public boolean getFading() {
        return fading;
    }

    public void setFadeAlpha(float fadeAlpha) {
        this.fadeAlpha = fadeAlpha;
    }
    public void setFading(boolean fading){
        this.fading = fading;
    }
}
