package com.daiwenzh5.plugin.mybatis.util;


import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FormatUtilsTest {

    @Test
    @DisplayName("重建查询语句")
    public void restoreQuery() {
        ConsoleViewUtils.OriginSqlLog originSqlLog = new ConsoleViewUtils.OriginSqlLog()
                .readLog(null,"15:33:21.917 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - ==>  Preparing: select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark from sys_dict_data where status = '0' and dict_type = ? order by dict_sort asc\n")
                .readLog(null,"15:33:21.926 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - ==> Parameters: sys_user_sex(Long)\n");
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
                .readLog(null,"DEBUG [main] - ==>  Preparing: update t_user set name = ?, salary = ? where id = ?\n")
                .readLog(null,"DEBUG [main] - ==> Parameters: 用户2-afterUpdate(String), 10000(BigDecimal), 2(Long)\n");
        String restore = FormatUtils.restore(originSqlLog, false);
        Assert.assertEquals("update t_user set name = '用户2-afterUpdate', salary = 10000 where id = 2", restore);
    }

    @Test
    @DisplayName("重建包含结果的查询语句")
    public void restoreQueryWithResult() {
        ConsoleViewUtils.OriginSqlLog originSqlLog = new ConsoleViewUtils.OriginSqlLog()
                .readLog(null,"==>  Preparing: SELECT id,supplier_name,org_name,remark,supplier_tax_code,update_time,org_id,org_tax_no,create_by,update_by,create_time,ext4,ext3,ext2,ext1 FROM input_export_enterprise WHERE (org_id IN (?,?) AND supplier_name LIKE ?) LIMIT ?,?")
                .readLog(null,"==> Parameters: 3(Long), 4(Long), %SUP%(String), 0(Long), 10(Long)\n")
                .readLog(null, "<==    Columns: id, supplier_name, org_name, remark, supplier_tax_code, update_time, org_id, org_tax_no, create_by, update_by, create_time, ext4, ext3, ext2, ext1\n")
                .readLog(null, "<==        Row: 3, SUP003, 测试企业3, 测试, 91340500574402383D, 2020-12-04 17:35:47, 3, null, 郑建雄, admin_1000000000128, 2020-12-04 17:35:47, 2, null, null, null\n")
                .readLog(null, "<==        Row: 4, SUP002, 测试企业4, 测试, 91340500574402383D, 2020-11-25 16:22:05, 4, null, 郑建雄, null, 2020-11-25 16:22:05, 2, null, null, null\n")
                .readLog(null, "<==      Total: 2\n");
        String restore = FormatUtils.restore(originSqlLog, false);
        Assert.assertEquals("SELECT id,supplier_name,org_name,remark,supplier_tax_code,update_time,org_id,org_tax_no,create_by,update_by,create_time,ext4,ext3,ext2,ext1 FROM input_export_enterprise WHERE (org_id IN (3,4) AND supplier_name LIKE '%SUP%') LIMIT 0,10",
                restore);
    }
}