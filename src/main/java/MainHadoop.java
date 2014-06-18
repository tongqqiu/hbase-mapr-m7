
import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.Configuration;

public class MainHadoop {
    public static void main(String[] args) throws Exception {
        byte buf[] = new byte[65 * 1024];

        // maprfs:/// -> uses the first entry in /opt/mapr/conf/mapr-clusters.conf
        // maprfs:///mapr/my.cluster.com/
        // /mapr/my.cluster.com/

        // String uri = "maprfs:///";
        String dirname = "/user/mapr";

        Configuration conf = new Configuration();
        System.out.println(conf.toString());

        //FileSystem fs = FileSystem.get(URI.create(uri), conf); // if wanting to use a different cluster
        FileSystem fs = FileSystem.get(conf);
        System.out.println(fs.toString());

        FileStatus[] files = fs.listStatus(new Path("/user"));
        System.out.println("Files: " + files.length);

        System.out.println(files[0].getPath().toString());

        Path dirpath = new Path(dirname + "/dir");
        Path wfilepath = new Path(dirname + "/file.w");
        //Path rfilepath = new Path( dirname + "/file.r");
        Path rfilepath = wfilepath;


        // try mkdir
        boolean res = fs.mkdirs(dirpath);
        if (!res) {
            System.out.println("mkdir failed, path: " + dirpath);
            return;
        }

        System.out.println("mkdir( " + dirpath + ") went ok, now writing file");

        // create wfile
        FSDataOutputStream ostr = fs.create(wfilepath,
                true, // overwrite
                512, // buffersize
                (short) 1, // replication
                (long) (64 * 1024 * 1024) // chunksize
        );
        ostr.write(buf);
        ostr.close();

        System.out.println("write( " + wfilepath + ") went ok");

        // read rfile
        System.out.println("reading file: " + rfilepath);
        FSDataInputStream istr = fs.open(rfilepath);
        int bb = istr.readInt();
        istr.close();
        System.out.println("Read ok");
    }
 }
