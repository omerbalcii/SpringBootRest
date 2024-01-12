package com.bilgeadam.springbootrest.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bilgeadam.springbootrest.model.Ders;
import com.bilgeadam.springbootrest.model.DersDTO;
import com.bilgeadam.springbootrest.model.Konu;
import com.bilgeadam.springbootrest.model.Ogretmen;

@Repository
public class DersRepository
{
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}

	@Autowired
	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<Ders> getAll()
	{
		// Incorrect column count: expected 1, actual 3
		// return jdbcTemplate.queryForList("select * from BILGE.Ders",
		// Ders.class);
		// -------------------------

		//		RowMapper<Ders> rowMapper = new RowMapper<Ders>()
		//		{
		//			@Override
		//			public Ders mapRow(ResultSet result, int rowNum) throws SQLException
		//			{
		//				return new Ders(result.getInt("ID"), result.getString("NAME"), result.getBoolean("IS_GICIK"));
		//			}
		//		};
		//		return jdbcTemplate.query("select * from DERS", rowMapper);

		// -------------------------
		// return jdbcTemplate.query("select * from Ders", (ResultSet result,
		// int rowNum) -> new DERS(result.getInt("ID"),
		// result.getInt("OGRETMEN_ID"),result.getInt("KONU_ID)));
		// -------------------------
		return jdbcTemplate.query("select * from \"public\".\"DERS\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(Ders.class));
	}

	public boolean deleteByID(long id)
	{
		String sql = "delete from \"public\".\"DERS\" where \"ID\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public Ders getByID(long id)
	{
		Ders ders = null;
		String sql = "select * from \"public\".\"DERS\" where \"ID\" = :ABUZIDDIN";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ABUZIDDIN", id);
		ders = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(Ders.class));
		// ---------------------------------
		/*
		 * ResultSetExtractor<Ders> ders = new ResultSetExtractor<Ders>() {
		 *
		 * @Override public Ders extractData(ResultSet result) throws SQLException,
		 * DataAccessException { return new Ders(result.getInt("ID"),
		 * result.getString("NAME"), result.getBoolean("IS_GICIK")); } };
		 * namedParameterJdbcTemplate.query(sql, paramMap, rse);
		 */
		// ---------------------------------
		// Incorrect column count: expected 1, actual 3
		// namedParameterJdbcTemplate.queryForObject(sql, paramMap, Ders.class);
		return ders;
	}

	public boolean save(Ders ders)
	{
		String sql = "insert into \"public\".\"DERS\" (\"OGRETMEN_ID\", \"KONU_ID\") values (:OGRETMENID, :KONUID)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("OGRETMENID", ders.getOGRETMEN_ID());
		paramMap.put("KONUID", ders.getKONU_ID());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	@Transactional
	// içeride try catch olmayacak, bu sayede rollback yapıyor
	public boolean save(Ogretmen ogretmen, Konu konu)
	{
		String sql = "insert into \"public\".\"OGRETMEN\" (\"NAME\", \"IS_GICIK\") values (:NAME, :GICIK)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("NAME", ogretmen.getNAME());
		paramMap.put("GICIK", ogretmen.isIS_GICIK());
		namedParameterJdbcTemplate.update(sql, paramMap);
		sql = "insert into \"public\".\"KONU\" (\"NAME\") values (:NAME)";
		paramMap = new HashMap<>();
		paramMap.put("NAME", konu.getNAME());
		namedParameterJdbcTemplate.update(sql, paramMap);
		sql = "insert into \"public\".\"DERS\" (\"OGRETMEN_ID\", \"KONU_ID\") values (:OGRETMENID, :KONUID)";
		paramMap = new HashMap<>();
		paramMap.put("OGRETMENID", 112233);
		paramMap.put("KONUID", 112233);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public List<DersDTO> getAllDTO()
	{
		RowMapper<DersDTO> mapper = new RowMapper<DersDTO>()
		{
			@Override
			public DersDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				DersDTO dersDTO = new DersDTO();
				Ogretmen ogretmen = new Ogretmen();
				Konu konu = new Konu();
				ogretmen.setID(rs.getLong("OGRETMEN_ID"));
				ogretmen.setNAME(rs.getString("OGR_NAME"));
				ogretmen.setIS_GICIK(rs.getBoolean("IS_GICIK"));
				konu.setID(rs.getLong("KONU_ID"));
				konu.setNAME(rs.getString("KONU_NAME"));
				dersDTO.setId(rs.getLong("ID"));
				dersDTO.setOgretmen(ogretmen);
				dersDTO.setKonu(konu);
				return dersDTO;
			}
		};
		return jdbcTemplate.query("select \"DERS\".\"ID\", \"OGRETMEN\".\"ID\" AS \"OGRETMEN_ID\", \"OGRETMEN\".\"NAME\" AS \"OGR_NAME\", \"OGRETMEN\".\"IS_GICIK\", \"KONU\".\"ID\" AS \"KONU_ID\", \"KONU\".\"NAME\" AS \"KONU_NAME\" from \"public\".\"DERS\" inner join \"public\".\"OGRETMEN\" ON \"OGRETMEN\".\"ID\" = \"DERS\".\"OGRETMEN_ID\" inner join \"public\".\"KONU\" ON \"KONU\".\"ID\" = \"DERS\".\"KONU_ID\"", mapper);
	}

	public DersDTO getByIdDTO(long id)
	{
		RowMapper<DersDTO> mapper = new RowMapper<DersDTO>()
		{
			@Override
			public DersDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				DersDTO dersDTO = new DersDTO();
				Ogretmen ogretmen = new Ogretmen();
				Konu konu = new Konu();
				ogretmen.setID(rs.getLong("OGRETMEN_ID"));
				ogretmen.setNAME(rs.getString("OGR_NAME"));
				ogretmen.setIS_GICIK(rs.getBoolean("IS_GICIK"));
				konu.setID(rs.getLong("KONU_ID"));
				konu.setNAME(rs.getString("KONU_NAME"));
				dersDTO.setId(rs.getLong("ID"));
				dersDTO.setOgretmen(ogretmen);
				dersDTO.setKonu(konu);
				return dersDTO;
			}
		};
		Map<String, Object> params = new HashMap<>();
		params.put("ID", id);
		return namedParameterJdbcTemplate.queryForObject("select \"DERS\".\"ID\", \"OGRETMEN\".\"ID\" AS \"OGRETMEN_ID\", \"OGRETMEN\".\"NAME\" AS \"OGR_NAME\", \"OGRETMEN\".\"IS_GICIK\", \"KONU\".\"ID\" AS \"KONU_ID\", \"KONU\".\"NAME\" AS \"KONU_NAME\" from \"public\".\"DERS\" inner join \"public\".\"OGRETMEN\" ON \"OGRETMEN\".\"ID\" = \"DERS\".\"OGRETMEN_ID\" inner join \"public\".\"KONU\" ON \"KONU\".\"ID\" = \"DERS\".\"KONU_ID\" where \"DERS\".\"ID\" = :ID", params, mapper);
	}

	public boolean update(Ders ders)
	{
		String sql = "UPDATE public.\"DERS\" SET \"OGRETMEN_ID\" = :OGRETMEN_ID, \"KONU_ID\" = :KONU_ID WHERE \"ID\" = :ID;";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("OGRETMEN_ID", ders.getOGRETMEN_ID());
		paramMap.put("KONU_ID", ders.getKONU_ID());
		paramMap.put("ID", ders.getID());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	//	public List<Ders> getAllLike(String name)
	//	{
	//		String sql = "select * from \"public\".\"DERS\" where \"NAME\" LIKE :NAME";
	//		Map<String, Object> paramMap = new HashMap<>();
	//		paramMap.put("NAME", "%" + name + "%"); // % işareti parameter içersinde olacak
	//		return namedParameterJdbcTemplate.query(sql, paramMap, BeanPropertyRowMapper.newInstance(Ders.class));
	//	}

	//	public boolean update(long id, Ders ders) throws SQLException
	//	{
	//	}
}