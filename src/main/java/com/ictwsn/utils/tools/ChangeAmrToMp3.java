package com.ictwsn.utils.tools;

/**
 * Created by Administrator on 2018-04-28.
 */

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

import java.io.File;

public class ChangeAmrToMp3 {

    public static void main(String[] args) {
        String sourcePath = "C:\\Users\\Administrator\\Desktop\\20180508200436.amr";
        String targetPath = "C:\\Users\\Administrator\\Desktop\\20180508200436.mp3";
//        String sourcePath = args[0];
//        String targetPath = args[1];
        changeToMp3(sourcePath, targetPath);
    }


    public static void changeToMp3(String sourcePath, String targetPath) {
        File source = new File(sourcePath);
        File target = new File(targetPath);
        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        try {
            encoder.encode(source, target, attrs);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }

    public static void voiceToMp3(String sourcePath, String targetPath) {
        File source = new File(sourcePath);
        File target = new File(targetPath);

        Runtime run = null;
        try {
            run = Runtime.getRuntime();
            String command = "sudo ffmpeg -i " + sourcePath +
                    " -f mp3 " + targetPath;
            runCmd(command);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            run.freeMemory();
        }
    }

    public static void runCmd(String command) {
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            int i = proc.waitFor();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}