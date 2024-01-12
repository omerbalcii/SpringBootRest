package com.bilgeadam.springbootrest.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.springbootrest.model.Ogretmen;
import com.bilgeadam.springbootrest.repository.OgretmenRepository;

@RestController
@RequestMapping(path = "ogretmen")
//@RestControllerAdvice(basePackageClasses = OgretmenRepository.class)
public class OgretmenController
{
	private OgretmenRepository ogretmenRepository;
	private final MessageSource messageSource;

	//	@ExceptionHandler(value = BadSqlGrammarException.class)
	//	public String badSqlGrammerExceptionHandler(BadSqlGrammarException e)
	//	{
	//		System.err.println("Bad sql yakalandı -> " + e.getMessage());
	//		return "bad sql hatası";
	//	}

	@ExceptionHandler(value = ArithmeticException.class)
	// bu mantıksız yöntem
	//	@ResponseStatus(code = HttpStatus.IM_USED, reason = "invalid jdbc usage")
	//	public String aritmetichandler(ArithmeticException e)
	public ResponseEntity<String> aritmetichandler(ArithmeticException e)
	{
		// server.error.include-message = always
		System.err.println(e.getMessage());
		// isterseniz responseentity döndürebilirsiniz
		return ResponseEntity.status(HttpStatus.IM_USED).body("yazılımcı kodu yanlış yazmış");
		// veya aşağıdaki gibi döndürülebilir
		//		return "yazılımcı kodu yanlış yazmış";
	}

	public OgretmenController(OgretmenRepository ogretmenRepository, ResourceBundleMessageSource messageSource)
	{
		this.ogretmenRepository = ogretmenRepository;
		// ResourceBundleMessageSource beanfactory de oluşturuldu
		this.messageSource = messageSource;
	}

	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody Ogretmen ogr)
	{
		// localhost:8080/ogretmen/update
		try
		{
			boolean result = ogretmenRepository.update(ogr);
			if (result)
			{
				return ResponseEntity.ok("Kayıt başarı ile güncellendi");
			}
			else
			{
				return ResponseEntity.internalServerError().body("Kayıt başarı ile güncellenemedi");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Kayıt başarı ile güncellenemedi");
		}
	}

	@PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Ogretmen ogr)
	{
		// localhost:8080/ogretmen/save
		try
		{
			boolean result = ogretmenRepository.save(ogr);
			if (result)
			{
				return ResponseEntity.ok("Kayıt başarı ile kaydedildi");
			}
			else
			{
				return ResponseEntity.internalServerError().body("Kayıt başarı ile kaydedilemedi");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Kayıt başarı ile kaydedilemedi");
		}
	}

	@GetMapping(path = "getbyidheader", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ogretmen> getbyidheader(@RequestHeader(name = "id") long id)
	{
		// localhost:8080/ogretmen/getbyidheader
		try
		{
			return ResponseEntity.ok(ogretmenRepository.getByID(id));
		}
		catch (EmptyResultDataAccessException e)
		{
			return ResponseEntity.notFound().build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping(path = "getbyidqueryparam", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ogretmen> getbyidqueryparam(@RequestParam(name = "id") long id)
	{
		// localhost:8080/ogretmen/getbyidqueryparam?id=1
		try
		{
			return ResponseEntity.ok(ogretmenRepository.getByID(id));
		}
		catch (EmptyResultDataAccessException e)
		{
			return ResponseEntity.notFound().build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping(path = "getbyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ogretmen> getbyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/ogretmen/getbyid/1
		// böyle intr yapılmaz, bütün servislerde çalışacak şekilde yazamam
		// ama bütün servislerde çalışacak bir işlemi Spring yapabilir dimi?
		//		if (lang == "tr")
		//		{
		//		}
		//		else if (lang == "en")
		//		{
		//		}
		try
		{
			return ResponseEntity.ok(ogretmenRepository.getByID(id));
		}
		catch (EmptyResultDataAccessException e)
		{
			return ResponseEntity.notFound().build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping(path = "deletebyid/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deletebyid(Locale locale, @PathVariable(name = "id") long id)
	{
		// localhost:8080/ogretmen/deletebyid/1
		Object[] params = new Object[1];
		params[0] = id;
		try
		{
			boolean result = ogretmenRepository.deleteByID(id);
			if (result)
			{
				return ResponseEntity.status(HttpStatus.OK).body(messageSource.getMessage("ogretmen.delete.success", params, locale));
			}
			else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("ogretmen.delete.notfound", params, locale));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageSource.getMessage("ogretmen.delete.error", params, locale));
		}
	}

	@GetMapping(path = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
	// aşağıdakiler security config 'te requestmatcher kuralı yazmak yerine
	// kullanılabilir
	//	@PreAuthorize(value = "isAuthenticated()")
	//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	//	@Secured(value = "ROLE_ADMIN")
	public ResponseEntity<List<Ogretmen>> getall()
	{
		// localhost:8080/ogretmen/getall
		//		try
		//		{
		//			int k = 7 / 0;
		//		}
		//		catch (Exception e)
		//		{
		//			e.printStackTrace();
		//		}
		//		try
		//		{
		return ResponseEntity.ok(ogretmenRepository.getAll());
		//		}
		//		catch (Exception e)
		//		{
		//			// daha sonra değişecek exception handling olacak
		//			System.err.println(e.getMessage());
		//			return ResponseEntity.internalServerError().build();
		//		}
	}
}
