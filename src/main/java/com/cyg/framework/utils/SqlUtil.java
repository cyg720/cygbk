package com.cyg.framework.utils;

/**
 * orcale 分页sql转换
 * @author Administrator
 *
 */
public class SqlUtil {
	
	public static String getLimitSql(String sql, int offset, int limit) {
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toUpperCase().endsWith(" FOR UPDATE")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect
				.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ ) where rownum_ > " + offset
				+ " and rownum_ <= " + (offset + limit));
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect.toString();
	}


}
