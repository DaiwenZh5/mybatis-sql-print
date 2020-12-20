package com.daiwenzh5.plugin.mybatis.util;


import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.impl.stores.IProjectStore;
import com.intellij.openapi.project.impl.ProjectImpl;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.SystemIndependent;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class FormatUtilsTest {

    @Test
    public void restore() {
        ConsoleViewUtils.OriginSqlLog originSqlLog = new ConsoleViewUtils.OriginSqlLog()
                .setPreparing("15:33:21.917 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - ==>  Preparing: select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark from sys_dict_data where status = '0' and dict_type = ? order by dict_sort asc\n")
                .setParameters("15:33:21.926 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - ==> Parameters: sys_user_sex(Long)\n")
                .setTotal("15:33:21.955 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - <==      Total: 3\n");
        String restore = FormatUtils.restore(originSqlLog);
        Assert.assertEquals(restore, "select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark\n" +
                "FROM sys_dict_data\n" +
                "WHERE status = '0' and dict_type = sys_user_sex order by dict_sort asc");
    }
}