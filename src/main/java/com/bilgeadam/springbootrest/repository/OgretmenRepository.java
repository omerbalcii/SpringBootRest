package com.bilgeadam.springbootrest.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bilgeadam.springbootrest.model.Ogretmen;

// CrudRepository
@Repository
//@Profile(value = "prod")
public class OgretmenRepository
{
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<Ogretmen> getAll()
	{
		// Incorrect column count: expected 1, actual 3
		// return jdbcTemplate.queryForList("select * from BILGE.OGRETMEN",
		// Ogretmen.class);
		// -------------------------
		//		RowMapper<Ogretmen> rowMapper = new RowMapper<Ogretmen>()
		//		{
		//			@Override
		//			public Ogretmen mapRow(ResultSet result, int rowNum) throws SQLException
		//			{
		//				return new Ogretmen(result.getInt("ID"), result.getString("NAME"), result.getBoolean("IS_GICIK"));
		//			}
		//		};
		//		return jdbcTemplate.query("select * from BILGE.OGRETMEN", rowMapper);

		// -------------------------
		// return jdbcTemplate.query("select * from BILGE.OGRETMEN", (ResultSet result,
		// int rowNum) -> new Ogretmen(result.getInt("ID"), result.getString("NAME"),
		// result.getBoolean("IS_GICIK")));
		// -------------------------
		return jdbcTemplate.query("select * from \"public\".\"OGRETMEN\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Ogretmen.class));
	}

	public boolean deleteByID(long id)
	{
		String sql = "delete from \"public\".\"OGRETMEN\" where \"ID\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public Ogretmen getByID(long id)
	{
		Ogretmen ogretmen = null;
		String sql = "select * from \"public\".\"OGRETMEN\" where \"ID\" = :ABUZIDDIN";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ABUZIDDIN", id);
		ogretmen = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogretmen.class));
		// ---------------------------------
		/*
		 * ResultSetExtractor<Ogretmen> rse = new ResultSetExtractor<Ogretmen>() {
		 *
		 * @Override public Ogretmen extractData(ResultSet result) throws SQLException, DataAccessException { return new Ogretmen(result.getInt("ID"), result.getString("NAME"), result.getBoolean("IS_GICIK")); } }; namedParameterJdbcTemplate.query(sql, paramMap, rse);
		 */
		// ---------------------------------
		// Incorrect column count: expected 1, actual 3
		// namedParameterJdbcTemplate.queryForObject(sql, paramMap, Ogretmen.class);
		return ogretmen;
	}

	public boolean save(Ogretmen ogr)
	{
		String sql = "insert into \"public\".\"OGRETMEN\" (\"NAME\", \"IS_GICIK\") values (:NAME, :GICIK)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", ogr.getNAME());
		paramMap.put("GICIK", ogr.isIS_GICIK());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public List<Ogretmen> getAllLike(String name)
	{
		String sql = "select * from \"public\".\"OGRETMEN\" where \"NAME\" LIKE :NAME";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", "%" + name + "%"); // % işareti parameter içersinde olacak
		return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Ogretmen.class));
	}

	public boolean update(Ogretmen ogr)
	{
		String sql = "UPDATE public.\"OGRETMEN\" SET \"NAME\"= :NAME, \"IS_GICIK\"=:IS_GICIK WHERE \"ID\"=:ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", ogr.getNAME());
		paramMap.put("IS_GICIK", ogr.isIS_GICIK());
		paramMap.put("ID", ogr.getID());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	//	public boolean update(long id, Ogretmen ogr) throws SQLException
	//	{
	//	}
}