import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Unpacker {

    private final ValidationException exception = new ValidationException();

    public String unpack(String input) throws Exception {
        if (!isValid(input))
            throw exception;
        int repeatCount;
        StringBuilder numberStr = new StringBuilder();
        StringBuilder innerExpression = new StringBuilder();
        StringBuilder outputLine = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            var charAtIndex = input.charAt(i);
            switch (charAtIndex) {
                case '[': {
                    charAtIndex = input.charAt(++i);
                    var innerBracketsCount = 0;
                    while (charAtIndex != ']' || innerBracketsCount != 0) {
                        switch (charAtIndex) {
                            case '[' -> innerBracketsCount++;
                            case ']' -> innerBracketsCount--;
                        }
                        innerExpression.append(charAtIndex);
                        charAtIndex = input.charAt(++i);
                    }
                    repeatCount = Integer.parseInt(numberStr.toString());
                    var innerResult = unpack(innerExpression.toString());
                    outputLine.append(innerResult.repeat(repeatCount));
                    numberStr = new StringBuilder();
                    innerExpression = new StringBuilder();
                    break;
                }
                case ']':
                    continue;
                default: {
                    if (Character.isDigit(charAtIndex)) {
                        numberStr.append(charAtIndex);
                    } else outputLine.append(charAtIndex);
                }
            }
        }
        return outputLine.toString();
    }

    private boolean isValid(String input) {
        boolean valid = validateCharacters(input);
        if (!valid)
            return false;
        valid = validateBrackets(input);
        if (!valid)
            return false;
        return validateExpression(input);
    }

    private boolean validateBrackets(String input) {
        int bracketsCount = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '[')
                bracketsCount++;
            else if (input.charAt(i) == ']')
                if (bracketsCount == 0) {
                    exception.addMessage("Неверная скобочная последовательность!");
                    return false;
                } else
                    bracketsCount--;
        }
        return bracketsCount == 0;
    }

    private boolean validateCharacters(String text) {
        Pattern pattern = Pattern.compile("[^a-zA-Z\\d\\[\\]]");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            exception.addMessage("Выражение содержит неверные символы. Допустимые символы: латинские буквы, числа и скобки []");
            return false;
        }
        return true;
    }

    private boolean validateExpression(String input) {
        for (int i = 0; i < input.length(); i++) {
            var charAtIndex = input.charAt(i);
            if (charAtIndex == '[') {
                if (i > 0) {
                    if (!Character.isDigit(input.charAt(i - 1))) {
                        exception.addMessage("Отсутствует число перед открывающейся скобкой", i - 1, input);
                        return false;
                    }
                } else {
                    exception.addMessage("Отсутствует число перед открывающейся скобкой", i, input);
                    return false;
                }
            }
            if (Character.isDigit(charAtIndex) && i < input.length() - 1) {
                while (i < input.length()) {
                    if (input.charAt(i + 1) == '[')
                        break;
                    if (Character.isDigit(input.charAt(i + 1)))
                        i++;
                    else {
                        exception.addMessage("Число не перед открывающейся скобкой", i, input);
                        return false;
                    }
                }
            } else if (Character.isDigit(charAtIndex)) {
                exception.addMessage("Число не перед открывающейся скобкой", i, input);
                return false;
            }
        }
        return true;
    }
}