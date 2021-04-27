import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class VasyaClerkTest {

    @Test
    void shouldCheckoutForOnePeopleInLine() {
        assertThat(tickets(new int[]{25})).isEqualTo(YES);
        assertThat(tickets(new int[]{50})).isEqualTo(NO);
    }

    @Test
    void shouldCheckoutForTwoPeopleInLine() {
        assertThat(tickets(new int[]{25, 25})).isEqualTo(YES);
        assertThat(tickets(new int[]{25, 50})).isEqualTo(YES);
        assertThat(tickets(new int[]{50, 25})).isEqualTo(NO);
        assertThat(tickets(new int[]{25, 50, 50})).isEqualTo(NO);
    }

    @Test
    void shouldCheckoutForFivePeopleInLine() {
        assertThat(tickets(new int[]{25, 25, 50, 50, 100})).isEqualTo(NO);
    }

    private final String YES = "YES";
    private final String NO = "NO";

    private static class MutableInt {
        int value = 1;

        public void increment() {
            ++value;
        }

        public void decrement() {
            --value;
        }

        public int get() {
            return value;
        }
    }

    private static class Checkout {
        private final Map<Integer, MutableInt> checkout = new HashMap<>();

        public void insert25() {
            insert(25);
        }

        public void remove25() {
            remove(25);
        }

        public int count25() {
            return count(25);
        }

        public void insert50() {
            insert(50);
        }

        public void remove50() {
            remove(50);
        }

        public int count50() {
            return count(50);
        }

        public void insert100() {
            insert(100);
        }

        public void remove100() {
            remove(100);
        }

        public int count100() {
            return count(100);
        }

        private int count(int dollar) {
            final MutableInt count = checkout.get(dollar);
            if (count == null) {
                return 0;
            }
            return count.get();
        }

        private void remove(int dollar) {
            final MutableInt count = checkout.get(dollar);
            if (count == null || count.get() == 0) {
                throw new IllegalStateException("No Money!");
            }

            count.decrement();
        }

        private void insert(int dollar) {
            MutableInt count = checkout.get(dollar);
            if (count == null) {
                checkout.put(dollar, new MutableInt());
            } else {
                count.increment();
            }
        }
    }

    private String tickets(int[] persons) {
        var checkout = new Checkout();

        for (int person : persons) {
            if (person == 25) {
                checkout.insert(25);
            }

            if (person == 50) {
                if (checkout.count(25) >= 1) {
                    checkout.remove(25);
                    checkout.insert(50);
                } else {
                    return NO;
                }
            }

            if (person == 100) {
                if (checkout.count(50) >= 1 && checkout.count(25) >= 1) {
                    checkout.remove(50);
                    checkout.remove(25);
                    checkout.insert(100);
                } else if (checkout.count(25) >= 3) {
                    checkout.remove(25);
                    checkout.remove(25);
                    checkout.remove(25);
                    checkout.insert(100);
                } else {
                    return NO;
                }
            }
        }

        return YES;
    }
}
