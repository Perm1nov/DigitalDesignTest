import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionValidator {
    private ValidationException exception;
    public ExpressionValidator(ValidationException e)
    {
        exception = e;
    }
    public boolean isValid(String input) {
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
                    exception.addMessage("Неверная скобочная последовательность");
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
            else if (charAtIndex == ']')
            {
                if(!Character.isLetter(input.charAt(i-1))) {
                    exception.addMessage("Пустая скобка", i, input);
                    return false;
                }
            }
            else if (Character.isDigit(charAtIndex) && i < input.length() - 1) {
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
