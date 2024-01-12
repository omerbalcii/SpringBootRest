package com.bilgeadam.springbootrest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@DisplayName(value = "Mockmvc testleri")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
// AutoConfigureMockMvc yazmazsam aşağıdaki mock değişkenini inject edemez !!
// unsatisfieddependencyexception alır
@AutoConfigureMockMvc
class MockMvcTests
{
	// MVC katmanında mock 'lamak için
	@Autowired
	MockMvc mock;

	@Test
	@DisplayName(value = "öğrenci getbyid testi")
	@Order(value = 2)
	void mockmvctest() throws Exception
	{
		// mock, junit ve spring el ele vermiş demiştim ya bütün sınıflar buralarda var
		// localhost yok dikkat ederseniz, bu direct controller 'a ulaşıyor, yada servlete
		RequestBuilder request = MockMvcRequestBuilders.get("/ogrenci/getbyid/1");
		// assertion gibi expectation yazabilirim, assert.equals falan yazmak yerine yani
		// json larda arama yapıyorum, büyük harf küçük harf önemli
		// yazım tarzı biraz garip ama jsonpath ile json araması yapan tek metod bu
		// aksi takdirde datayı alıp Jackson ile map 'lemem gerekir
		ResultMatcher numberMatcher = MockMvcResultMatchers.jsonPath("$.ogr_NUMBER").value("123");
		ResultMatcher nameMatcher = MockMvcResultMatchers.jsonPath("$.name").value("zehra");
		ResultMatcher yearMatcher = MockMvcResultMatchers.jsonPath("$.year").value("1");
		// ResultMatcher ile reponse status kodu da 200 olması lazım bu koşulda
		// mesela 404 'ler için de test yazmak gerkeir işte bussiness öyle çünkü
		// yine dediğim gibi önemli olan business yani ben bu testi yazsam da diğer case 'ler kalacak
		ResultMatcher okMatcer = MockMvcResultMatchers.status().isOk();
		mock.perform(request).andExpect(numberMatcher).andExpect(nameMatcher).andExpect(yearMatcher).andExpect(okMatcer);
		// 4 adet expect ifadesinin hepsi true olmak zorunda, yoksa rest işlemi hatalıdır
	}

	void test()
	{
	}
}