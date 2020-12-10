package com.daiwenzh5.plugin.mybatis;

import com.daiwenzh5.plugin.mybatis.util.FormatSqlUtils;
import com.daiwenzh5.plugin.mybatis.util.StringUtils;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @author daiwenzh5
 * @description SQL日志过滤器
 * @date 2020/12/8
 */
public class LogFilter implements Filter {

    public static final String PREPARING = " - ==>  Preparing: ";
    public static final String PARAMETERS = " - ==> Parameters:";
    public static final String TOTAL = "<==      Total:";
    public static final String SPLIT_LINE = "-----------------------------------------------------------------------------------------------------------------------";
    public static Integer endIndex = 1;
    private static String preparingLine = "";
    private static StringBuffer parametersLine = new StringBuffer();
    private static boolean isEnd = false;
    private final Project project;

    public LogFilter(Project project) {
        this.project = project;
    }

    @Nullable
    @Override
    public Result applyFilter(@NotNull String currentLine, int i) {
        if (this.project == null) {
            return null;
        }
        //是否一条sql+参数结束
        //这里有个bug，如果最后一条sql参数console出来后没有其他日志console，则不会触发最后一条sql的格式化动作。

        if (endIndex != 1 && !currentLine.contains(PARAMETERS)
                || currentLine.contains(TOTAL)) {
            //已结束Sql
            isEnd = true;
            System.out.println("=====================结束当前sql=====================");
            endIndex = 1;
            //拼接sql
            System.out.println("--------------------开始格式化sql--------------------");
            print();
        }
        //是否包含sql语句
        if (currentLine.contains(PREPARING)) {
            //设置当前sql语句
            preparingLine = currentLine;
            System.out.println("监测到sql语句:" + currentLine);
            System.out.println("+++++++++++++++++++++准备开始获取sql参数，初始化序号为[1]+++++++++++++++++++++");
            endIndex = 1;
            //包含则return null，继续返回读取下一行日志
            return null;
        }
        //是否包含sql参数
        if (currentLine.contains(PARAMETERS)) {
            String paramValue = getParamValue(currentLine);
            System.out.println("序号为[" + endIndex + "]，获取到 sql 参数为：" + paramValue);
            endIndex++;
            parametersLine.append(paramValue);
            return null;
        }

        //sql语句为空则继续读下一行日志
        StringUtils.isEmpty(preparingLine);
        return null;
    }


    private String getParamValue(String sqlParam) {
        int index = sqlParam.indexOf(PARAMETERS);
        if (index > -1) {
            return sqlParam.substring(index + PARAMETERS.length()).trim();
        }
        return "";
    }

    private void print() {
        if (StringUtils.isNotEmpty(preparingLine) && StringUtils.isNotEmpty(parametersLine.toString()) && isEnd) {
            int indexNum = PropertiesCenter.getIndex(project);
            String[] split = StringUtils.split(preparingLine, PREPARING);
            //序号前缀字符串
            String preStr = "-- " + indexNum + " " + split[0].trim();
            PropertiesCenter.setIndex(project, ++indexNum);
            String restoreSql = FormatSqlUtils.restore(split[1], parametersLine.toString());
            ToolWindowConsole.log(preStr, "annotation");
            ToolWindowConsole.log(restoreSql, "sql");
            ToolWindowConsole.log(SPLIT_LINE, "split");
            preparingLine = "";
            parametersLine = new StringBuffer();
            isEnd = false;
        }
    }

    public static void main(String[] args) {
        String[] split = StringUtils.split("15:44:02.217 [restartedMain] DEBUG c.r.s.m.S.selectDictDataByType - [debug,137] - ==>  Preparing: select dict_code, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark from sys_dict_data where status = '0' and dict_type = ? order by dict_sort asc\n", PREPARING);
        System.out.println(split[0]);
    }


}
