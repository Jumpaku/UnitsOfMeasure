FROM zenika/alpine-kotlin:1.2-jdk8
WORKDIR /home/project/UnitsOfMeasure

RUN apk --update add git gradle
COPY ./project/UnitsOfMeasure ./
RUN ./gradlew build

CMD [ "bash" ]