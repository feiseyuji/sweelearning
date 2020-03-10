package com.womenhz.swee.nio.javabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ChannelDemo {
    /**
     * 1. channel分类：filexxx, socketxxx(tcp), ServerSocketxxx(tcp), Datagramxxx(udp)
     *
     *
     * */

    public static void main(String[] args) {
        nioCopyFile("f:\\ruoyi_20200304.sql", "f:\\copy.sql");
    }

    public static void nioCopyFile(String srcFilepath, String destFilePath) {
        File srcfile = new File(srcFilepath);
        File destFile = new File(destFilePath);

        try {
            if (!destFile.exists()){
                destFile.createNewFile();
            }

            long startTime = System.currentTimeMillis();
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            FileChannel inChannel = null;
            FileChannel outChannel = null;

            try{
                fileInputStream = new FileInputStream(srcfile);
                fileOutputStream = new FileOutputStream(destFile);

                inChannel = fileInputStream.getChannel();
                outChannel = fileOutputStream.getChannel();

                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int length = -1;
                while ((length = inChannel.read(byteBuffer)) != -1) {
                    byteBuffer.flip();
                    int outlength = 0;
                    while ((outlength = outChannel.write(byteBuffer)) != 0) {
                        log.info("write = "+outlength);
                    }
                    byteBuffer.clear();
                }
                outChannel.force(true);
            }finally {
                outChannel.close();
                fileOutputStream.close();
                inChannel.close();
                fileInputStream.close();
            }
            long endTime = System.currentTimeMillis();
            log.info("time = "+(endTime - startTime));
        }catch (Exception e) {

            log.info("e = "+e.getMessage());
        }

    }

}
