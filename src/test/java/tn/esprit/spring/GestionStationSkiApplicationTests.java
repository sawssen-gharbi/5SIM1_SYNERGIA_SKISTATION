package tn.esprit.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.spring.repositories.IPisteRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class GestionStationSkiApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	IPisteRepository pisteRepository;
	IPisteRepository p = Mockito.mock(IPisteRepository.class);

}
