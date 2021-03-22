import java.util.Scanner;

class Main {
    public static void main(String[] args) {
    /*    System.out.println("Enter expression: ");
        Scanner in = new Scanner(System.in);
        var input = in.nextLine();*/
        Unpacker unp = new Unpacker();
        try {
            System.out.println(unp.tryUnpack("3[xyz]4[xy]z")); // xyz xyz xyz xy xy xy xy z
            System.out.println(unp.tryUnpack("2[3[x]y]")); // xxxyxxxy
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }
}
