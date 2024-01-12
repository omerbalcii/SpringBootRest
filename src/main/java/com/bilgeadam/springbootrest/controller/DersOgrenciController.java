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

import com.bilgeadam.springbootrest.model.DersOgrenci;
import com.bilgeadam.springbootrest.model.DersOgrenciDTO;
import com.bilgeadam.springbootrest.repository.DersOgrenciRepository;

@RestController
@RequestMapping(path = "dersogrenci")
public class DersOgrenciController
{
	private DersOgrenciRepository dersOgrenciRepository;

	public DersOgrenciController(DersOgrenciRepository dersOgrenciRepository)
	{
		this.dersOgrenciRepository = dersOgrenciRepository;
	}

	@PostMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> update(@RequestBody DersOgrenci dogrn)
	{
		// localhost:8080/dersogrenci/update
		try
		{
			boolean result = dersOgrenciRepository.update(dogrn);
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

	@PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> save(@RequestBody DersOgrenci dogrn)

	{
		// localhost:8080/dersogrenci/save
		try
		{
			boolean result = dersOgrenciRepository.save(dogrn);
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
	public ResponseEntity<DersOgrenci> getbyidheader(@RequestHeader(name = "id") long id)
	{
		// localhost:8080/dersogrenci/getbyidheader
		try
		{
			return ResponseEntity.ok(dersOgrenciRepository.getByID(id));
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
	public ResponseEntity<DersOgrenci> getbyidqueryparam(@RequestParam(name = "id") long id)
	{
		// localhost:8080/dersogrenci/getbyidqueryparam?id=1
		try
		{
			return ResponseEntity.ok(dersOgrenciRepository.getByID(id));
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
	public ResponseEntity<DersOgrenciDTO> getbyidDTO(@PathVariable(name = "id") long id)
	{
		// localhost:8080/dersogrenci/getbyid/dto/1
		try
		{
			return ResponseEntity.ok(dersOgrenciRepository.getByIdDTO(id));
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
	public ResponseEntity<DersOgrenci> getbyid(@PathVariable(name = "id") long id)
	{
		// localhost:8080/dersogrenci/getbyid/1
		try
		{
			return ResponseEntity.ok(dersOgrenciRepository.getByID(id));
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
		// localhost:8080/dersogrenci/deletebyid/1
		try
		{
			boolean result = dersOgrenciRepository.deleteByID(id);
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
	public ResponseEntity<List<DersOgrenci>> getall()
	{
		// localhost:8080/dersogrenci/getall
		try
		{
			return ResponseEntity.ok(dersOgrenciRepository.getAll());
		}
		catch (Exception e)
		{
			// daha sonra değişecek exception handling olacak
			System.err.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}

	@GetMapping(path = "getalldto", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DersOgrenciDTO>> getalldto()
	{
		// localhost:8080/dersogrenci/getalldto
		try
		{
			return ResponseEntity.ok(dersOgrenciRepository.getAllDTO());
		}
		catch (Exception e)
		{
			// daha sonra değişecek exception handling olacak
			System.err.println(e.getMessage());
			return ResponseEntity.internalServerError().build();
		}
	}
}
