package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificate gs=new GiftCertificate();
        gs.setId(rs.getInt("id"));
        gs.setName(rs.getString("name"));
        gs.setDuration(rs.getInt("duration"));
        gs.setPrice(rs.getBigDecimal("price"));
        gs.setCreateDate(rs.getTimestamp("create_date"));
        gs.setLastUpDateTime(rs.getTimestamp("last_update_date"));

        return gs;
    }
}
