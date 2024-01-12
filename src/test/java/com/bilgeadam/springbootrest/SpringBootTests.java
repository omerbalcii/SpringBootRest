package com.bilgeadam.springbootrest;

import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import com.bilgeadam.springbootrest.model.Ogretmen;

// starter test gerekiyor :)
// starter da hem junit hem assertj hem mockito var hepsini düşünmüşler
// junit.jupiter görüyorsanız junit 5 versiyonudur
// eskisinde yani verison 4 te org.junit paket ismi
// burada DEFINED_PORT vermezsem uygualama sanırım başka bir porttan başlıyor random
// properties 'te ne yazıyorsa oradan başla diyoruz yani
// dirtiescontext ile test bitince context temizlenir ve spring boot kapatılır
@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DisplayName(value = "Bütün entegrasyon testleri")
// test sırası önemli ise metodların tepesinde @org.junit.jupiter.api.Order yazarak sıra verebilmek için
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SpringBootTests
{
	// exception vermeyen testler başarılı sayılıyor
	// istediğiniz kadar testi alt alta yazabilirsiniz
	// ayrı ayrı class lara da yazabilirsiniz, maven hepsini bulur ve çalıştırır
	// ama mavena gelmeden kendim de run as junittest yapabilirim
	// -----------------------------
	// şimdi test kısmını bütün uygulama ayakta olduğu için resttemplate ile yapabilirim
	// spring bootta testresttemplate diye bir class da var
	// veya mockmvc kullanabilirim, ama bu gereksiz sanırım bu aşamada
	// -----------------------------

	@Test
	// junit 4 ile isim verilemiyor olabilir
	@DisplayName(value = "ogretmen getbyid testi") // dikkat junit 5 ve sonrası için
	@Order(value = 2)
	void restteamplatetest()
	{
		// bu kodu yazmak kolaydı, şimdi bunun gibi bir kod için assessment lar yazmam gerekiyor
		// getbyid için yapayım o daha kolay olacak
		RestTemplate template = new RestTemplate();
		String url = "http://127.0.0.1:8080/ogretmen/getbyid/{id}";
		HashMap<String, Object> uriVariables = new HashMap<String, Object>();
		// bütün öğretmenleri test etmek isterseniz döngü kurup dönebilirsiniz
		// ama elinizde onları da tutmanız lazım aşağıda assert yazabilmek için
		// unittest bu yüzden fazla abartılıyor, kimse 1000 tane test yazamayacak çünkü
		// hocam nereden biliyorsun 999. öğretmenin düzgün geleceğini?
		// bilmyorum tabi ki napayım nasıl bileyim ki :D
		// test yazıyom kendimi korumaya alıyorum sadece
		uriVariables.put("id", 2);
		ResponseEntity<Ogretmen> response = template.getForEntity(url, Ogretmen.class, uriVariables);
		// gelen body ile benim verdiğim öğretmen equal olmalı
		// burada equals metodunun içeriğe bakması lazım yani override edilmesi lazım
		// junit assertions olacak junit 5 ile gelmiş bu da
		// Assertions.assertEquals(object, object, string) olan
		Assertions.assertEquals(response.getBody(), new Ogretmen(2, "hasan", true), "gelen öğretmen datası yanlış !!!");
	}
}