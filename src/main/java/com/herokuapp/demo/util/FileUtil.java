package com.herokuapp.demo.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

import com.herokuapp.demo.exception.BaseException;

public class FileUtil {
    private FileUtil(){}

    public static final String ENCODING_UTF8 = "UTF-8";

    public static void writeFile(String content, String fileName) {
        Path path = Paths.get(fileName);
        createFileIfNotExist(path);

        try(SeekableByteChannel seekableByteChannel = Files.newByteChannel(path,
                EnumSet.of(StandardOpenOption.WRITE,
                        StandardOpenOption.TRUNCATE_EXISTING))) {

            ByteBuffer buffer = ByteBuffer.wrap(content.getBytes(Charset.forName(ENCODING_UTF8)));
            seekableByteChannel.write(buffer);
            buffer.clear();
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }

    private static void createFileIfNotExist(Path path) {
        try {
            if(path.toFile().exists()) {
                return;
            }
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        } catch (IOException e) {
            throw new BaseException(e);
        }
    }
}
