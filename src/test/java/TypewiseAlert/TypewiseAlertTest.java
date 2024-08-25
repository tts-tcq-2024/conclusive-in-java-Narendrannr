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
    public void infersBreachAsPerLimits() {
        assertTrue(TypewiseAlert.inferBreach(12, 20, 30) == BreachType.TOO_LOW);
        assertTrue(TypewiseAlert.inferBreach(35, 20, 30) == BreachType.TOO_HIGH);
        assertTrue(TypewiseAlert.inferBreach(25, 20, 30) == BreachType.NORMAL);
    }

    @Test
    public void classifiesTemperatureBreachForPassiveCooling() {
        PassiveCooler cooler = new PassiveCooler();
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 0) == BreachType.NORMAL);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 35) == BreachType.NORMAL);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, -1) == BreachType.TOO_LOW);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 36) == BreachType.TOO_HIGH);
    }

    @Test
    public void classifiesTemperatureBreachForMediumActiveCooling() {
        MediumActiveCooler cooler = new MediumActiveCooler();
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 0) == BreachType.NORMAL);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 40) == BreachType.NORMAL);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, -1) == BreachType.TOO_LOW);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 41) == BreachType.TOO_HIGH);
    }

    @Test
    public void classifiesTemperatureBreachForHighActiveCooling() {
        HighActiveCooler cooler = new HighActiveCooler();
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 0) == BreachType.NORMAL);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 45) == BreachType.NORMAL);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, -1) == BreachType.TOO_LOW);
        assertTrue(TypewiseAlert.classifyTemperatureBreach(cooler, 46) == BreachType.TOO_HIGH);
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
        // Redirect System.out to capture printed content
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));

        emailAlert.SendAlertInfo(BreachType.TOO_HIGH);
        
        String expectedOutput = "To: test@example.com\nHi, the temperature Too High\n";
        assertEquals(expectedOutput, out.toString());

        // Reset System.out
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
