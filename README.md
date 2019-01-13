# announcement-ranking

This is a program which order announcement on a web portal depending on some factors.

Setting up:
- mvn clean package
- java -jar target/announcement-ranking-0.0.1-SNAPSHOT.jar

URLS:
- localhost:8080/announcements -> Para el encargado, listado de todos los anuncios
- localhost:8080/admin/announcements -> Para el encargado de calidad, listado de todos los anuncios irrelevantes
- localhost:8080/user/announcements -> Para el usuario, listado de todos los anuncios relevantes ordenados de mayor a menor puntuación
