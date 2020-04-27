package com.sorm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @Author yzh
 * @Date 2020/4/27 19:50
 * @Version 1.0
 */
public interface CallBack {
    Object doExecute(Connection conn, PreparedStatement ps, ResultSet rs);
}
