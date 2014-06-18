import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;

import java.io.IOException;

/**
 * @author Tongqing Qiu.
 */
public class M7Demo {
    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        HTable table = new HTable(conf, "/user/root/students");
        Put p1 = new Put("student1".getBytes());

        byte[] account = "account".getBytes();
        byte[] address = "address".getBytes();

        p1.add(account, "name".getBytes(), "Alice".getBytes());
        p1.add(address, "street".getBytes(), "123 Ballmer Av".getBytes());
        p1.add(address, "zipcode".getBytes(), "12345".getBytes());
        p1.add(address, "state".getBytes(), "CA".getBytes());

        Put p2 = new Put("student2".getBytes());
        p2.add(account, "name".getBytes(), "Bob".getBytes());
        p2.add(address, "street".getBytes(), "1 Infinite Loop".getBytes());
        p2.add(address, "zipcode".getBytes(), "12345".getBytes());
        p2.add(address, "state".getBytes(), "CA".getBytes());
        p2.add(account, "name".getBytes(), "Frank".getBytes());

        table.put(p1);
        table.put(p2);
        table.close();
    }
}
