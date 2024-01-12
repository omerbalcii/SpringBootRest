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

import com.bilgeadam.springbootrest.model.DersDTO;
import com.bilgeadam.springbootrest.model.DersOgrenci;
import com.bilgeadam.springbootrest.model.DersOgrenciDTO;
import com.bilgeadam.springbootrest.model.Konu;
import com.bilgeadam.springbootrest.model.Ogrenci;
import com.bilgeadam.springbootrest.model.Ogretmen;

@Repository
public class DersOgrenciRepository
{
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public List<DersOgrenci> getAll()
	{
		// Incorrect column count: expected 1, actual 3
		// return jdbcTemplate.queryForList("select * from BILGE.DersOgrenci",
		// DersOgrenci.class);
		// -------------------------

		//		RowMapper<DersOgrenci> rowMapper = new RowMapper<DersOgrenci>()
		//		{
		//			@Override
		//			public DersOgrenci mapRow(ResultSet result, int rowNum) throws SQLException
		//			{
		//				return new DersOgrenci(result.getInt("ID"), result.getString("NAME"), result.getBoolean("IS_GICIK"));
		//			}
		//		};
		//		return jdbcTemplate.query("select * from BILGE.DersOgrenci", rowMapper);

		// -------------------------
		// return jdbcTemplate.query("select * from DersOgrenci", (ResultSet result,
		// int rowNum) -> new DersOgrenci(result.getInt("ID"),
		// result.getInt("DERS_ID"),result.getInt("OGRENCI_ID"),result.getLong("DEVAMSIZLIK"),result.getLong("YEAR")));
		// -------------------------
		return jdbcTemplate.query("select * from \"public\".\"DERS_OGRENCI\" order by \"ID\" asc", BeanPropertyRowMapper.newInstance(DersOgrenci.class));
	}

