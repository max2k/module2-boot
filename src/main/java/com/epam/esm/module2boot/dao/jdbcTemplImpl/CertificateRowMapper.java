package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateRowMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {

        GiftCertificate gs= GiftCertificate.builder().build();
        gs.setId(rs.getInt("id"));
        gs.setDescription(rs.getString("description"));
        gs.setName(rs.getString("name"));
        gs.setDuration(rs.getInt("duration"));
        gs.setPrice(rs.getBigDecimal("price"));
        gs.setCreateDate(rs.getTimestamp("create_date"));
        gs.setLastUpdateDate(rs.getTimestamp("last_update_date"));

        return gs;
    }
}
