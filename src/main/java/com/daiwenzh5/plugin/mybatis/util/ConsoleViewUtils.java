package com.daiwenzh5.plugin.mybatis.util;

import com.daiwenzh5.plugin.mybatis.type.LogType;
import com.intellij.execution.impl.ConsoleViewImpl;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import lombok.AccessLevel;
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


    public final String PREPARING = " - ==>  Preparing: ";
    public final String PARAMETERS = " - ==> Parameters:";
    public final String TOTAL = "<==      Total:";
    public final String SPLIT_LINE = "------";
    private final Map<Project, ConsoleView> consoleViewMap = new HashMap<>();
    private final Map<Project, Integer> indexMap = new HashMap<>();
    @Getter
    private final OriginSqlLog originSqlLog = new OriginSqlLog();

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private boolean status = Boolean.TRUE;

    public boolean isActive() {
        return status;
    }

    @SuppressWarnings("unused")
    public void active() {
        status = Boolean.TRUE;
    }

//    public void put(Project project, SqlConsoleView console) {
//        consoleViewMap.put(project, console);
//    }

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
        ConsoleView consoleView = consoleViewMap.get(project);
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

        private String total = "";

        public OriginSqlLog setPreparing(@NotNull String line) {
            return setPreparing(null, line);
        }

        public OriginSqlLog setPreparing(@Nullable Project project, @NotNull String line) {
            String preparing = StringUtils.substring(line, PREPARING, true).trim();
            if (StringUtils.isNotEmpty(preparing)) {
                if (Objects.nonNull(project)) {
                    Integer index = indexMap.getOrDefault(project, 0);
                    index = index + 1;
                    indexMap.put(project, index);
                    this.info = "-- [" + index + "] " + StringUtils.substring(line, PREPARING);
                } else {
                    this.info = "-- " + StringUtils.substring(line, PREPARING);
                }
                this.preparing = preparing;
            }
            return this;
        }

        public OriginSqlLog setParameters(@NotNull String line) {
            String parameters = StringUtils.substring(line, PARAMETERS, true).trim();
            if (StringUtils.isNotEmpty(parameters)) {
                this.parameters = parameters;
            }
            return this;
        }

        public OriginSqlLog setTotal(@NotNull String line) {
            String total = StringUtils.substring(line, TOTAL, true).trim();
            if (StringUtils.isNotEmpty(total)) {
                this.total = total;
                this.endFlag = true;
            }
            return this;
        }

    }
}
