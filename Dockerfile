FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.13_8 
VOLUME /tmp
COPY ./target/dependency/BOOT-INF/lib /app/lib
COPY ./target/dependency/META-INF /app/META-INF
COPY ./target/dependency/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.youssef.pharmacie.namingserver.ApiGatewayApplication"]