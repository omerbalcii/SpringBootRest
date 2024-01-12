package com.bilgeadam.springbootrest.controller;

import java.util.List;

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

import com.bilgeadam.springbootrest.model.Konu;
import com.bilgeadam.springbootrest.repository.KonuRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(path = "konu")
@io.swagger.v3.oas.annotations.tags.Tag(description = "Konu endpointleri", name = "konu")
public class KonuController
{
	private KonuRepository konuRepository;

	public KonuController(KonuRepository konuRepository)
	{
		this.konuRepository = konuRepository;
	}

	@PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Konu konu)
	{
		// localhost:8080/konu/save
		try
		{
			boolean result = konuRepository.save(konu);
			if (result)
			{
				return ResponseEntity.status(HttpStatus.CREATED).body("Kayıt başarı ile kaydedildi");
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

	@Operation(parameters = @Parameter(name = "id", description = "istenen id"), description = "Bulunursa 200 bulunamazsa 404", summary = "ID ile getir")
	@ApiResponses(value =
	{ @ApiResponse(responseCode = "200", description = "Bulunursa"), @ApiResponse(responseCode = "404", description = "Bulunamazsa", content = @Content(examples = @ExampleObject(value = "burada body olmaz"))) })
	@GetMapping(path = "getbyidheader", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Konu> getbyidheader(@RequestHeader(name = "id") long id)
	{
		// localhost:8080/Konu/getbyidheader
		try
		{
			return ResponseEntity.ok(konuRepository.getByID(id));
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
	public ResponseEntity<Konu> getbyidqueryparam(@RequestParam(name = "id") long id)
	{
		// localhost:8080/Konu/getbyidqueryparam?id=1
		try
		{
			return ResponseEntity.ok(konuRepository.getByID(id));
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
	public ResponseEntity<Konu> getbyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/Konu/getbyid/1
		try
		{
			return ResponseEntity.ok(konuRepository.getByID(id));
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

	@io.swagger.v3.oas.annotations.Hidden
	@DeleteMapping(path = "deletebyid/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deletebyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/Konu/deletebyid/1
		try
		{
			boolean result = konuRepository.deleteByID(id);
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
	public ResponseEntity<List<Konu>> hellospring()
	{
		// localhost:8080/Konu/getall
		try
		{
			return ResponseEntity.ok(konuRepository.getAll());
		}
		catch (Exception e)
		{
			// daha sonra değişecek exception handling olacak
			System.err.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}
