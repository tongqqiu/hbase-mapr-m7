import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class MainHbase {
    public static void main(String[] args) throws Exception {
        Configuration conf = HBaseConfiguration.create();
        /* ensure that the filesystem path "/tables" exists on the MapR cluster  */
        conf.set("hbase.table.namespace.mappings", "*:/tables");
        try {
            HBaseAdmin admin = new HBaseAdmin(conf);
            HTableDescriptor desc = new HTableDescriptor("/user/mapr/testtable");
            HColumnDescriptor meta = new HColumnDescriptor("personal".getBytes());
            HColumnDescriptor prefix = new HColumnDescriptor("account".getBytes());
            desc.addFamily(meta);
            desc.addFamily(prefix);
            admin.createTable(desc);
            admin.close();
        } catch (Exception e) {
            System.err.println("Exception at " + e);
            System.exit(1);
        }
    }
}