	public List<DersOgrenciDTO> getAllDTO()
	{
		String sql = """
				select "DERS_OGRENCI"."ID", "DERS_OGRENCI"."NOTE", "DERS_OGRENCI"."DEVAMSIZLIK",
				"OGRENCI"."ID" AS "OGRENCI_ID", "OGRENCI"."NAME" AS "OGRENCI_NAME", "OGRENCI"."YEAR" AS "OGRENCI_YEAR", "OGRENCI"."OGR_NUMBER" AS "OGRENCI_NUMBER",
				"DERS"."ID" AS "DERS_ID",
				"KONU"."ID" AS "KONU_ID", "KONU"."NAME" AS "KONU_NAME",
				"OGRETMEN"."ID" AS "OGRETMEN_ID", "OGRETMEN"."NAME" AS "OGRETMEN_NAME", "OGRETMEN"."IS_GICIK" AS "OGRETMEN_GICIK"
				from "DERS_OGRENCI"
				inner join "OGRENCI" ON "OGRENCI"."ID" = "DERS_OGRENCI"."OGRENCI_ID"
				inner join "DERS" ON "DERS"."ID" = "DERS_OGRENCI"."DERS_ID"
				inner join "OGRETMEN" ON "OGRETMEN"."ID" = "DERS"."OGRETMEN_ID"
				inner join "KONU" ON "KONU"."ID" = "DERS"."KONU_ID"
								""";
		RowMapper<DersOgrenciDTO> rowMapper = new RowMapper<DersOgrenciDTO>()
		{
			@Override
			public DersOgrenciDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				DersOgrenciDTO dersOgrenciDTO = new DersOgrenciDTO();
				dersOgrenciDTO.setId(rs.getLong("ID"));
				dersOgrenciDTO.setDEVAMSIZLIK(rs.getInt("DEVAMSIZLIK"));
				dersOgrenciDTO.setNOTE(rs.getInt("NOTE"));
				Ogrenci ogrenci = new Ogrenci(rs.getLong("OGRENCI_ID"), rs.getString("OGRENCI_NAME"), rs.getLong("OGRENCI_NUMBER"), rs.getLong("OGRENCI_YEAR"));
				dersOgrenciDTO.setOgrenci(ogrenci);
				Ogretmen ogretmen = new Ogretmen(rs.getLong("OGRETMEN_ID"), rs.getString("OGRETMEN_NAME"), rs.getBoolean("OGRETMEN_GICIK"));
				Konu konu = new Konu(rs.getLong("KONU_ID"), rs.getString("KONU_NAME"));
				DersDTO ders = new DersDTO(rs.getLong("DERS_ID"), ogretmen, konu);
				dersOgrenciDTO.setDers(ders);
				return dersOgrenciDTO;
			}
		};
		return namedParameterJdbcTemplate.query(sql, rowMapper);
	}

	public DersOgrenciDTO getByIdDTO(long id)
	{
		String sql = """
				select "DERS_OGRENCI"."ID", "DERS_OGRENCI"."NOTE", "DERS_OGRENCI"."DEVAMSIZLIK",
				"OGRENCI"."ID" AS "OGRENCI_ID", "OGRENCI"."NAME" AS "OGRENCI_NAME", "OGRENCI"."YEAR" AS "OGRENCI_YEAR", "OGRENCI"."OGR_NUMBER" AS "OGRENCI_NUMBER",
				"DERS"."ID" AS "DERS_ID",
				"KONU"."ID" AS "KONU_ID", "KONU"."NAME" AS "KONU_NAME",
				"OGRETMEN"."ID" AS "OGRETMEN_ID", "OGRETMEN"."NAME" AS "OGRETMEN_NAME", "OGRETMEN"."IS_GICIK" AS "OGRETMEN_GICIK"
				from "DERS_OGRENCI"
				inner join "OGRENCI" ON "OGRENCI"."ID" = "DERS_OGRENCI"."OGRENCI_ID"
				inner join "DERS" ON "DERS"."ID" = "DERS_OGRENCI"."DERS_ID"
				inner join "OGRETMEN" ON "OGRETMEN"."ID" = "DERS"."OGRETMEN_ID"
				inner join "KONU" ON "KONU"."ID" = "DERS"."KONU_ID"
				where "DERS_OGRENCI"."ID" = :DERS_OGRENCI_ID
								""";
		Map<String, Object> params = new HashMap<>();
		params.put("DERS_OGRENCI_ID", id);
		RowMapper<DersOgrenciDTO> rowMapper = new RowMapper<DersOgrenciDTO>()
		{
			@Override
			public DersOgrenciDTO mapRow(ResultSet rs, int rowNum) throws SQLException
			{
				DersOgrenciDTO dersOgrenciDTO = new DersOgrenciDTO();
				dersOgrenciDTO.setId(rs.getLong("ID"));
				dersOgrenciDTO.setDEVAMSIZLIK(rs.getInt("DEVAMSIZLIK"));
				dersOgrenciDTO.setNOTE(rs.getInt("NOTE"));
				Ogrenci ogrenci = new Ogrenci(rs.getLong("OGRENCI_ID"), rs.getString("OGRENCI_NAME"), rs.getLong("OGRENCI_NUMBER"), rs.getLong("OGRENCI_YEAR"));
				dersOgrenciDTO.setOgrenci(ogrenci);
				Ogretmen ogretmen = new Ogretmen(rs.getLong("OGRETMEN_ID"), rs.getString("OGRETMEN_NAME"), rs.getBoolean("OGRETMEN_GICIK"));
				Konu konu = new Konu(rs.getLong("KONU_ID"), rs.getString("KONU_NAME"));
				DersDTO ders = new DersDTO(rs.getLong("DERS_ID"), ogretmen, konu);
				dersOgrenciDTO.setDers(ders);
				return dersOgrenciDTO;
			}
		};
		return namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper);
	}

	public boolean deleteByID(long id)
	{
		String sql = "delete from \"public\".\"DERS_OGRENCI\" where \"ID\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ID", id);
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public DersOgrenci getByID(long id)
	{
		DersOgrenci dersOgrenci = null;
		String sql = "select * from \"public\".\"DERS_OGRENCI\" where \"ID\" = :ABUZIDDIN";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ABUZIDDIN", id);
		dersOgrenci = namedParameterJdbcTemplate.queryForObject(sql, paramMap, BeanPropertyRowMapper.newInstance(DersOgrenci.class));
		// ---------------------------------
		/*
		 * ResultSetExtractor<DersOgrenci> rse = new ResultSetExtractor<DersOgrenci>() {
		 *
		 * @Override public DersOgrenci extractData(ResultSet result) throws
		 * SQLException, DataAccessException { return new
		 * DersOgrenci(result.getInt("ID"), result.getString("NAME"),
		 * result.getBoolean("IS_GICIK")); } }; namedParameterJdbcTemplate.query(sql,
		 * paramMap, rse);
		 */
		// ---------------------------------
		// Incorrect column count: expected 1, actual 3
		// namedParameterJdbcTemplate.queryForObject(sql, paramMap, DersOgrenci.class);
		return dersOgrenci;
	}

	public boolean save(DersOgrenci dogr)
	{
		String sql = "INSERT INTO \"public\".\"DERS_OGRENCI\"(\"DERS_ID\", \"OGRENCI_ID\", \"DEVAMSIZLIK\", \"NOTE\") VALUES  (:DERS_ID, :OGRENCI_ID, :DEVAMSIZLIK, :NOTE)";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("DERS_ID", dogr.getDERS_ID());
		paramMap.put("OGRENCI_ID", dogr.getOGRENCI_ID());
		paramMap.put("DEVAMSIZLIK", dogr.getDEVAMSIZLIK());
		paramMap.put("NOTE", dogr.getNOTE());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	public boolean update(DersOgrenci dogr)
	{
		String sql = "UPDATE public.\"DERS_OGRENCI\" SET \"ID\"= :ID, \"DERS_ID\"= :DERS_ID, \"OGRENCI_ID\"= :OGRENCI_ID, \"DEVAMSIZLIK\"= :DEVAMSIZLIK, \"NOTE\"= :NOTE WHERE \"ID\" = :ID";
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("DERS_ID", dogr.getDERS_ID());
		paramMap.put("OGRENCI_ID", dogr.getOGRENCI_ID());
		paramMap.put("DEVAMSIZLIK", dogr.getDEVAMSIZLIK());
		paramMap.put("NOTE", dogr.getNOTE());
		paramMap.put("ID", dogr.getID());
		return namedParameterJdbcTemplate.update(sql, paramMap) == 1;
	}

	//	public boolean update(long id, DersOgrenci dogr) throws SQLException
	//	{
	//	}
}