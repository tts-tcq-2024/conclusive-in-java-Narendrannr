package TypewiseAlert;

public enum BreachType {
    NORMAL("Normal"),
    TOO_LOW("Too Low"),
    TOO_HIGH("Too High");
    private final String mMessage;
    BreachType(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
};
