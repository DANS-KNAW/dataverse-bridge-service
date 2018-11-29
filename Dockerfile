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

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG APP_BASE_DIR=/usr/local/bridge
ARG APP_PROP
ARG APP_LIB
ARG JAR_FILE
RUN mkdir -p ${APP_BASE_DIR}/bin && cd ${APP_BASE_DIR} && mkdir config bin/lib
COPY ${JAR_FILE} ${APP_BASE_DIR}/bin/bridge-service.jar
COPY ${APP_LIB} ${APP_BASE_DIR}/bin/lib
COPY ${APP_PROP} ${APP_BASE_DIR}/config

ENTRYPOINT [ "sh", "-c", "cd /usr/local/bridge; java -Dspring.profiles.active=docker -Djava.security.egd=file:/dev/./urandom -jar ./bin/bridge-service.jar" ]