package com.idealista.ranking.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.idealista.ranking.model.Announcement;
import com.idealista.ranking.model.AnnouncementRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

	private String desc1 = "Este piso es una ganga, compra, compra, COMPRA!!!!!";
	private String typo1 = "CHALET";
	private int size1 = 300;
	private int garden1;
	private int[] pict1 = null;

	private String desc2 = "Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo";
	private String typo2 = "FLAT";
	private int size2;
	private int garden2;
	private int[] pict2 = new int[] {4};

	private String desc3 = "";
	private String typo3 = "CHALET";
	private int size3 = 210;
	private int garden3 = 25;
	private int[] pict3 = new int[] {2};

	private String desc4 = "Ático céntrico muy luminoso y recién reformado, parece nuevo";
	private String typo4 = "FLAT";
	private int size4 = 130;
	private int garden4;
	private int[] pict4 = new int[] {5};

	private String desc5 = "Pisazo";
	private String typo5 = "FLAT";
	private int size5 = 130;
	private int garden5;
	private int[] pict5 = new int[] {3,4};

	private String desc6 = "";
	private String typo6 = "GARAGE";
	private int size6;
	private int garden6;
	private int[] pict6 = new int [] {6};

	private String desc7 = "Garage en el centro de Albacete";
	private String typo7 = "GARAGE";
	private int size7 = 300;
	private int garden7;
	private int[] pict7 = null;

	private String desc8 = "Maravilloso chalet situado en als afueras de un pequeño pueblo rural. "
			+ "El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!";
	private String typo8 = "CHALET";
	private int size8 = 150;
	private int garden8 = 20;
	private int[] pict8 = new int [] {1,7};

	@Bean
	CommandLineRunner initDatabase(AnnouncementRepository repo) {
		return args -> {
			log.info("Preloading" + repo.save(new Announcement(desc1, typo1, size1, garden1, pict1)));
			log.info("Preloading" + repo.save(new Announcement(desc2, typo2, size2, garden2, pict2)));
			log.info("Preloading" + repo.save(new Announcement(desc3, typo3, size3, garden3, pict3)));
			log.info("Preloading" + repo.save(new Announcement(desc4, typo4, size4, garden4, pict4)));
			log.info("Preloading" + repo.save(new Announcement(desc5, typo5, size5, garden5, pict5)));
			log.info("Preloading" + repo.save(new Announcement(desc6, typo6, size6, garden6, pict6)));
			log.info("Preloading" + repo.save(new Announcement(desc7, typo7, size7, garden7, pict7)));
			log.info("Preloading" + repo.save(new Announcement(desc8, typo8, size8, garden8, pict8)));
		};
	}
}
