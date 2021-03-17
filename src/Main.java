import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.println("Enter expression: ");
        Scanner in = new Scanner(System.in);
        var input = in.nextLine();
        var unp = new Unpacker();
        try {
            System.out.println(unp.unpack(input.replaceAll(" ", "")));
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
