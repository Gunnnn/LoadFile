import java.io.File;
import java.nio.file.Paths;


public class FlatFileReadTest {

    public static void main(String ... args) {
//        File f1 = new File("C:/Temp/dot_1.dot");
        //File f1 = new File("//TestFlatFile");
        //File f1 = Paths.get(".", "resources", "TestFlatFile.txt").normalize().toFile();
        File f1 = new File(FlatFileReadTest.class.getClassLoader().getResource("TestFlatFile.txt").getPath());
        System.out.println(f1.canRead());
        System.out.println(f1.toString());
    }

}
