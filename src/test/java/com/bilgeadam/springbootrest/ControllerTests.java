package com.bilgeadam.springbootrest;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.bilgeadam.springbootrest.controller.OgrenciController;
import com.bilgeadam.springbootrest.model.Ogrenci;
import com.bilgeadam.springbootrest.repository.OgrenciRepository;

// sadece bir controller çalışsın da onu test edeyim isterseniz yöntem bu
// When using JUnit 4, this annotation should be used in combination with @RunWith(SpringRunner.class)
// adamlar direkt controllers diye yazmış zaten
@WebMvcTest(controllers = OgrenciController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
// sırf config sınıfını verdik diye bütün security den kaçabildik
// seconfig sınıfı new yapıldı bean olarak atıldı ve herşey permit oldu
// bu kadar yani başka bir konfigürasyona gerek kalmadı
//@Import(value = SeConfig.class)
public class ControllerTests
{
	// bu arada mockito diye bir kütühane var
	// yine mockmvc kullanıyorum çünkü bu controller testinde resttemplate kullanılmaz
	// çünkü uygulama ayağa kalkmaz, nereye istek atacaksın yani
	@Autowired
	public MockMvc mock;

	// bu sınıfa istek atılırsa mock servisi araya giriyor
	// muhtemelen proxy katmanı yapıyor arada
	// bu sınıf çalışmıyor kendisinden istediğimiz şeyi döndürüyor
	// aslında sizi kandırıyor ama zaten amacım bu repo nun çalışması değil ki
	@MockBean
	public OgrenciRepository ogrenciRepository;

//	@Test
	public void getOgrenciById() throws Exception
	{
		Ogrenci ogrenci = new Ogrenci("Zehra", 123, 1);
		// meşhur mockito kütüphanesi, diyor ki ogrenciRepository.getByID(1) çağırılırsa bana yukarıdaki öğrenciyi döndürmüş gibi yap
		// mock lamak yani taklit etmek oluyor bu, berkenin dediği belki de bu
		// biz zaten biliyoruz o katmana hiç gitmedik, gitmiş gibi yaptık !!!!
		// diğer örneklerini rahat rahat bulurusnuz
		Mockito.when(ogrenciRepository.getByID(1)).thenReturn(ogrenci);
		// gerisi aynı diğer testlerdeki gibi expectation yazmaya kalıyor
		// json ı yazmışım buraya kopya çekmek için, test leri kafadan yazamazsınız çünkü
		// {"name":"Zehra", "ogr_NUMBER":123, "year" : 1}
		RequestBuilder request = MockMvcRequestBuilders.get("/ogrenci/getbyid/1");
		// assertion gibi expectation yazabilirim
		ResultMatcher numberMatcher = MockMvcResultMatchers.jsonPath("$.ogr_NUMBER").value("123");
		ResultMatcher nameMatcher = MockMvcResultMatchers.jsonPath("$.name").value("Zehra");
		ResultMatcher yearMatcher = MockMvcResultMatchers.jsonPath("$.year").value("1");
		ResultMatcher okMatcer = MockMvcResultMatchers.status().isOk();
		mock.perform(request).andExpect(numberMatcher).andExpect(nameMatcher).andExpect(yearMatcher).andExpect(okMatcer);
	}
}
