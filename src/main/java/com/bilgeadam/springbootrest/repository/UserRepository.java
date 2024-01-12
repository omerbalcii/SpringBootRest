package com.bilgeadam.springbootrest.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import com.bilgeadam.springbootrest.model.Role;
import com.bilgeadam.springbootrest.model.SystemUser;

@Repository
public class UserRepository
{
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public UserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public SystemUser getByUserName(String username)
	{
		String sql = "select * from \"public\".\"users\" where \"username\" = :USERNAME";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USERNAME", username);
		return namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(SystemUser.class));
	}

	public List<GrantedAuthority> getUserRoles(String username)
	{
		String sql = "SELECT \"authority\" FROM \"public\".\"authorities\" where \"username\" = :USERNAME";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("USERNAME", username);
		// beanpropertymapper kullanıyorduk ama burada gerek yok çünkü tek sütun select
		// yapıyorum
		List<String> liste = namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
		List<GrantedAuthority> roles = new ArrayList<>();
		for (String role : liste)
		{
			roles.add(new Role(role));
		}
		return roles;
	}
}