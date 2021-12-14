package ru.otus.dsokolov.service;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ShellService {
    private final TestServiceImpl testService;

    public ShellService(TestServiceImpl testService) {
        this.testService = testService;
    }

    @ShellMethod(key = "run_test", value = "run test")
    public void runTest() {
        testService.runTest();
    }

}
