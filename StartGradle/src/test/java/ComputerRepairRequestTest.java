import ro.mpp2025.model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ComputerRepairRequestTest {

    @Test
    @DisplayName("First test")
    public void testExample(){
        ComputerRepairRequest crr = new ComputerRepairRequest(1, "John", "Str. Mihai Viteazu, nr. 1", "0740123456", "Lenovo", "2021-03-01", "The laptop doesn't start");
        assertEquals(crr.getOwnerName(), "John");
        assertEquals(crr.getOwnerAddress(), "Str. Mihai Viteazu, nr. 1");
    }

    @Test
    @DisplayName("Second test")
    public void testExample2(){
        assertEquals(1, 1);
    }




}
