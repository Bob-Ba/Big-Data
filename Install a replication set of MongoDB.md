# 1. Prepare three servers
    
```
    mongodb-linux-x86_64-rhel80-4.2.10.tgz
    
    hadoop100 (10.0.2.100), hadoop101 (10.0.2.101), hadoop102 (10.0.2.102)
```

# 2. Prepare three mongo.conf files for every server

## hadoop100:
    
```
systemLog:
    #The path of the log to which mongod or mongos should send all diagnostic logging information
    #
    destination: file
    #
    path: "/opt/module/mongo/logs/mongodb.log"
    #
    logAppend: true

storage:
    #The directory where the mongod instance stores its data.Default value is "/data/db"
    dbPath: "/opt/module/mongo/data/db"
    journal:
        enabled: true

processManagement:
    #Run background
    fork: true

net:
    #Binded IP address for service, the default value is localhost
    #Give the local area IP for shards, e.g., hadoop100
    bindIp: localhost,hadoop100
    #Binded port, the default value is 27017
    port: 27017

replication:
    #The name of the replication set
    replSetName: replTPP0
 
```    
    
## hadoop101:
```    
systemLog:
    #The path of the log to which mongod or mongos should send all diagnostic logging information
    #
    destination: file
    #
    path: "/opt/module/mongo/logs/mongodb.log"
    #
    logAppend: true

storage:
    #The directory where the mongod instance stores its data.Default value is "/data/db"
    dbPath: "/opt/module/mongo/data/db"
    journal:
        enabled: true

processManagement:
    #Run background
    fork: true

net:
    #Binded IP address for service, the default value is localhost
    #Give the local area IP for shards, e.g., hadoop101
    bindIp: localhost,hadoop101
    #Binded port, the default value is 27017
    port: 27017

replication:
    #The name of the replication set
    replSetName: replTPP0

```    

## hadoop102:
```    
systemLog:
    #The path of the log to which mongod or mongos should send all diagnostic logging information
    #
    destination: file
    #
    path: "/opt/module/mongo/logs/mongodb.log"
    #
    logAppend: true

storage:
    #The directory where the mongod instance stores its data.Default value is "/data/db"
    dbPath: "/opt/module/mongo/data/db"
    journal:
        enabled: true

processManagement:
    #Run background
    fork: true

net:
    #Binded IP address for service, the default value is localhost
    #Give the local area IP for shards, e.g., hadoop102
    bindIp: localhost,hadoop102
    #Binded port, the default value is 27017
    port: 27017

replication:
    #The name of the replication set
    replSetName: replTPP0
```
    

# 3. The architecture of replication set
```
Abstract: The architecture is similar to MySQL cluster, including read and write.
```

## a. One Primary with two Secondary nodes


### a 1.1 How to elect Primary node when the original Primary node goes down.


## b. One Primary, one Secondary and one 
```
Abstract: Arbiter node does not store data generally, which is only responsible for election.
```

### b 1.1 Election with Arbiter


# 4. The settings of replication set
## 4.1 Check the connection between all members in repica set
```
Test the connection from hadoop100 to ohter hosts with the following operation set.
./mongo --host hadoop101 --port 27017
./mongo --host hadoop102 --port 27017
Note: Test the connection from other different members, ensure all members can connect to others.
```

## 4.2 Connect to the Primary server and start the settings (Please use hostname to replace IP)
```
./mongo --host hadoop100 --port 27017  

#You have initiate the server first or you can not use any commands and got an exception: NotMasterNoSlaveOk.
rs.initiate( {
   _id : "replTPP0",
   members: [
      { _id: 0, host: "hadoop100:27017" }
   ]
})

```

## 4.3 Show the configuration of the default replication after initiation
```
rs.conf()
```

## 4.4 Adding members in this replication set (The important step)
```
rs.add("hadoop101:27017")  #(Secondary node)

rs.addArb("hadoop102:27017")  #(Arbiter node)
```

## 4.5 Use Secondary node 
```
#If you wanna use the node, you have to acknowledge the role first with the operation set below:
rs.secondaryOk()
```

# 5. Test the replica set 
```
use TPP (use database TPP or the server will create a database named TPP)
db (check the current database)
show dbs (show all databases, if you just create a database, you can see the database name in the result list after you insert one data at least)
```

## 5.1 Insert data in collection
```
db.products.insert( { _id: 1, item: "bag", qty: 20 } )
db.products.find()
```

**Note:** All Shell methods: https://docs.mongodb.com/v4.2/reference/method/
