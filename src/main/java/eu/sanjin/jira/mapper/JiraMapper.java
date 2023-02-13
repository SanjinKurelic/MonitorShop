package eu.sanjin.jira.mapper;

import eu.sanjin.jira.model.JiraReport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.testng.ITestResult;
import org.testng.annotations.Test;

@Mapper
public interface JiraMapper {

    @Mapping(target = "testCase", source = ".", qualifiedByName = "getTestCase")
    @Mapping(target = "comment", source = "throwable.message")
    JiraReport mapITestResultToJiraReport(ITestResult result);

    @Named("getTestCase")
    default String getTestCaseKey(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
    }
}
