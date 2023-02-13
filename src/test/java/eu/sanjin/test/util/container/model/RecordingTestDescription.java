package eu.sanjin.test.util.container.model;

import lombok.RequiredArgsConstructor;
import org.testcontainers.lifecycle.TestDescription;
import org.testng.ITestResult;
import org.testng.annotations.Test;

@RequiredArgsConstructor
public class RecordingTestDescription implements TestDescription {

    private final ITestResult result;

    @Override
    public String getTestId() {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
    }

    @Override
    public String getFilesystemFriendlyName() {
        return result.getName();
    }
}
