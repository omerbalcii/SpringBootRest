package com.bilgeadam.springbootrest.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.springbootrest.model.Konu;

@Repository
//@Lazy // veya application properties 'te
public class KonuRepository
{
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<Konu> getAll()
	{
		// Incorrect column count: expected 1, actual 3
		// return jdbcTemplate.queryForList("select * from Konu",
		// Konu.class);
		// -------------------------

//		RowMapper<Konu> rowMapper = new RowMapper<Konu>()
//		{
//			@Override
//			public Konu mapRow(ResultSet result, int rowNum) throws SQLException
//			{
//				return new Konu(result.getInt("ID"), result.getString("NAME"), result.getBoolean("IS_GICIK"));
//			}
//		};
//		return jdbcTemplate.query("select * from Konu", rowMapper);

		// -------------------------
		// return jdbcTemplate.query("select * from Konu", (ResultSet result,
		// int rowNum) -> new Konu(result.getInt("ID"), result.getString("NAME"));
		// -------------------------
		return jdbcTemplate.query("select * from \"public\".\"KONU\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Konu.class));
	}

	public boolean deleteByID(long id)
	{
		String sql = "delete from \"public\".\"KONU\" where \"ID\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public Konu getByID(long id)
	{
		Konu konu = null;
		String sql = "select * from \"public\".\"KONU\" where \"ID\" = :ABUZIDDIN";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ABUZIDDIN", id);
		konu = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Konu.class));
		// ---------------------------------
		/*
		 * ResultSetExtractor<Konu> rse = new ResultSetExtractor<Konu>() {
		 * 
		 * @Override public Konu extractData(ResultSet result) throws SQLException,
		 * DataAccessException { return new Konu(result.getInt("ID"),
		 * result.getString("NAME"),
		 * result.getLong("OGR_NUMBER"),result.getLong("YEAR")); } };
		 * namedParameterJdbcTemplate.query(sql, paramMap, rse);
		 */
		// ---------------------------------
		// Incorrect column count: expected 1, actual 3
		// namedParameterJdbcTemplate.queryForObject(sql, paramMap, Konu.class);
		return konu;
	}

	public boolean save(Konu konu)
	{
		String sql = "insert into \"public\".\"KONU\" (\"NAME\") values (:NAME)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", konu.getNAME());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public List<Konu> getAllLike(String name)
	{
		String sql = "select * from \"public\".\"KONU\" where \"NAME\" LIKE :NAME";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", "%" + name + "%"); // % işareti parameter içersinde olacak
		return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Konu.class));
	}
}
