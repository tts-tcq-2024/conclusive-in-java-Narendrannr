package TypewiseAlert;

import static org.junit.Assert.*;
import org.junit.Test;

import TypewiseAlert.cooler.*;
import TypewiseAlert.transmitter.*;

public class TypewiseAlertTest {

    private static class MockAlertTarget implements TargetAlertInterface {
        public BreachType lastAlertSent;

        @Override
        public void SendAlertInfo(BreachType breachType) {
            lastAlertSent = breachType;
        }
    }


    @Test
    public void checkAndAlertForNormalTemperature() {
        MockAlertTarget alertTarget = new MockAlertTarget();
        BatteryCharacter batteryChar = new BatteryCharacter(new PassiveCooler(), "TestBrand");
        
        TypewiseAlert.checkAndAlert(alertTarget, batteryChar, 25);
        assertNull(alertTarget.lastAlertSent);
    }

    @Test
    public void checkAndAlertForLowTemperature() {
        MockAlertTarget alertTarget = new MockAlertTarget();
        BatteryCharacter batteryChar = new BatteryCharacter(new PassiveCooler(), "TestBrand");
        
        TypewiseAlert.checkAndAlert(alertTarget, batteryChar, -1);
        assertEquals(BreachType.TOO_LOW, alertTarget.lastAlertSent);
    }

    @Test
    public void checkAndAlertForHighTemperature() {
        MockAlertTarget alertTarget = new MockAlertTarget();
        BatteryCharacter batteryChar = new BatteryCharacter(new PassiveCooler(), "TestBrand");
        
        TypewiseAlert.checkAndAlert(alertTarget, batteryChar, 36);
        assertEquals(BreachType.TOO_HIGH, alertTarget.lastAlertSent);
    }

    @Test
    public void testEmailAlertContent() {
        EmailAlert emailAlert = new EmailAlert("test@example.com");
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        emailAlert.SendAlertInfo(BreachType.TOO_HIGH);
        String expectedOutput = "To: test@example.com\nHi, the temperature Too High\n";
        assertEquals(expectedOutput, out.toString());
        System.setOut(System.out);
    }

    @Test
    public void testControllerAlertContent() {
        ControllerAlert controllerAlert = new ControllerAlert();
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        controllerAlert.SendAlertInfo(BreachType.TOO_LOW);
        
        String expectedOutput = "65261 : temperature Too Low\n";
        assertEquals(expectedOutput, out.toString());

        System.setOut(System.out);
    }
}
