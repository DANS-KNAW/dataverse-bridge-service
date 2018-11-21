#!/bin/bash
echo "Trying to shutdown the bridge application...."
curl -X POST 'http://localhost:9285/admin/shutdown'
echo " "
echo "Thanks for using the Dataverse Bridge...."
echo "Tot ziens!"
