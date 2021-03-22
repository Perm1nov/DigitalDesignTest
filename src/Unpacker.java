public class Unpacker {

    private final ValidationException exception = new ValidationException();
    private final ExpressionValidator validator = new ExpressionValidator(exception);

    public String tryUnpack(String input) throws ValidationException {
        return unpack(input,false);
    }

    public String unpack(String input,boolean wasValidated) throws ValidationException {
        if(!wasValidated)
        if (!validator.isValid(input))
            throw exception;
        wasValidated = true;

        int repeatCount;
        StringBuilder numberStr = new StringBuilder();
        StringBuilder innerExpression = new StringBuilder();
        StringBuilder outputLine = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            var charAtIndex = input.charAt(i);
            if (charAtIndex == '[') {
                charAtIndex = input.charAt(++i);
                var innerBracketsCount = 0;
                while (charAtIndex != ']' || innerBracketsCount != 0) {
                    if (charAtIndex == '[')
                        innerBracketsCount++;
                    else if (charAtIndex == ']')
                        innerBracketsCount--;

                    innerExpression.append(charAtIndex);
                    charAtIndex = input.charAt(++i);
                }
                repeatCount = Integer.parseInt(numberStr.toString());
                var innerResult = unpack(innerExpression.toString(), wasValidated);
                outputLine.append(innerResult.repeat(repeatCount));
                numberStr.setLength(0);
                innerExpression.setLength(0);
            } else if (charAtIndex == ']') {
                continue;
            }
            else if (Character.isDigit(charAtIndex))
                numberStr.append(charAtIndex);
            else outputLine.append(charAtIndex);
        }
        return outputLine.toString();
    }
}