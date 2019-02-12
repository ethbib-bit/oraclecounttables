#!/bin/bash

#iterate over all other lib
application_home=`pwd`
CLIENTLIBS=`ls -R1 ${application_home}/lib/*.jar`

for i in ${CLIENTLIBS}; do
   if [ "${CLIENTLIBS}" ] ; then
      LIBPATH=${LIBPATH}:"$i"
   fi
done

#also add conf folder to have log4j config files available
LIBPATH=${LIBPATH}:${application_home}/conf/

cd ${application_home}

#start java application with correct class path and 'config.properties' location as argument
java -cp "${application_home}/bin$LIBPATH" -Xms128m -Xmx1024m com.ethz.oraclecounttable.AppStarter
#/opt/exlibris/product/java/bin/java -cp "${application_home}/bin$LIBPATH" -Xms128m -Xmx1024m com.ethz.oraclecounttable.AppStarter

exit
