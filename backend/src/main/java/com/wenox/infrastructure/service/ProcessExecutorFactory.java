package com.wenox.infrastructure.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;
import org.slf4j.LoggerFactory;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.slf4j.Slf4jStream;

public class ProcessExecutorFactory {

  public static ProcessExecutor newProcess(String... command) {
    return new ProcessExecutor(command)
        .exitValue(0)
        .readOutput(false)
        .redirectOutput(Slf4jStream.of(LoggerFactory.getLogger(ProcessExecutorFactory.class)).asInfo())
        .destroyOnExit()
        .timeout(60, TimeUnit.SECONDS);
  }

  public static ProcessExecutor newProcessWithOutputRedirectedTo(OutputStream outputStream, String... command) {
    return new ProcessExecutor(command)
        .exitValue(0)
        .readOutput(false)
        .redirectOutput(outputStream)
        .redirectOutputAlsoTo(Slf4jStream.of(LoggerFactory.getLogger(ProcessExecutorFactory.class)).asInfo())
        .destroyOnExit()
        .timeout(60, TimeUnit.SECONDS);
  }
}
