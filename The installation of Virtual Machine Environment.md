# 1. Preparation
```
    VirtualBox 6.1  The official website
    CentOS 8-stream Download (CentOS-Stream-8-x86_64-20200827-dvd1.iso)
```

# 2. Installation
## 2.1. 
```
Install an empty virtual machine first without any systems, which resembles a new laptop without OS.
```

## 2.2. 
```
And then, run the virtual machine, choose the iso mirror file, e.g., CentOS-Stream-8-x86_64-20200827-dvd1.iso
```

## 2.3. 
```
Memory: 1-2G (can be modified any time on VirtualBox panel), Disk: 8-10G
```

## 2.4. Open the network

### a. 
```
Make sure the virtual machine own an Adapter that can only be used to visit the internet by NAT mode.
NAT mode only allows all virtual machines to use one IP that is the same at the master to visit net. 
Namely, all other visitors outside can not visit any virtual machines by IP because they do not have one, they share only one IP from master.

You can see the adapter name, default IP, Gateway and DNS information from the initial panel below, but now you only can
visit the internet while the master can not ping the virtual machine successfully because they are not in the same slash.
```    	 


### b. 
Create another adapter for the communication that the master wants to ping the virtual machine successfully (do this on VirtualBox-->Network)
 	 This adapter allows the master to connect to the virtual machine, such as using ping and ssh to visit and control.
  	Host-only Adapter is established by the VirtualBox through 'Network' in Tools Menu, which will produce a new IP slash that
        will bind the master with the virtual machine together in the same slash. 
         There will be a new adapter shown in master: (the vboxnet0 is the new one)
        terminal panel when using command 'ifconfig', e.g., :
        
        """        
         vboxnet0: flags=8943<UP,BROADCAST,RUNNING,PROMISC,SIMPLEX,MULTICAST> mtu 1500
         ether 0a:00:27:00:00:00 
         inet 192.168.56.1 netmask 0xffffff00 broadcast 192.168.56.255
        """
   
Meanwhile, we can see a new adapter named 'enp0s8' with the new IP such as '192.168.56.1', giving it a static IP
       '192.168.56.100', so you can ping 192.168.56.100 from master, because that is the another IP for the virtual machine to
communicate with master, which is different from the other one called 'enp0s3' that is the IP used to visit the internet.

Note of the Network: The virtual machine will have two adapters, one is used to visit the internet, another one is used to communicated with master.

Examples of the Network configuraton: 
It is better to use static IPs which are created by the VirtualBox at beginning.

ls -l /etc/sysconfig/network-scripts/ifcfg-enp0s3
a. The adapter for surfing internet:
        ONBOOT=yes
        BOOTPROTO=static
        IPADDR=10.0.2.100
        GATEWAY=10.0.2.2
        DNS1=192.168.01
        HWADDR=08:00:27:88:26:DC

ls -l /etc/sysconfig/network-scripts/ifcfg-enp0s8
b. The adapter for communication with master
        ONBOOT=yes
        BOOTPROTO=static
        IPADDR=192.168.56.100
        GATEWAY=192.168.56.1
        NETMASK=255.255.255.0
        DNS1=192.168.56.1
        HWADDR=08:00:27:B1:21:BD

2.5. Change hostname
First, vim /etc/sysconfig/network
Second, update and save with: HOSTNAME=hadoop100
Last, hostnamectl set-hostname hadoop100 (work immediately)

2.6. Configure hosts
First, vim /etc/hosts
Second, update it and save it
Third, source /etc/hosts (make it available)

Part1, Virtual Machine
if there are 3 in the cluster:
192.168.56.100 hadoop100
192.168.56.101 hadoop101
192.168.56.102 hadoop102

Part2, Master
192.168.56.100 hadoop100
192.168.56.101 hadoop101
192.168.56.102 hadoop102


Now, if you can ping every one in the environment, the cluster is built successfully.
