package ru.otus.dsokolov.service;

import ru.otus.dsokolov.domain.TestResult;

public interface TestService {

    void runTest();

    boolean isTestPassed(TestResult testResult);

    void processPersonAnswers(TestResult testResult);
}
