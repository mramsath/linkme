# Use an official Java runtime as a parent image
FROM openjdk:8-jre-alpine
MAINTAINER mohammadu.ramsath@gmail.com

ARG service=service
ENV hostname=hostname
ENV active_profile=dev
ENV jar_file=$service
ENV preferIpAddress=false
ENV configserver=http://localhost:8888
ENV healhPath=health
ENV server_port=80
ENV healthUrl=http://localhost

RUN apk add curl
HEALTHCHECK  --interval=10s CMD curl --silent --fail $healthUrl/$healhPath || exit 1

WORKDIR /app
ADD $service /app
ENV service=${service}
RUN ls -al
CMD java -jar -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=70 -XX:CompressedClassSpaceSize=64m -XX:ReservedCodeCacheSize=64m -XX:MaxMetaspaceSize=256m -Xms256m -Xmx1024m -Dspring.profiles.active=${active_profile} -Durl.config-server=${configserver} -Deureka.instance.hostname=${hostname} -Deureka.instance.prefer-ip-address=${preferIpAddress}  -Dserver.port=${server_port} ${jar_file}
