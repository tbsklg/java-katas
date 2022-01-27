package kyufive;

import static java.util.Arrays.copyOfRange;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

class ValidateParentheses {

    public static boolean validParentheses(String parens) {
        final MyStack<Character> stack = MyStack.empty();
        final var chars = parens.toCharArray();

        return validate(stack, chars);
    }

    private static class MyStack<T> {

        private final Deque<T> deque;

        private MyStack(Deque<T> deque) {
            this.deque = deque;
        }

        public static <T> MyStack<T> empty() {
            Deque<T> deque = new ArrayDeque<>();

            return new MyStack<>(deque);
        }

        public MyStack<T> push(T c) {
            final var newDeque = new ArrayDeque<T>();

            newDeque.addAll(deque);
            newDeque.push(c);

            return new MyStack<T>(newDeque);
        }

        private Optional<Pair<MyStack<T>, T>> pop() {
            if (this.deque.isEmpty()) {
                return Optional.empty();
            }

            final var newDeque = new ArrayDeque<T>();
            newDeque.addAll(this.deque);
            
            final var first = newDeque.pop();
            final var myStack = new MyStack<T>(newDeque);

            return Optional.of(Pair.with(myStack, first));
        }

        public boolean isEmpty() {
            return this.deque.isEmpty();
        }
    }

    private static boolean validate(MyStack<Character> stack, char[] c) {
        if (!stack.isEmpty() && c.length == 0) {
            return false;
        }

        if (stack.isEmpty() && c.length == 0) {
            return true;
        }

        final var head = c[0];
        final var tail = copyOfRange(c, 1, c.length);

        if (!isParentheses(head)) {
            return validate(stack, tail);
        }

        if (isOpenParentheses(head)) {
            return validate(stack.push(')'), tail);
        }

        final var mayBeFirst = stack.pop();
        if (!mayBeFirst.isPresent()) {
            return false;
        }

        final var first = mayBeFirst.get().snd();
        final var newStack = mayBeFirst.get().fst();

        return first == ')' && validate(newStack, tail);
    }

    private static boolean isOpenParentheses(final char head) {
        return head == '(';
    }

    private static boolean isParentheses(final char head) {
        return head == ')' || isOpenParentheses(head);
    }

    private static class Pair<L, R> {
        private final L left;
        private final R right;

        private Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public static <L, R> Pair<L, R> with(final L left, final R right) {
            return new Pair<L, R>(left, right);
        }

        public L fst() {
            return left;
        }

        public R snd() {
            return right;
        }
    }
}