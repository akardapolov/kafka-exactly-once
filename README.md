# Отправка и прием сообщения в Kafka exactly-once

### Требуемое ПО

Для сборки и запуска проекта требуются:
- [Java 11 (Open JDK)](https://openjdk.java.net/projects/jdk/11/)
- [Apache Maven 3.3+](https://maven.apache.org/)

Для развертывания компонентов приложения используется механизм контейнеризации:
- [Docker](https://www.docker.com/)

### Запуск приложения

Для запуска приложения необходимо:
1. Скомпилировать и собрать проект: mvn clean package
2. Запустить создание образов и контейнеров брокера Kafka в Docker: docker-compose up -d
3. Настроить параметры подключения к Kafka в application.yml
4. Запустить проект локально 
5. Из Postman отправить POST запрос на http://localhost:8888/post (body из файла packet.xml)

## Лицензия
[![GPLv3 license](https://img.shields.io/badge/License-GPLv3-blue.svg)](http://perso.crans.org/besson/LICENSE.html)

  Code released under the GNU General Public License v3.0
  
## Контакты
  Created by [@akardapolov](mailto:akardapolov@gmail.com)