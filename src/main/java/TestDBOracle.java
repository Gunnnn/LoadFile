import java.io.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.*;

public class TestDBOracle {

    public static void main(String[] args) {
        generateCsvFile("C:/Temp/test.csv");
    }

    private static void generateCsvFile(String filename) {
        DatabaseMetaData dbMetaData = null;
        String columnNameQuote = "";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = "jdbc:oracle:thin:@//s-msk-dwh-dbt01:1521/GDWH4_S_MSK_DWH_DBT01";
            //jdbc:oracle:thin:scott/tiger@//myhost:1521/myservicename
            Connection conn = DriverManager.getConnection(url, "ruagaas",
                    "Qwerty_123");
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt
                    .executeQuery("select 1 from dual"
                            //"select 'digraph graphname {' as tree  from dual union all\n" +
                            //"select distinct\n" +
                            //"upper('\"'||m.sch_src||'.'||m.tbl_src||'\" -> \"'||m.sch_trg||'.'||m.tbl_trg||'\";') \n" +
                            //"from ruadm.ODI11_TABLE_MAPPING m\n"
                            //"union all select '}' from dual"
                    );
            ResultSetMetaData rsmd = rset.getMetaData();
            rset.next();
            FileWriter fname = new FileWriter(filename);
            BufferedWriter bwOutFile = new BufferedWriter(fname);
            StringBuffer sbOutput = new StringBuffer();
            sbOutput.append("TREE");
            bwOutFile.append(sbOutput);
            bwOutFile.append(System.getProperty("line.separator"));
            System.out.println("No of columns in the table:"
                    + rsmd.getColumnCount());
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                String  cname = rsmd.getColumnName(i);

            }


            System.out.println();
            while (rset.next()) {
                System.out.println(rset.getString(1) + " " + rset.getString(2)
                        + " " + rset.getString(3) + " " + rset.getString(4));
                bwOutFile.append(rset.getString(1) + "," + rset.getString(2) + "," + rset.getString(3) + "," + rset.getString(4));
                bwOutFile.append(System.getProperty("line.separator"));
                bwOutFile.flush();

            }
            bwOutFile.close();
            stmt.close();
            System.out.println("Ok.");
        }

        catch (Exception e) {
            System.err.println("Unable to connect to database: " + e);

        }

    }
}