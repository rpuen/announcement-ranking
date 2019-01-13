package com.idealista.ranking.data;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.idealista.ranking.model.Announcement;
import com.idealista.ranking.model.AnnouncementService;
import com.idealista.ranking.model.Picture;
import com.idealista.ranking.model.PictureRepository;
import com.idealista.ranking.model.Quality;
import com.idealista.ranking.model.Typology;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

	private Picture pic1 = new Picture(
			"https://www.idealista.com/pictures/1",
			Quality.SD
			);
	
	private Picture pic2 = new Picture(
			"https://www.idealista.com/pictures/2",
			Quality.HD
			);
	
	private Picture pic3 = new Picture(
			"https://www.idealista.com/pictures/3",
			Quality.SD
			);
	
	private Picture pic4 = new Picture(
			"https://www.idealista.com/pictures/4",
			Quality.HD
			);
	
	private Picture pic5 = new Picture(
			"https://www.idealista.com/pictures/5",
			Quality.SD
			);
	
	private Picture pic6 = new Picture(
			"https://www.idealista.com/pictures/6",
			Quality.SD
			);
	
	private Picture pic7 = new Picture(
			"https://www.idealista.com/pictures/7",
			Quality.SD
			);
	
	private Announcement anun1 = new Announcement(
			"Este piso es una ganga, compra, compra, COMPRA!!!!!",
			Typology.CHALET,
			300,
			0,
			null);

	private Announcement anun2 = new Announcement(
			"Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo",
			Typology.FLAT,
			0,
			0,
			Arrays.asList(new Picture[] {pic4}));

	private Announcement anun3 = new Announcement(
			"",
			Typology.CHALET,
			210,
			25,
			Arrays.asList(new Picture[] {pic2}));

	private Announcement anun4 = new Announcement(
			"Ático céntrico muy luminoso y recién reformado, parece nuevo", 
			Typology.FLAT, 
			130, 
			0,
			Arrays.asList(new Picture[] {pic5}));

	private Announcement anun5 = new Announcement(
			"Pisazo",
			Typology.FLAT, 
			130,
			0,
			Arrays.asList(new Picture[] {pic3, pic4}));

	private Announcement anun6 = new Announcement(
			"",
			Typology.GARAGE,
			0, 
			0,
			Arrays.asList(new Picture [] {pic6}));

	private Announcement anun7 = new Announcement(
			"Garage en el centro de Albacete",
			Typology.GARAGE,
			300, 
			0,
			null);
	
	private Announcement anun8 = new Announcement(
			"Maravilloso chalet situado en als afueras de un pequeño pueblo rural."  
			+ "El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!", 
			Typology.CHALET,
			150,
			20, 
			Arrays.asList(new Picture [] {pic1, pic7}));

	@Bean
	CommandLineRunner initDatabase(AnnouncementService repo, PictureRepository picRepo) {
		return args -> {
			log.info("Preloading picture:" + picRepo.save(pic1));
			log.info("Preloading picture:" + picRepo.save(pic2));
			log.info("Preloading picture:" + picRepo.save(pic3));
			log.info("Preloading picture:" + picRepo.save(pic4));
			log.info("Preloading picture:" + picRepo.save(pic5));
			log.info("Preloading picture:" + picRepo.save(pic6));
			log.info("Preloading picture:" + picRepo.save(pic7));
			repo.save(Arrays.asList(new Announcement [] {anun1, anun2, anun3, anun4, anun5, anun6, anun7, anun8}));
		};
	}
}
