FROM zenika/alpine-kotlin:1.2-jdk8
WORKDIR /home/project

RUN apk --update add --virtual=build-dependencies build-base git gradle

CMD [ "bash" ]