package TypewiseAlert;

import TypewiseAlert.cooler.CoolerInterface;
import TypewiseAlert.transmitter.EmailAlert;
import TypewiseAlert.transmitter.TargetAlertInterface;

public class TypewiseAlert 
{
	private static final TargetAlertInterface mEmail = new EmailAlert("a.b@c.com");

    private static BreachType inferBreach(double value, double lowerLimit, double upperLimit) {
        if (value < lowerLimit) {
            return BreachType.TOO_LOW;
        }
        if (value > upperLimit) {
            return BreachType.TOO_HIGH;
        }
        return BreachType.NORMAL;
    }

    private static BreachType classifyTemperatureBreach(
    		CoolerInterface cooler, double temperatureInC) {
        return inferBreach(temperatureInC, cooler.getLowerLimit(), cooler.getUpperLimit());
    }


    public static void checkAndAlert(
    		TargetAlertInterface alertTarget, BatteryCharacter batteryChar, double temperatureInC) {

        BreachType breachType = classifyTemperatureBreach(
                batteryChar.getCooler(), temperatureInC
        );

        if (breachType != BreachType.NORMAL) {
            alertTarget.SendAlertInfo(breachType);
        }

    }
}
