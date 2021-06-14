package com.bsmlima.salesdataanalysis.watcher;

import com.bsmlima.salesdataanalysis.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

@Profile("!test")
@Service
public class DirectoryWatcher implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final WatchService watchService;
    private final ReportService reportService;
    private final String inputPath;

    public DirectoryWatcher(ReportService reportService, @Value("${files.input.path}") String inputPath) throws IOException {
        this.reportService = reportService;
        this.watchService = FileSystems.getDefault().newWatchService();
        this.inputPath = inputPath;

        Path path = Paths.get(inputPath);
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    }

    @Override
    public void run(final String... s) throws InterruptedException {
        processInitialFiles();
        watchAndProcessNewFiles();
    }

    private void processInitialFiles() {
        File f = new File(inputPath);
        List<String> files = Arrays.asList(f.list());

        logger.info("Processing initial files, {} files found", files.size());

        files.forEach(filename -> {
            try {
                reportService.processFile(filename);
            } catch (IOException e) {
                logger.error("Unable to process file {}", filename);
            }
        });
        logger.info("Finished processing initial files");
    }

    private void watchAndProcessNewFiles() throws InterruptedException{
        logger.info("Started watching directory {} for changes", inputPath);
        while (true) {
            WatchKey queuedKey = watchService.take();
            for (WatchEvent<?> watchEvent : queuedKey.pollEvents()) {
                try {
                    reportService.processFile(watchEvent.context().toString());
                } catch (IOException e) {
                    logger.error("Unable to process file {}", watchEvent.context().toString());
                }
                queuedKey.reset();
            }
        }
    }
}
