package TypewiseAlert.transmitter;

import TypewiseAlert.BreachType;

public class ControllerAlert implements TargetAlertInterface{
	private int mHeader = 0xfeed;
    @Override
    public void SendAlertInfo(BreachType breachType) {
        System.out.printf("%d : temperature %s\n", mHeader, breachType.getMessage());
    }
}
