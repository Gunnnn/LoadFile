import java.io.File;

public class FileRead {

    public static void main(String ... args) {
        File f1 = new File("C:/Temp/dot_1.dot");
        System.out.println(f1.canRead());
    }

}
