package org.cv.ehcache.utils;

import java.util.List;

/**
 * Created by Ale on 2020/4/1
 */
public class DynamicSqlTool {
    StringBuilder sql = null;

    /**
     * 通过id单个删除或者批量删除
     * @param ids
     * @return
     */
    public String delById(List<Long> ids){
        System.out.println(ids);
        sql = new StringBuilder("delete from test_stu where id in (");
        for (Long id : ids) {
            sql.append(id+",");
        }
        sql.setCharAt(sql.length()-1,')');
        return sql.toString();
    }
}
