package com.bilgeadam.springbootrest.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.springbootrest.model.Ogrenci;

@Repository
public class OgrenciRepository
{
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<Ogrenci> getAll()
	{
		// Incorrect column count: expected 1, actual 3
		// return jdbcTemplate.queryForList("select * from OGRENCİ",
		// Ogrenci.class);
		// -------------------------

//		RowMapper<Ogrenci> rowMapper = new RowMapper<Ogrenci>()
//		{
//			@Override
//			public Ogrenci mapRow(ResultSet result, int rowNum) throws SQLException
//			{
//				return new Ogrenci(result.getInt("ID"), result.getString("NAME"), result.getLong("OGR_NUMBER"),result.getLong("YEAR"));
//			}
//		};
//		return jdbcTemplate.query("select * from Ogrenci", rowMapper);

		// -------------------------
		// return jdbcTemplate.query("select * from Ogrenci", (ResultSet result,
		// int rowNum) -> new Ogrenci(result.getInt("ID"),
		// result.getString("NAME"),result.getLong("OGR_NUMBER"),result.getLong("YEAR"));
		// -------------------------
		return jdbcTemplate.query("select * from \"public\".\"OGRENCI\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Ogrenci.class));
	}

	public boolean deleteByID(long id)
	{
		String sql = "delete from \"public\".\"OGRENCI\" where \"ID\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public Ogrenci getByID(long id)
	{
		Ogrenci ogrenci = null;
		String sql = "select * from \"public\".\"OGRENCI\" where \"ID\" = :ABUZIDDIN";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ABUZIDDIN", id);
		ogrenci = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogrenci.class));
		// ---------------------------------
		/*
		 * ResultSetExtractor<Ogrenci> rse = new ResultSetExtractor<Ogrenci>() {
		 * 
		 * @Override public Ogrenci extractData(ResultSet result) throws SQLException,
		 * DataAccessException { return new Ogrenci(result.getInt("ID"),
		 * result.getString("NAME"),
		 * result.getLong("OGR_NUMBER"),result.getLong("YEAR")); } };
		 * namedParameterJdbcTemplate.query(sql, paramMap, rse);
		 */
		// ---------------------------------
		// Incorrect column count: expected 1, actual 3
		// namedParameterJdbcTemplate.queryForObject(sql, paramMap, Ogrenci.class);
		return ogrenci;
	}

	public boolean save(Ogrenci ogrn)
	{
		String sql = "insert into \"public\".\"OGRENCI\" (\"NAME\", \"OGR_NUMBER\", \"YEAR\") values (:NAME, :NUMBER, :YEAR)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", ogrn.getNAME());
		paramMap.put("NUMBER", ogrn.getOGR_NUMBER());
		paramMap.put("YEAR", ogrn.getYEAR());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public List<Ogrenci> getAllLike(String name)
	{
		String sql = "select * from \"public\".\"OGRENCI\" where \"NAME\" LIKE :NAME";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", "%" + name + "%"); // % işareti parameter içersinde olacak
		return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogrenci.class));
	}
}
