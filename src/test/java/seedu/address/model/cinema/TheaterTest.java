package seedu.address.model.cinema;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class TheaterTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Theater((Integer)null,null,null));
    }

    @Test
    public void constructor_invalidTheater_throwsIllegalArgumentException() {
        int invalidTheaterNumber = 0;
        String invalidTheaterSeats = "0";
        String invalidTheaterStatus = "C";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Theater(invalidTheaterNumber, invalidTheaterSeats, invalidTheaterStatus));
    }

    @Test
    public void isValidTheater() {
        // number of seats and status is null
        Assert.assertThrows(NullPointerException.class, () -> Theater.isValidTheater(0, null,null));

        // valid theaterNumber and status but invalid number of seats
        assertFalse(Theater.isValidTheater(10,"0","M")); // number of seat is 0

        // valid theaterNumber and number of seats but invalid status
        assertFalse(Theater.isValidTheater(10,"5","F")); // status is not A,M,U

        // valid number of seats and status but invalid theaterNumber
        assertFalse(Theater.isValidTheater(0,"5","M")); // theaterNumber is 0

        // invalid number of seats and theaterNumber but valid status
        assertFalse(Theater.isValidTheater(0,"0","M")); // number of seat is 0
        assertFalse(Theater.isValidTheater(0,"-5","A")); // number of seat is negative

        // invalid number of seats and status, but valid theaterNumber
        assertFalse(Theater.isValidTheater(2,"-5","O")); // status is not A,M,U, number of seats is negative

        // invalid status and theaterNumber but valid number of seats
        assertFalse(Theater.isValidTheater(0,"2","C")); // status is not A,M,U, theaterNumber is 0

        // valid theater number, number of seats and status
        assertTrue(Theater.isValidTheater(1, "2","A"));
    }
}
