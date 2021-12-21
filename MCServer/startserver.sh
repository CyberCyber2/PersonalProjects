#!/bin/bash
logfile='/home/alpha/minecraft/outputlog.txt'

echo "01-startserver.sh RUNNING" | tee -a  $logfile
ps -ef | grep 'java' | grep -v grep | awk '{print $2}' | xargs -r kill -9
echo "*****JAVA OUTPUT*****" >> $logfile
java -Xmx16G -Xms16G -jar minecraft_server_1.18.1.jar nogui | tee -a $logfile
