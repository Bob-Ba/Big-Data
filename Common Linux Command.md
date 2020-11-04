
**Start commands:**
```
sbin/start-dfs.sh
sbin/start-yarn.sh
mapred --daemon start historyserver
```
 
HDFS commands:
```
hdfs dfs -ls -R /user/Bob
hdfs dfs -mkdir /user/Bob/input
hdfs dfs -rm -r /user/Bob/output
```
 
Permission commands:
```
sudo chmod -R 755 /apache-tomcat
 
sudo chown Bob:Bob module/ -R
```

Push:
```
scp -r module root@hadoop101:/opt/module  (on hadoop100, push data from hadoop100 to hadoop101)
```
 
Pull:
```
cd  /opt
scp -r Bob@hadoop100:/opt/module ./ (on hadoop102, get data from hadoop100)
```
 
Other examples:
```
on hadoop101, transfer data from hadoop100 to hadoop102
scp -r Bob@hadoop100:/opt/module root@hadoop102:/opt/module
```
 
Copy environment varables:
```
scp /etc/profile root@hadoop101:/etc/profile
source /etc/profile
```
