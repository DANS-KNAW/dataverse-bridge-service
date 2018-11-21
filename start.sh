#!/bin/bash
#todo: first check whether the service already started, using  curl -X GET 'http://localhost:9285/admin/health'
#todo: check whether the required properties in the application.properties exists
#Checking example:
#var filename = config/application.properties
#while read -r line; do
#    name="$line"
#    echo "Name read from file - $name"
#done < "$filename"

echo "Starting dataverse bridge Quickstart...."
echo "Starting bridge service ...."
nohup java -jar -Xms512M bridge-service.jar >> /dev/null &
echo "Starting ... "
( tail -f -n0  logs/bridge-service.log & ) | grep -q "Started BridgeService"
echo "Bridge service is started... "
echo "Now, you can open your favourite browser and visit the bridge API: http://localhost:8592/api/v1"
