import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RomanNumeralsTest {

    @Test
    public void convertToRoman() {
        assertNumeral(1, "I");
        assertNumeral(2, "II");
        assertNumeral(3, "III");
        assertNumeral(4, "IV");
        assertNumeral(5, "V");
        assertNumeral(6, "VI");
        assertNumeral(7, "VII");
        assertNumeral(9, "IX");
        assertNumeral(10, "X");
        assertNumeral(11, "XI");
        assertNumeral(12, "XII");
        assertNumeral(44, "XLIV");
        assertNumeral(50, "L");
        assertNumeral(100, "C");
        assertNumeral(500, "D");
        assertNumeral(999, "CMXCIX");
        assertNumeral(1000, "M");
        assertNumeral(1234, "MCCXXXIV");
        assertNumeral(3999, "MMMCMXCIX");
    }

    private void assertNumeral(int arabic, String roman) {
        assertThat(RomanNumeral.toRoman(arabic)).isEqualTo(roman);
    }

    @Test
    public void convertFromRoman() {
        assertRoman("I", 1);
        assertRoman("II", 2);
        assertRoman("III", 3);
        assertRoman("IV", 4);
        assertRoman("V", 5);
        assertRoman("VI", 6);
        assertRoman("VII", 7);
        assertRoman("IX", 9);
        assertRoman("X", 10);
        assertRoman("XV", 15);
        assertRoman("L", 50);
        assertRoman("C", 100);
        assertRoman("D", 500);
        assertRoman("M", 1000);
        assertRoman("MCCXXXIV", 1234);
        assertRoman("MMMCMXCIX", 3999);
    }

    private void assertRoman(String roman, int arabic) {
        assertThat(RomanNumeral.fromRoman(roman)).isEqualTo(arabic);
    }
}
