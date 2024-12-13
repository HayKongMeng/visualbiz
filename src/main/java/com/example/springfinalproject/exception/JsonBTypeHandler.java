package com.example.springfinalproject.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonBTypeHandler extends BaseTypeHandler<Object> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJson(parameter));
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toObject(rs.getString(columnName));
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toObject(rs.getString(columnIndex));
    }

    @Override
    public Object getNullableResult(java.sql.CallableStatement cs, int columnIndex) throws SQLException {
        return toObject(cs.getString(columnIndex));
    }

    private String toJson(Object object) throws SQLException {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new SQLException("Failed to convert object to JSON string.", e);
        }
    }

    private Object toObject(String content) throws SQLException {
        if (content == null || content.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(content, Object.class);
        } catch (IOException e) {
            throw new SQLException("Failed to convert JSON string to object.", e);
        }
    }


}