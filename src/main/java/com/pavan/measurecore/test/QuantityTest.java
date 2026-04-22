package com.pavan.measurecore.test;

import com.pavan.measurecore.model.Quantity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityTest {

    @Test
    void shouldReturnTrueForSameQuantities() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(5);

        assertEquals(q1, q2);
    }
}