public class StateManager {

    private static StateManager instance;
    private int state; // state 0: Menu, state 1: Cafe, state 2: Boss Fight
    private int score; // score log
    private int sequenceScore; // resets every sequence
    private int sequenceStartScore = 0; // sequenceScore-sequenceStartScore should not be less that threshHold.
    private int sequenceNo = 1;
    private int cakeNo = 1;
    private int timer; // calculated based on difficulty. will implement later in fileManager
    private float fadeAlpha = 1f;
    private boolean fading = false;

    private SoundManager soundManager;

    private Cake currentOrder;
    private Cake playerCake;

    private static final String[] BATTERS = {"Chocolate", "Vanilla", "Strawberry"};
    private static final String[] ICINGS = {"Chocolate", "Vanilla", "Strawberry"};
    private static final String[] TOPPINGS = {"candy", "smiley", "clover"};

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
            currentOrder = generateOrder();
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
        Cake order = new Cake();
        for (int i = 0; i < order.getCakeLayers().length; i++) {
            int randomizer = (int) (Math.random() * 3);
            if (order.getCakeLayers()[i].getLayerType().equals("BATTER")) {
                Ingredient ing = new Ingredient(BATTERS[randomizer], "Batter");
                ing.loadImage();
                order.applyLayer(ing);
            } else if (order.getCakeLayers()[i].getLayerType().equals("ICING")) {
                Ingredient ing = new Ingredient(ICINGS[randomizer], "Icing");
                ing.loadImage();
                order.applyLayer(ing);
            } else if (order.getCakeLayers()[i].getLayerType().equals("TOPPING")) {
                Ingredient ing = new Ingredient(TOPPINGS[randomizer], "Topping");
                ing.loadImage();
                order.applyLayer(ing);
            } else if (order.getCakeLayers()[i].getLayerType().equals("PAN")) {
                order.applyLayer(new Ingredient("Pan", "Pan"));
            }
        }
        return order;
    }

    public void checkBossFightTrigger() {
        if (cakeNo >= 5) {
            score += 5;
            sequenceNo++;
            cakeNo = 0;
            sequenceScore = 0;
            sequenceStartScore = score;
        } else if (score - sequenceStartScore <=-9 ) {
            setState(2);
        }
    }

    public void submitCake(Cake playerCake) {
        this.playerCake = playerCake;
        int correct = playerCake.matchesOrder(currentOrder);
        int cakeScore = correct - ((playerCake.getCakeLayers().length - correct) * 3);
        score += cakeScore;
        sequenceScore += cakeScore;
        cakeNo++;
        fileManager.log(sequenceNo, cakeNo, score, sequenceScore, sequenceStartScore);
        System.out.println("correct: " + correct + " layers: " + playerCake.getCakeLayers().length);
        for (int i = 0; i < playerCake.getCakeLayers().length; i++) {
            CakeLayer p = playerCake.getCakeLayers()[i];
            CakeLayer o = currentOrder.getCakeLayers()[i];
            System.out.println("Layer " + i + ": player=" + (p.getIngredient() != null ? p.getIngredient().getName() : "null") + " order=" + (o.getIngredient() != null ? o.getIngredient().getName() : "null"));
        }
        if (correct == 1) {
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
    public Cake getCurrentOrder(){
        return currentOrder;
    }
}
