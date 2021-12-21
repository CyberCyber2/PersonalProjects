#!/bin/bash
logfile='/home/alpha/minecraft/outputlog.txt'

echo "***" > $logfile #to clear file
now=$(date +"%T")
echo "Current Time: $now" | tee -a $logfile #add date to file

if [[ $(python3 /home/alpha/minecraft/02-serverhealth.py | grep 'Ping Works') ]]; then
        echo -e "The Server is running" | tee -a  $logfile
else
        echo -e "The Server is down" | tee -a $logfile
        ps -ef | grep 'java' | grep -v grep | awk '{print $2}' | xargs -r kill -9
        echo -e "Restarting Server" | tee -a $logfile
        bash /home/alpha/minecraft/01-startserver.sh #output handled in own file
fi
