
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;

/**
 * @author 孔德昱
 * @date 2023/1/11 20:26 星期三
 */
public class main {
    public static void main(String[] args) throws Exception {
        String file_path = "D:\\Codes\\python\\crawler\\books.csv";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file_path), "gbk"));
        String line;
        Connection connection = JDBCUtils.getConnection();
        connection.setAutoCommit(false);
        String sql = "insert into book_item(id,book_name,publication_date,author_name,publisher_name,rating) values (?,?,?,?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        line = br.readLine(); //跳过第一行(不要删)
        int i = 1;
        while ((line = br.readLine()) != null) {
            // 一行一行地处理...
            String[] items = line.split(",");
            System.out.println(Arrays.toString(Arrays.stream(items).toArray()));

            ps.setObject(1, items[0]);
            ps.setObject(2, items[1]);
            ps.setObject(3, items[2]);
            ps.setObject(4, items[3]);
            ps.setObject(5, items[4]);
            ps.setObject(6, items[5]);

            ps.addBatch();
            if (i % 500 == 0) {
                ps.executeBatch();
                ps.clearBatch();
            }
            i++;
//            ps.execute();
        }
        ps.executeBatch();
        ps.clearBatch();
        connection.commit();
        JDBCUtils.closeResource(connection, ps);
    }


}
