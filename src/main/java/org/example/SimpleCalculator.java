package org.example;

public class SimpleCalculator {

        public int run(String input) {
                String expression = input.replaceAll("\\s+", "");
                return calculator(expression, 0, expression.length() - 1);
        }

        private int calculator(String expression, int start, int end) {



                if (start > end) {
                        throw new IllegalArgumentException("Invalid range: start=" + start + ", end=" + end + ", expr=" + expression);
                }

                while (start <= end && expression.charAt(start) == '(' && findMatchingParenthesis(expression, start) == end) {
                        start++;
                        end--;
                }

                if (expression.charAt(start) == '-') {
                        if (start == 0 || expression.charAt(start - 1) == '(' || isOperator(expression.charAt(start - 1))) {
                                return -calculator(expression, start + 1, end);
                        }
                }


                int operatorIndex = findOperator(expression, start, end);
                if (operatorIndex == -1) {
                        try {
                                return Integer.parseInt(expression.substring(start, end + 1));
                        } catch (NumberFormatException e) {
                                throw new IllegalArgumentException("Invalid number format: " + expression.substring(start, end + 1));
                        }
                }

                int left = calculator(expression, start, operatorIndex - 1);
                int right = calculator(expression, operatorIndex + 1, end);

                return applyOperator(expression.charAt(operatorIndex), left, right);
        }

        private int findMatchingParenthesis(String expression, int start) {
                int count = 1;
                int i = start + 1;
                while (i < expression.length() && count > 0) {
                        if (expression.charAt(i) == '(') {
                                count++;
                        } else if (expression.charAt(i) == ')') {
                                count--;
                        }
                        i++;
                }
                return count == 0 ? i - 1 : -1;
        }

        private int findOperator(String expression, int start, int end) {
                int parenthesesLevel = 0;
                int lastAddSubtract = -1;
                int lastMultiply = -1;

                for (int i = start; i <= end; i++) {
                        char c = expression.charAt(i);
                        if (c == '(') {
                                parenthesesLevel++;
                        } else if (c == ')') {
                                parenthesesLevel--;
                        } else if (parenthesesLevel == 0) {
                                if (c == '+' || (c == '-' && i > start && !isOperator(expression.charAt(i - 1)))) {
                                        lastAddSubtract = i;
                                } else if (c == '*') {
                                        lastMultiply = i;
                                }
                        }
                }

                if (lastAddSubtract != -1) {
                        return lastAddSubtract;
                }
                return lastMultiply;
        }

        private boolean isOperator(char c) {
                return c == '+' || c == '-' || c == '*';
        }

        private int applyOperator(char operator, int left, int right) {
                switch (operator) {
                        case '+': return left + right;
                        case '-': return left - right;
                        case '*': return left * right;
                        case '/':
                                if (right == 0) {
                                        throw new ArithmeticException("Division by zero");
                                }
                                return left / right;
                        default:
                                throw new IllegalArgumentException("Unsupported operator: " + operator);
                }
        }
}
