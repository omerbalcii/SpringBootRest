package com.bilgeadam.springbootrest;

import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import com.bilgeadam.springbootrest.model.Ogretmen;
import com.bilgeadam.springbootrest.repository.OgretmenRepository;

@JdbcTest
// imoorta ihtiyacım var, repo 'larımı nedense ayağa kaldırmamış
@Import(OgretmenRepository.class)
// @DataJpaTest // hibernate ve jpa için
// testlerde otomatik embedded h2 database falan kullanıyorlar ama bizim kapı gibi postgre 'miz var
// bunu söylemezsem kafadan birşeytler yapıyor
// Failed to replace DataSource with an embedded database for tests hatası veriyor
// [15:02] Berke Gürel hocam ordaki @AutoConfigureTest gereksiz di mi @JdbcTest varken
// hayır değil çünkü default 'ta ezecek şekilde tanımlanmış yukarıdaki jdbctest içerisinde
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoryTests
{
	@Autowired
	public OgretmenRepository ogretmenRepository;

//	@Test
	// rollback olmazsa otomatik rollback yapıyor, default değeri true
//	@org.springframework.test.annotation.Rollback(value = false)
	public void junior_yazilimci_insert_testi_yazmis()
	{
		// bu test ile ilgili dikkatinizi çeken birşey var mı?
		// iştahlı junior yazılımcı hemen basmış insert testini ???
		// bir insert yaparsam true dönmesi lazım diye yazmış
		Ogretmen expected = new Ogretmen("deneme " + new Random().nextInt(10000), false);
		System.err.println(expected);
		boolean res = ogretmenRepository.save(expected);
		// gayet kurallı düzgün anlaşılır test hatta test edelim success
		Assertions.assertTrue(res, "Burası hata mesajı");
	}

//	@Test
	public void getbyid()
	{
		// buralarda mock lama falan yok gerçekten db 'ye gidiyoruz
		// başka test katmanı da yok sadece repo testi
		// en basic test bu aslında
		// db den gelene actual dedim
		Ogretmen actual = ogretmenRepository.getByID(6);
		// olması gerekene expected dedim
		Ogretmen expected = new Ogretmen(6, "oğuzhan", false);
		// hani bu 3 parametre istiyordu benden ???
		// sırası karışmasın, önce expected sonra actual
		Assertions.assertEquals(expected, actual);
	}
}