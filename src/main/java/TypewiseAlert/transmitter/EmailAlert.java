package TypewiseAlert.transmitter;

import TypewiseAlert.BreachType;

public class EmailAlert implements TargetAlertInterface{
    private final String mRecepient;

    public EmailAlert(String recepient) {
        mRecepient = recepient;
    }
    @Override
    public void SendAlertInfo(BreachType breachType) {
        System.out.printf("To: %s\n", mRecepient);
        System.out.printf("Hi, the temperature %s\n", breachType.getMessage());
    }
}
