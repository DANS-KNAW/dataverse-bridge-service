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
nohup java -jar -Xms512M -Xmx2G bridge-service.jar >> /dev/null &
echo "Starting ... "
( tail -f -n0  logs/bridge-service.log & ) | grep -q "Started BridgeService"
echo "Bridge service is started... "
echo "Now, you can open your favourite browser and visit the bridge API: http://localhost:8592/api/v1"
