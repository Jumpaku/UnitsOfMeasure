FROM zenika/alpine-kotlin:1.2-jdk8
WORKDIR /home/project/UnitsOfMeasure

RUN apk --update add --virtual=build-dependencies build-base git gradle
COPY ./project/UnitsOfMeasure ./
RUN cd UnitsOfMeasure && ./gradlew build

CMD [ "bash" ]