package com.daiwenzh5.plugin.mybatis.aop;

import com.daiwenzh5.plugin.mybatis.type.LogType;
import com.daiwenzh5.plugin.mybatis.util.ConsoleViewUtils;
import com.daiwenzh5.plugin.mybatis.util.FormatUtils;
import com.intellij.execution.ConsoleFolding;
import com.intellij.openapi.project.Project;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @author daiwenzh5
 * @description 日志折叠面板
 * @date 2020/12/14
 */
@Slf4j
public class SqlConsoleFolding extends ConsoleFolding {


    @Override
    public boolean shouldFoldLine(@NotNull Project project, @NotNull String line) {
        line = line.replace("\n", "");
        // 当控制台激活时执行
        if (ConsoleViewUtils.isActive()) {
            // 读取原 SQL 日志
            ConsoleViewUtils.OriginSqlLog originSqlLog = ConsoleViewUtils.getOriginSqlLog()
                    .setPreparing(project,line)
                    .setParameters(line)
                    .setResult(line);
            // 当 SQL 读取结束后进行格式化并打印
            if (originSqlLog.isEndFlag()) {
                String restoreSql = FormatUtils.restore(originSqlLog);
                log.info("重组 SQL：{}", restoreSql);
                ConsoleViewUtils.logN(project, originSqlLog.getInfo(), LogType.ANNOTATION);
                ConsoleViewUtils.logN(project, restoreSql, LogType.SQL);
                ConsoleViewUtils.logN(project, "Total: " + originSqlLog.getResult(), LogType.ANNOTATION);
                ConsoleViewUtils.logN(project, ConsoleViewUtils.SPLIT_LINE, LogType.SPLIT_LINE);
            }
        }
        return super.shouldFoldLine(project, line);
    }
}
