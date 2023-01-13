
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

        //插入论文信息
//        String file_path = "D:\\Codes\\python\\crawler\\paper.csv";
//        String sql = "insert into paper_item(paper_id,paper_author,paper_date,paper_title,upload_date,paper_uploader_id) values (?,?,?,?,?,?)";

        //插入用户信息
//        String file_path = "D:\\Codes\\python\\crawler\\user.csv";
//        String sql = "insert into user_item(user_id,user_identity,user_name)values(?,?,?)";

        //插入买书信息
//        String file_path = "D:\\Codes\\python\\crawler\\buyer.csv";
//        String sql = "insert into buyer_item(buyer_id,book_id,user_id)values(?,?,?)";

        String begin_index = "20000";
        String count = "500000";
        String condaPath ="D:\\Anaconda\\Scripts";
        System.out.println(condaPath);
        Process process = Runtime.getRuntime().exec(condaPath+"\\activate.bat python && python D:\\Codes\\python\\crawler\\dbs数据\\fake_data\\fake_data.py "+begin_index+" "+count);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        in = new BufferedReader(new InputStreamReader(process.getInputStream(),"gbk"));
        //接收错误流
        BufferedReader    isError = new BufferedReader(new InputStreamReader(process.getErrorStream(),"gbk"));
        StringBuilder sb= new StringBuilder();
        StringBuilder sbError= new StringBuilder();
        String line=null;
        String lineError= null;
        while ((line = in.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        System.out.println(sb);

        while ((lineError= isError.readLine()) != null) {
            sbError.append(lineError);
            sbError.append("\n");
        }
        System.out.println(sbError);
        in.close();
        isError.close();
        process.waitFor();
        handleBook();
    }

    public static void handleBook() throws Exception {
        // 插入书籍信息
        String file_path = "D:\\Codes\\python\\crawler\\dbs数据\\books.csv";
        String sql = "insert into book_item(id,book_name,publication_date,author_name,publisher_name,rating) values (?,?,?,?,?,?)";

        long start = System.currentTimeMillis();
        insertData(file_path, sql);
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为：" + (end - start));
    }

    public static void insertData(String file_path, String sql) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file_path), "gbk"));
        String line;
        Connection connection = JDBCUtils.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(sql);
        line = br.readLine(); //跳过第一行(不要删)
        int i = 1;
        while ((line = br.readLine()) != null) {
            // 一行一行地处理...
            String[] items = line.split(",");
            System.out.println(Arrays.toString(Arrays.stream(items).toArray()));

            //book
            ps.setObject(1, items[0]);
            ps.setObject(2, items[1]);
            ps.setObject(3, items[2]);
            ps.setObject(4, items[3]);
            ps.setObject(5, items[4]);
            ps.setObject(6, items[5]);

            //paper
//            ps.setObject(1, items[0]);
//            ps.setObject(2, items[2]);
//            ps.setObject(3, items[4]);
//            ps.setObject(4, items[1]);
//            ps.setObject(5, items[5]);
//            ps.setObject(6, items[6]);

            //user
//            ps.setObject(1, items[0]);
//            ps.setObject(2, items[1]);
//            ps.setObject(3, items[2]);

            //buyer
//            ps.setObject(1, items[0]);
//            ps.setObject(2, items[1]);
//            ps.setObject(3, items[2]);

            ps.addBatch();
            if (i % 1000 == 0) {
                System.out.println("\n\n\n提交batch");
                ps.executeBatch();
                ps.clearBatch();
                connection.commit();
            }
            i++;
//            ps.execute();
        }
        System.out.println("\n\n\n提交batch");
        ps.executeBatch();
        ps.clearBatch();
        connection.commit();
        JDBCUtils.closeResource(connection, ps);
    }
}
