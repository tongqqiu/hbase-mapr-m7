hbase-mapr-m7
=============

Sample code of using Hbase API to create Mapr M7 native table

Different from the pure hbase table, we need to 

- Include the mapr-hbase library (see pom.xml)
- Add the table mapping when creating the configuration (so MapR knows that is native table creation)
- Run a node on the cluster (otherwise, need to install client on a separate node and config)
- Set -Djava.library.path=/opt/mapr/lib when run
