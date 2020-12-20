package com.daiwenzh5.plugin.mybatis.util;


import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FormatUtilsTest {

    @Test
    @DisplayName("重建查询语句")
    public void restoreQuery() {
        ConsoleViewUtils.OriginSqlLog originSqlLog = new ConsoleViewUtils.OriginSqlLog()
                .setPreparing("15:33:21.917 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - ==>  Preparing: select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark from sys_dict_data where status = '0' and dict_type = ? order by dict_sort asc\n")
                .setParameters("15:33:21.926 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - ==> Parameters: sys_user_sex(Long)\n")
                .setResult("15:33:21.955 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - <==      Total: 3\n");
        String restore = FormatUtils.restore(originSqlLog, true);
        System.out.println(restore);
//        Assert.assertEquals(restore, "select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark " +
//                "from sys_dict_data " +
//                "where status = '0' and dict_type = sys_user_sex order by dict_sort asc");
    }

    @Test
    @DisplayName("重建更新语句")
    public void restoreUpdate() {
        ConsoleViewUtils.OriginSqlLog originSqlLog = new ConsoleViewUtils.OriginSqlLog()
                .setPreparing("DEBUG [main] - ==>  Preparing: update t_user set name = ?, salary = ? where id = ?\n")
                .setParameters("DEBUG [main] - ==> Parameters: 用户2-afterUpdate(String), 10000(BigDecimal), 2(Long)\n")
                .setResult("DEBUG [main] - <==    Updates: 1\n");
        String restore = FormatUtils.restore(originSqlLog, false);
        Assert.assertEquals(restore,
                "update t_user set name = '用户2-afterUpdate', salary = 10000 where id = 2");
    }
}