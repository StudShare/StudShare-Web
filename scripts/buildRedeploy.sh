#!/bin/sh

echo "************ UNDEPLOYING *******************"
./../../../../../opt/devel/glassfish3/bin/asadmin undeploy agencja
echo "************ BUILDING **********************"
mvn package
echo "************ DEPLOYING *********************"
./../../../../../opt/devel/glassfish3/bin/asadmin deploy target/agencja.war
