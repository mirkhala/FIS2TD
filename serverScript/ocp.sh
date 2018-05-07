#!/bin/sh
sudo iptables -F

h=$(hostname -s)
name=fisworkshop
long=${#name}
num=${h:$long}

DOMAIN=rhtechofficelatam.com
fisDomain=fis$num.$DOMAIN
fisHost=$name.$fisDomain
profile=fis

echo y | oc-cluster destroy $profile
rm -rf /root/.oc
oc-cluster up $profile --public-hostname=$fisHost --routing-suffix=apps.$fisDomain

sleep 5s
echo y | oc login https://localhost:8443 --username=admin --password=admin --insecure-skip-tls-verify
oc delete project myproject
oc project openshift
oc create -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/fis-image-streams.json
oc create -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/amq63-basic.json
oc create -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/openjdk18-image-stream.json
oc import-image jenkins-pablo --from=docker.io/openshift/jenkins-2-centos7 --confirm 
oc delete template jenkins-persistent
oc create -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/jenkins-pablo-persistent.yml

chcat -d /root/.oc/profiles/$profile/volumes/vol{01..10}

oc new-project db --display-name="Database"
oc adm policy add-scc-to-user anyuid system:serviceaccount:db:default
oc create -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/pgsql-db.json
oc new-app --template=fis2td-db
oc new-project ci --display-name="Continuous Integration"
oc new-app -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/gogs.json --param=HOSTNAME=gogs.$fisDomain
oc new-app -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/nexus_v2.json --param=HOSTNAME_HTTP=nexus.$fisDomain
oc new-project ws --display-name="WebService"
oc new-app -f https://raw.githubusercontent.com/pszuster/FIS2TD/master/templates/webservice.json --param=HOSTNAME_HTTP=webservice.$fisDomain
oc new-app --docker-image=docker.io/openshift/swagger-ui-site:latest --name=swagger-ui
oc expose service/swagger-ui --hostname=swagger-ui.$fisDomain
oc new-app --docker-image=docker.io/mlabouardy/hystrix-dashboard --name=hystrix-dashboard
oc expose service/hystrix-dashboard --hostname=hystrix-dashboard.$fisDomain
