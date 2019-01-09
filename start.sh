#!/bin/bash
#
# Copyright (C) 2018 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
# http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

set -e
#todo: first check whether the service already started, using  curl -X GET 'http://localhost:9285/admin/health'

if [ 200 -eq $(curl --write-out '%{http_code}\n' --silent --output /dev/null 'http://localhost:8592/api/v1/index.html') ]
    then
       echo "The server is running... now testing whether the healthiness of the server...."
       if curl -s "http://localhost:9285/admin/health" | grep "UP"
            then
                echo "The service is running..";
            else
                echo "The service is running but one of the service isn't working properly. Please kill the process...";
       fi
       exit 1;
    else
        echo "Trying to start...";
fi

echo "Checking the required api key...";
#check whether the required properties in the application.properties exists
cat ./config/application.properties | while read LINE; do
    if [ "$LINE" = 'bridge.apikey=${bapik}' ];
    then
        echo "Please fill in \${bapik} in bridge.apikey=\${bapik}.";
        exit 1;
    fi
    if [ "$LINE" = 'bridge.apikey=${dvnapik}' ];
    then
        echo "Please fill in \${dvnapik} in bridge.apikey=\${dvnapik}.";
    fi
    if [ "$LINE" = 'bridge.apikey=${bsapik}' ];
    then
        echo "Please fill in \${bsapik} in bridge.apikey=\${bsapik}.";
    fi
done

echo "Starting dataverse bridge Quickstart...."
echo "Starting bridge service ...."
nohup java -jar -Xms512M -Xmx2G bridge-service.jar >> /dev/null &
echo "Starting ... "
( tail -f -n0  ./logs/bridge-service.log & ) | grep -q "Started BridgeService"
echo "Bridge service is started... "
echo "Now, you can open your favourite browser and visit the bridge API: http://localhost:8592/api/v1"

