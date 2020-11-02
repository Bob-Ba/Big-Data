Part 1 The architecture of full-distributed Hadoop cluster

                 hadoop100                 hadoop101                          hadoop102
HDFS             NameNode, DataNode        DataNode                           SecondaryNameNode, DataNode
YARN             NodeManager               ResourceManager, NodeManager       NodeManager

Note: use 3 servers to simulate the real cluster, all designs to the nodes are related to the consumption of resources, it is not fixed.

Part 2 Settings
1. core-site.xml
<!-- Put site-specific property overrides in this file. -->
<configuration>
    <!-- Set NameNode in HDFS -->
    <property>
        <name>fs.defaultFS</name>
        <value>hdfs://hadoop100:9000</value>
    </property>

    <!-- Set data directory for the files Hadoop produces when running -->
    <property>
        <name>hadoop.tmp.dir</name>
        <value>/opt/module/hadoop-3.2.1/data/tmp</value>
    </property>
</configuration>

2. hadoop-env.sh
export JAVA_HOME=/opt/module/jdk1.8.0_261

3. hdfs-site.xml
<!-- Put site-specific property overrides in this file. -->

<configuration>
    <!-- Set the number of replication in HDFS -->
    <property>
        <name>dfs.replication</name>
        <value>3</value>
    </property>

    <!-- Set the secondary node for Hadoop -->
    <property>
        <name>dfs.namenode.secondary.http-address</name>
        <value>hadoop102:50090</value>
    </property>
</configuration>

4. yarn-env.sh
export JAVA_HOME=/opt/module/jdk1.8.0_261

5. yarn-site.xml
<configuration>

<!-- Site specific YARN configuration properties -->
    <!-- Set the way how Reducer get data -->
    <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>

    <!-- Set the address of ResourceManager of Yarn -->
    <property>
        <name>yarn.resourcemanager.hostname</name>
        <value>hadoop101</value>
    </property>
    <property>
        <name>yarn.nodemanager.env-whitelist</name>
        <value>JAVA_HOME,HADOOP_COMMON_HOME,HADOOP_HDFS_HOME,HADOOP_CONF_DIR,CLASSPATH_PREPEND_DISTCACHE,HADOOP_YARN_HOME,HADOOP_MAPRED_HOME</value>
    </property>

    <!-- Set aggregation log -->
    <property>
        <name>yarn.log-aggregation-enable</name>
        <value>true</value>
    </property>

    <!-- Log retain second -->
    <property>
        <name>yarn.log-aggregation.retain-seconds</name>
        <value>43200</value>
    </property>
</configuration>

6. mapred-env.sh
export JAVA_HOME=/opt/module/jdk1.8.0_261

7. mapred-site.xml
<!-- Put site-specific property overrides in this file. -->
<configuration>
    <!-- MR run on Yarn -->
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>
    <property>
        <name>mapreduce.application.classpath</name>
        <value>$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/*:$HADOOP_MAPRED_HOME/share/hadoop/mapreduce/lib/*</value>
    </property>

    <!-- Set history server address -->
    <property>
        <name>mapreduce.jobhistory.address</name>
        <value>hadoop100:10020</value>
    </property>

    <!-- Set history server web address -->
    <property>
        <name>mapreduce.jobhistory.webapp.address</name>
        <value>hadoop100:19888</value>
    </property>
</configuration>


Part 3 Start Service
1. Must delete $HADOOP_HOME/data/ and $HADOOP_HOME/logs/ 
rm -rf $HADOOP_HOME/data/  $HADOOP_HOME/logs/  (execute it on all servers)

2. Edit 'workers' file (related to Datanode, its name is 'slaves' in old version)
Write all Datanode servers' host in it, namely, just write the host how many you have for datanode, no any spaces and return.
/opt/module/hadoop-3.2.1/etc/hadoop/slaves (if there is no slaves file, just create it by yourself): 
hadoop100
hadoop101
hadoop102

3. Format namenode
bin/hdfs namenode -format

4. Start cluster
a. Go to the Namenode server, and execute the start command below:
sbin/start-dfs.sh       <--->  (sbin/stop-dfs.sh)

see the logs like:


b. Go to the Resourcemanager server node, start yarn
sbin/start-yarn.sh       <--->  (sbin/stop-yarn.sh)

logs:


5. Stop cluster
sbin/stop-yarn.sh (first)
sbin/stop-dfs.sh   (second)

