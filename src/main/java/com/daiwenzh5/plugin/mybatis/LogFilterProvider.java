package com.daiwenzh5.plugin.mybatis;

import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author daiwenzh5
 * @description 日志过滤器发布者
 * @date 2020/12/8
 */
public class LogFilterProvider implements ConsoleFilterProvider {

    @NotNull
    @Override
    public Filter[] getDefaultFilters(@NotNull Project project) {
        return new Filter[]{new LogFilter(project)};
    }
}
