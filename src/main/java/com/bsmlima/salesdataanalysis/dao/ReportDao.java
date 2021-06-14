package com.bsmlima.salesdataanalysis.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class ReportDao {

    private final String inputPath;
    private final String outputPath;

    public ReportDao(@Value("${files.input.path}") String inputPath, @Value("${files.output.path}") String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public Stream<String> read(String filename) throws IOException {
        return Files.lines(Paths.get(inputPath, filename), StandardCharsets.UTF_8);
    }

    public void save(String filename, String content) throws IOException {
        Files.write(Paths.get(outputPath, filename), content.getBytes());
    }
}
