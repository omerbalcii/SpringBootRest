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

import com.bilgeadam.springbootrest.model.Ders;
import com.bilgeadam.springbootrest.model.DersDTO;
import com.bilgeadam.springbootrest.repository.DersRepository;

@RestController
@RequestMapping(path = "ders")
public class DersController
{
	private DersRepository dersRepository;
	//	private DersService dersService;

	public DersController(DersRepository dersRepository)
	{
		this.dersRepository = dersRepository;
	}

	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody Ders ders)
	{
		// localhost:8080/ders/update
		try
		{
			boolean result = dersRepository.update(ders);
			if (result)
			{
				return ResponseEntity.ok("Kayıt başarı ile güncellendi");
			}
			else
			{
				return ResponseEntity.internalServerError().body("Kayıt başarı ile güncellendi");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("Kayıt başarı ile güncellendi");
		}
	}

	@PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody Ders ders)
	{
		// localhost:8080/ders/save
		try
		{
			boolean result = dersRepository.save(ders);
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
	public ResponseEntity<Ders> getbyidheader(@RequestHeader(name = "id") long id)
	{
		// localhost:8080/Ders/getbyidheader
		try
		{
			return ResponseEntity.ok(dersRepository.getByID(id));
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
	public ResponseEntity<Ders> getbyidqueryparam(@RequestParam(name = "id") long id)
	{
		// localhost:8080/Ders/getbyidqueryparam?id=1
		try
		{
			return ResponseEntity.ok(dersRepository.getByID(id));
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
	public ResponseEntity<Ders> getbyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/Ders/getbyid/1
		try
		{
			return ResponseEntity.ok(dersRepository.getByID(id));
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

	@GetMapping(path = "getbyid/dto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DersDTO> getbyidDTO(@PathVariable(name = "id") long id)
	{
		// localhost:8080/Ders/getbyid/dto/1
		try
		{
			return ResponseEntity.ok(dersRepository.getByIdDTO(id));
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
	public ResponseEntity<String> deletebyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/Ders/deletebyid/1
		try
		{
			boolean result = dersRepository.deleteByID(id);
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
	public ResponseEntity<List<Ders>> getall()
	{
		// localhost:8080/ders/getall
		try
		{
			return ResponseEntity.ok(dersRepository.getAll());
		}
		catch (Exception e)
		{
			// daha sonra değişecek exception handling olacak
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping(path = "getalldto", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DersDTO>> getalldto()
	{
		// localhost:8080/ders/getalldto
		try
		{
			return ResponseEntity.ok(dersRepository.getAllDTO());
		}
		catch (Exception e)
		{
			// daha sonra değişecek exception handling olacak
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}
