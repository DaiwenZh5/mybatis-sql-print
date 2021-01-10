package com.daiwenzh5.plugin.mybatis.util;

import com.daiwenzh5.plugin.mybatis.type.LogType;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author daiwenzh5
 * @date 2020年12月10日
 */
@UtilityClass
public class ConsoleViewUtils {


    public final String PREPARING = "==>  Preparing:";
    public final String PARAMETERS = "==> Parameters:";
    public final String SPLIT_LINE = "------";
    private final Map<Project, ConsoleView> consoleViewMap = new HashMap<>();
    private final Map<Project, Integer> indexMap = new HashMap<>();
    @Getter
    private final OriginSqlLog originSqlLog = new OriginSqlLog();

    private final Map<Project, Boolean> statusMap = new HashMap<>();

    public Boolean isActive(@NotNull Project project) {
        return statusMap.getOrDefault(project, Boolean.FALSE);
    }

    public void active(@NotNull Project project) {
        statusMap.put(project, Boolean.TRUE);
    }

    @SuppressWarnings("unused")
    public ConsoleView get(Project project) {
        // 获取 SQL 控制台
        ConsoleView consoleView = consoleViewMap.get(project);
        // 当缓存中不存在时新建
        if (Objects.isNull(consoleView)) {
            consoleView = new ConsoleViewImpl(project, false);
            consoleViewMap.put(project, consoleView);
        }
        return consoleView;
    }

    public void log(@NotNull Project project, String info, LogType logType) {
        ConsoleView consoleView = ConsoleViewUtils.get(project);
        consoleView.print(info, logType.style);
    }

    public void logN(@NotNull Project project, String info, LogType logType) {
        log(project, info + "\n", logType);
    }


    @Getter
    @Setter
    @ToString
    public class OriginSqlLog {

        private boolean endFlag;

        private String info = "";

        private String preparing = "";

        private String parameters = "";

        public OriginSqlLog readLog(@Nullable Project project, @NotNull String line) {
            // 仅当上一条 SQL 结束时接收新的语句
            if (!endFlag && line.contains(PREPARING)) {
                String[] split = line.split(PREPARING);
                if (Objects.nonNull(project)) {
                    Integer index = indexMap.getOrDefault(project, 0);
                    index = index + 1;
                    indexMap.put(project, index);
                    this.info = "-- [" + index + "] " + split[0].trim();
                } else {
                    this.info = "-- " + split[0].trim();
                }
                this.preparing = split[1];
            } else if (!endFlag && line.contains(PARAMETERS)) {
                this.parameters = Objects.requireNonNull(StringUtil.substringAfter(line, PARAMETERS)).trim();
                this.endFlag = true;
            }
            return this;
        }
    }
}
