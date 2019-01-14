# announcement-ranking

This is a program which order announcement on a web portal depending on some factors.

Setting up:
- mvn clean package
- java -jar target/announcement-ranking-0.0.1-SNAPSHOT.jar

URLS:
- localhost:8080/announcements -> For quality manager, list of all the announcements
- localhost:8080/admin/announcements -> For quality manager, list of all irrelevants announcements
- localhost:8080/user/announcements -> For the customer, list of all relevants announcements sorted by score from greater to lower score

Service class:
- com.idealista.ranking.service.AnnouncementService.java

In this class is defined a save method, where it is computed the score of each announcement following the rules requested on the exercise.

Prepare the data:
- com.idealista.ranking.data.LoadDatabase.java

In this class are defined all the announcement and picture data from the exercise. Then, save method from AnnouncementService is called to compute the score and then write on the DB (in this case, JPA persistence).

Controller:
- com.idealista.ranking.controller.AnnouncementController.java

In this class are defined the GET http requests for the exercise. Note, there aren't post, put neither delete request since they aren't necessary to complete the exercise.
