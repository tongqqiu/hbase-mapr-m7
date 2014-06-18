import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

public class CreateTables {
  private static final String TABLE_PATH_REGEX = "/user/mapr/mytesttable.*";
  private static final String MYTESTTABLE3 = "/user/mapr/mytesttable3";
  private static final String MYTESTTABLE2 = "/user/mapr/mytesttable2";
  private static final String MYTESTTABLE1 = "/user/mapr/mytesttable1";

  public static void main(String[] args) throws Exception {
    Configuration conf = HBaseConfiguration.create();
     /* ensure that the filesystem path "/tables" exists on the MapR cluster  */
      conf.set("hbase.table.namespace.mappings", "*:/tables");

      try {


      HBaseAdmin admin = new HBaseAdmin(conf);

      //  create 3 tables
      createTable(admin, MYTESTTABLE1);
      createTable(admin, MYTESTTABLE2);
      createTable(admin, MYTESTTABLE3);

      //  list the newly created table
      listTables(admin, TABLE_PATH_REGEX);

      //  delete the tables
      admin.deleteTable(MYTESTTABLE1);
      admin.deleteTable(MYTESTTABLE2);
      admin.deleteTable(MYTESTTABLE3);

      //  now see if the tables are gone
      listTables(admin, TABLE_PATH_REGEX);

      admin.close();
    } catch (Exception e) {
      System.err.println("Exception at " + e);
      System.exit(1);
    }
  }

  private static void createTable(HBaseAdmin admin, String tablePath)
      throws IOException {
    if (!admin.tableExists(tablePath)) {
      HTableDescriptor table = new HTableDescriptor(tablePath);
      table.addFamily(new HColumnDescriptor("family"));
      admin.createTable(table);
    }
  }

  private static void listTables(HBaseAdmin admin, String tablePathRegex)
      throws IOException {
    HTableDescriptor[] tableDescs = admin.listTables(tablePathRegex);
    System.out.println("Listing tables");
    if (tableDescs != null && tableDescs.length > 0) {
      for (HTableDescriptor tableDesc : tableDescs) {
        System.out.println(tableDesc.getNameAsString());
      }
    } else {
      System.out.println("No table found matching " + tablePathRegex);
    }
  }
}
