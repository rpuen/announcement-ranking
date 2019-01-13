package com.idealista.ranking.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void allShouldReturnAllAnnouncements() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/announcements",String.class)).contains(
				"[{\"id\":1,\"score\":15,\"description\":\"Este piso es una ganga, compra, compra, COMPRA!!!!!\",\"typology\":\"CHALET\",\"houseSize\":300,\"gardenSize\":0,\"pictures\":[]},{\"id\":2,\"score\":75,\"description\":\"Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo\",\"typology\":\"FLAT\",\"houseSize\":0,\"gardenSize\":0,\"pictures\":[{\"id\":4,\"url\":\"https://www.idealista.com/pictures/4\",\"quality\":\"HD\"}]},{\"id\":3,\"score\":20,\"description\":\"\",\"typology\":\"CHALET\",\"houseSize\":210,\"gardenSize\":25,\"pictures\":[{\"id\":2,\"url\":\"https://www.idealista.com/pictures/2\",\"quality\":\"HD\"}]},{\"id\":4,\"score\":100,\"description\":\"Ático céntrico muy luminoso y recién reformado, parece nuevo\",\"typology\":\"FLAT\",\"houseSize\":130,\"gardenSize\":0,\"pictures\":[{\"id\":5,\"url\":\"https://www.idealista.com/pictures/5\",\"quality\":\"SD\"}]},{\"id\":5,\"score\":75,\"description\":\"Pisazo\",\"typology\":\"FLAT\",\"houseSize\":130,\"gardenSize\":0,\"pictures\":[{\"id\":3,\"url\":\"https://www.idealista.com/pictures/3\",\"quality\":\"SD\"},{\"id\":4,\"url\":\"https://www.idealista.com/pictures/4\",\"quality\":\"HD\"}]},{\"id\":6,\"score\":50,\"description\":\"\",\"typology\":\"GARAGE\",\"houseSize\":0,\"gardenSize\":0,\"pictures\":[{\"id\":6,\"url\":\"https://www.idealista.com/pictures/6\",\"quality\":\"SD\"}]},{\"id\":7,\"score\":0,\"description\":\"Garage en el centro de Albacete\",\"typology\":\"GARAGE\",\"houseSize\":300,\"gardenSize\":0,\"pictures\":[]},{\"id\":8,\"score\":85,\"description\":\"Maravilloso chalet situado en als afueras de un pequeño pueblo rural.El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!\",\"typology\":\"CHALET\",\"houseSize\":150,\"gardenSize\":20,\"pictures\":[{\"id\":1,\"url\":\"https://www.idealista.com/pictures/1\",\"quality\":\"SD\"},{\"id\":7,\"url\":\"https://www.idealista.com/pictures/7\",\"quality\":\"SD\"}]}]");
	}
	
	@Test
	public void oneShouldReturnOneAnnouncement() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/announcements/1", String.class)).contains(
				"{\"id\":1,\"score\":15,\"description\":\"Este piso es una ganga, compra, compra, COMPRA!!!!!\",\"typology\":\"CHALET\",\"houseSize\":300,\"gardenSize\":0,\"pictures\":[]}");
	}
	
	@Test
	public void oneShouldReturnAnnouncementNotFound() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/announcements/11", String.class)).contains(
				"Could not find announcement 11");
	}
	
	@Test
	public void allIrrelevantsShouldReturnAllIrrelevants() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/admin/announcements", String.class)).contains(
				"[{\"id\":1,\"score\":15,\"description\":\"Este piso es una ganga, compra, compra, COMPRA!!!!!\",\"typology\":\"CHALET\",\"houseSize\":300,\"gardenSize\":0,\"pictures\":[]},{\"id\":3,\"score\":20,\"description\":\"\",\"typology\":\"CHALET\",\"houseSize\":210,\"gardenSize\":25,\"pictures\":[{\"id\":2,\"url\":\"https://www.idealista.com/pictures/2\",\"quality\":\"HD\"}]},{\"id\":7,\"score\":0,\"description\":\"Garage en el centro de Albacete\",\"typology\":\"GARAGE\",\"houseSize\":300,\"gardenSize\":0,\"pictures\":[]}]");
	}
	
	@Test
	public void allRelevantsShouldReturnOnlyRelevants() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:"+port+"/user/announcements", String.class)).contains(
				"[{\"id\":4,\"score\":100,\"description\":\"Ático céntrico muy luminoso y recién reformado, parece nuevo\",\"typology\":\"FLAT\",\"houseSize\":130,\"gardenSize\":0,\"pictures\":[{\"id\":5,\"url\":\"https://www.idealista.com/pictures/5\",\"quality\":\"SD\"}]},{\"id\":8,\"score\":85,\"description\":\"Maravilloso chalet situado en als afueras de un pequeño pueblo rural.El entorno es espectacular, las vistas magníficas. ¡Cómprelo ahora!\",\"typology\":\"CHALET\",\"houseSize\":150,\"gardenSize\":20,\"pictures\":[{\"id\":1,\"url\":\"https://www.idealista.com/pictures/1\",\"quality\":\"SD\"},{\"id\":7,\"url\":\"https://www.idealista.com/pictures/7\",\"quality\":\"SD\"}]},{\"id\":2,\"score\":75,\"description\":\"Nuevo ático céntrico recién reformado. No deje pasar la oportunidad y adquiera este ático de lujo\",\"typology\":\"FLAT\",\"houseSize\":0,\"gardenSize\":0,\"pictures\":[{\"id\":4,\"url\":\"https://www.idealista.com/pictures/4\",\"quality\":\"HD\"}]},{\"id\":5,\"score\":75,\"description\":\"Pisazo\",\"typology\":\"FLAT\",\"houseSize\":130,\"gardenSize\":0,\"pictures\":[{\"id\":3,\"url\":\"https://www.idealista.com/pictures/3\",\"quality\":\"SD\"},{\"id\":4,\"url\":\"https://www.idealista.com/pictures/4\",\"quality\":\"HD\"}]},{\"id\":6,\"score\":50,\"description\":\"\",\"typology\":\"GARAGE\",\"houseSize\":0,\"gardenSize\":0,\"pictures\":[{\"id\":6,\"url\":\"https://www.idealista.com/pictures/6\",\"quality\":\"SD\"}]}]");
	}

}