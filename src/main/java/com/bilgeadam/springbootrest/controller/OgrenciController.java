package com.bilgeadam.springbootrest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.springbootrest.model.Ogrenci;
import com.bilgeadam.springbootrest.repository.OgrenciRepository;

@RestController
@RequestMapping(path = "ogrenci")
public class OgrenciController
{
	private OgrenciRepository ogrenciRepository;

	// bu yeterli ama yetmez muhtemelen :)
	//	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// yöntem 2, bu sınıfın logları konsolda OgrenciController diye görünsün
	// yoksa ben neredne bulayım o kadar log içerisinde
	private final Logger logger = LoggerFactory.getLogger("OgrenciController");

	public OgrenciController(OgrenciRepository ogrenciRepository)
	{
		this.ogrenciRepository = ogrenciRepository;
	}

	@PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Ogrenci ogrn)

	{
		// localhost:8080/ogrenci/save
		try
		{
			boolean result = ogrenciRepository.save(ogrn);
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
	public ResponseEntity<Ogrenci> getbyidheader(@RequestHeader(name = "id") long id)
	{
		// localhost:8080/ogrenci/getbyidheader
		try
		{
			return ResponseEntity.ok(ogrenciRepository.getByID(id));
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
	public ResponseEntity<Ogrenci> getbyidqueryparam(@RequestParam(name = "id") long id)
	{
		// localhost:8080/Ogrenci/getbyidqueryparam?id=1
		try
		{
			return ResponseEntity.ok(ogrenciRepository.getByID(id));
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
	public ResponseEntity<Ogrenci> getbyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/Ogrenci/getbyid/1
		try
		{
			// özellikle ÖĞRENCİ diye belirtiyorum, şimdilik
			logger.trace("ÖĞRENCİ getbyid yapıldı");
			return ResponseEntity.ok(ogrenciRepository.getByID(id));
		}
		catch (EmptyResultDataAccessException e)
		{
			logger.warn("ÖĞRENCİ getbyid bulunamadı");
			return ResponseEntity.notFound().build();
		}
		catch (Exception e)
		{
			logger.error("ÖĞRENCİ getbyid exception");
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping(path = "deletebyid/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deletebyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/Ogrenci/deletebyid/1
		try
		{
			boolean result = ogrenciRepository.deleteByID(id);
			if (result)
			{
				return ResponseEntity.ok(id + "id 'li kayıt başarı ile silindi");
			}
			else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(id + "id 'li kayıt bulunamadı");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(id + "id 'li kayıt başarı ile silindi");
		}
	}

	@GetMapping(path = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ogrenci>> getall()
	{
		// localhost:8080/ogrenci/getall
		try
		{
			return ResponseEntity.ok(ogrenciRepository.getAll());
		}
		catch (Exception e)
		{
			// daha sonra değişecek exception handling olacak
			System.err.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}
