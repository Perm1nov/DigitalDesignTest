public class ValidationException extends Exception {
    public StringBuilder message;

    public ValidationException() {
        message = new StringBuilder();
    }

    public void addMessage(String message, int index, String source) {
        this.message.append("-".repeat(100)).append("\n");
        this.message.append(source).append("\t|\t").append(message).append("\n");
        this.message.append(" ".repeat(Math.max(0, index)));
        this.message.append("^\n");
        this.message.append("-".repeat(100)).append("\n");
    }

    public void addMessage(String message) {
        this.message.append(message + "\n");
    }

    @Override
    public String getMessage() {
        return message.toString();
    }
}
