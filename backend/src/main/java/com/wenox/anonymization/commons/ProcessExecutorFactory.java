package com.wenox.anonymization.commons;

import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

public class ProcessExecutorFactory {

  public static ProcessExecutor newProcess(String... command) {
    return new ProcessExecutor(command)
        .exitValue(0)
        .readOutput(false)
        .redirectOutput(System.out)
        .redirectOutputAlsoTo(Slf4jStream.of(LoggerFactory.getLogger(ProcessExecutorFactory.class)).asInfo())
        .redirectError(System.err)
        .redirectErrorAlsoTo(Slf4jStream.of(LoggerFactory.getLogger(ProcessExecutorFactory.class)).asError())
        .destroyOnExit()
        .timeout(60, TimeUnit.SECONDS);
  }
}
