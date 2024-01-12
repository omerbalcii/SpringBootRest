package com.bilgeadam.springbootrest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bilgeadam.springbootrest.model.Ders;
import com.bilgeadam.springbootrest.repository.DersRepository;

@Service
public class DersService
{
	private DersRepository dersRepository;

	public List<Ders> getAll()
	{
		return new ArrayList<>();
	}
}