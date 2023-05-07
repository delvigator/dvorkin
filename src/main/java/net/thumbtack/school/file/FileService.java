package net.thumbtack.school.file;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


import com.google.gson.Gson;
import net.thumbtack.school.colors.v3.ColorException;
import net.thumbtack.school.figures.v3.ColoredRectangle;
import net.thumbtack.school.figures.v3.Rectangle;
import net.thumbtack.school.ttschool.Trainee;
import net.thumbtack.school.ttschool.TrainingException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {
    public static void writeByteArrayToBinaryFile(String fileName, byte[] array) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName)) {

            out.write(array);
            // REVU не нужно его ловит, пробрасывайте выше
            // вообще catch не нужен. Здесь и везде в этом классе
        }
    }

    public static void writeByteArrayToBinaryFile(File file, byte[] array) throws IOException {
        writeByteArrayToBinaryFile(file.getAbsolutePath(), array);
    }

    public static byte[] readByteArrayFromBinaryFile(String fileName) throws IOException {
        try (FileInputStream fin = new FileInputStream(fileName)) {
            // REVU как все сложно...
            // просто взять длину файла, сделать массив на эту длину и fin.read его
            byte[] buffer = new byte[fin.available()];
            int i;
            int j = 0;
            while ((i = fin.read()) != -1 && j < buffer.length) {
                buffer[j] = (byte) i;
                j++;
            }

            return buffer;

        }
    }

    public static byte[] readByteArrayFromBinaryFile(File file) throws IOException {
        return readByteArrayFromBinaryFile(file.getAbsolutePath());
    }

    public static byte[] writeAndReadByteArrayUsingByteStream(byte[] array) throws IOException {
        // REVU try
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] res = new byte[array.length / 2];

            out.write(array);

            try (
                    ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray())
            ) {
                int j = 0;
                for (int i = 0; i < array.length; i++) {
                    int data = input.read();
                    if (i % 2 == 0) {
                        res[j] = (byte) data;
                        j++;
                    }
                }
            }
            return res;
        }
    }

    public static void writeByteArrayToBinaryFileBuffered(String fileName, byte[] array) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fileName);
             BufferedOutputStream bos = new BufferedOutputStream(out)) {
            bos.write(array, 0, array.length);
        } catch (IOException ex) {
            throw new RuntimeException(ex);

        }
    }

    public static void writeByteArrayToBinaryFileBuffered(File file, byte[] array) throws IOException {
        writeByteArrayToBinaryFileBuffered(file.getAbsolutePath(), array);
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(String fileName) throws IOException {
        try (FileInputStream is = new FileInputStream(fileName)) {
            BufferedInputStream bf = new BufferedInputStream(is);
            return bf.readAllBytes();
        }
    }

    public static byte[] readByteArrayFromBinaryFileBuffered(File file) throws IOException {
        return readByteArrayFromBinaryFileBuffered(file.getAbsolutePath());
    }

    public static void writeRectangleToBinaryFile(File file, Rectangle rect) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            dataOutputStream.writeInt(rect.getTopLeft().getX());
            dataOutputStream.writeInt(rect.getTopLeft().getY());
            dataOutputStream.writeInt(rect.getBottomRight().getX());
            dataOutputStream.writeInt(rect.getBottomRight().getY());
        }
    }

    public static Rectangle readRectangleFromBinaryFile(File file) throws IOException, ClassNotFoundException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            return new Rectangle(dataInputStream.readInt(),
                    dataInputStream.readInt(),
                    dataInputStream.readInt(),
                    dataInputStream.readInt());
        }

    }

    public static void writeColoredRectangleToBinaryFile(File file, ColoredRectangle rect) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            dataOutputStream.writeInt(rect.getTopLeft().getX());
            dataOutputStream.writeInt(rect.getTopLeft().getY());
            dataOutputStream.writeInt(rect.getBottomRight().getX());
            dataOutputStream.writeInt(rect.getBottomRight().getY());
            dataOutputStream.writeUTF(rect.getColor().toString());
        }
    }

    public static ColoredRectangle readColoredRectangleFromBinaryFile(File file) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            return new ColoredRectangle(dataInputStream.readInt(),
                    dataInputStream.readInt(),
                    dataInputStream.readInt(),
                    dataInputStream.readInt(), dataInputStream.readUTF());
        } catch (ColorException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeRectangleArrayToBinaryFile(File file, Rectangle[] rects) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file))) {
            for (Rectangle counter : rects) {
                dataOutputStream.writeInt(counter.getTopLeft().getX());
                dataOutputStream.writeInt(counter.getTopLeft().getY());
                dataOutputStream.writeInt(counter.getBottomRight().getX());
                dataOutputStream.writeInt(counter.getBottomRight().getY());
            }
        }
    }

    public static Rectangle[] readRectangleArrayFromBinaryFileReverse(File file) throws IOException {
        // REVU надо читать в обратном порядке, а не заносить
        // RandomAccessFile и его метод seek
        /*try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file))) {
            Rectangle[] res = new Rectangle[dataInputStream.available()/16];
            for (int j = dataInputStream.available()/16-1; j>=0; j--) {
                res[j] = new Rectangle(dataInputStream.readInt(),
                        dataInputStream.readInt(),
                        dataInputStream.readInt(),
                        dataInputStream.readInt());
            }
            return res;
        }
         */
        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            raf.seek(file.length());
            Rectangle[] array = new Rectangle[(int) (file.length() / 16)];
            int j = 0;
            for (long i = file.length() - 16; i >= 0; i -= 16) {
                raf.seek(i);
                array[j] = (new Rectangle(raf.readInt(),
                        raf.readInt(), raf.readInt(), raf.readInt()));
                j++;
            }
            return array;
        }
    }

    public static void writeRectangleToTextFileOneLine(File file, Rectangle rect) throws IOException {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                Writer writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8)
        ) {
            writer.write(rect.getTopLeft().getX() + " ");
            writer.write(rect.getTopLeft().getY() + " ");
            writer.write(rect.getBottomRight().getX() + " ");
            writer.write(rect.getBottomRight().getY() + " ");
        }
    }

    public static Rectangle readRectangleFromTextFileOneLine(File file) throws IOException {
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            String string = br.readLine();
            String[] n = string.split(" ");
            return new Rectangle(Integer.parseInt(n[0]), Integer.parseInt(n[1]), Integer.parseInt(n[2]), Integer.parseInt(n[3]));
        }
    }

    public static void writeRectangleToTextFileFourLines(File file, Rectangle rect) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(Integer.toString(rect.getTopLeft().getX()));
            fw.write(System.lineSeparator());
            fw.write(Integer.toString(rect.getTopLeft().getY()));
            fw.write(System.lineSeparator());
            fw.write(Integer.toString(rect.getBottomRight().getX()));
            fw.write(System.lineSeparator());
            fw.write(Integer.toString(rect.getBottomRight().getY()));
        }
    }

    public static Rectangle readRectangleFromTextFileFourLines(File file) throws IOException {
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            return new Rectangle(Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()), Integer.parseInt(br.readLine()));

        }
    }

    public static void writeTraineeToTextFileOneLine(File file, Trainee trainee) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(trainee.getFirstName());
            fw.write(" ");
            fw.write(trainee.getLastName());
            fw.write(" ");
            fw.write(Integer.toString(trainee.getRating()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Trainee readTraineeFromTextFileOneLine(File file) throws IOException {
        try (FileReader fr = new FileReader(file);
             Scanner scanner = new Scanner(fr)) {
            return new Trainee(scanner.next(), scanner.next(), scanner.nextInt());
        } catch (TrainingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeTraineeToTextFileThreeLines(File file, Trainee trainee) throws IOException {
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(trainee.getFirstName());
            fw.write(System.lineSeparator());
            fw.write(trainee.getLastName());
            fw.write(System.lineSeparator());
            fw.write(Integer.toString(trainee.getRating()));


        }
    }

    public static Trainee readTraineeFromTextFileThreeLines(File file) throws IOException {
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {
            return new Trainee(br.readLine(), br.readLine(), Integer.parseInt(br.readLine()));
        } catch (TrainingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void serializeTraineeToBinaryFile(File file, Trainee trainee) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream ob = new ObjectOutputStream(fileOutputStream)
        ) {
            ob.writeObject(trainee);

        }
    }

    public static Trainee deserializeTraineeFromBinaryFile(File file) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream ob = new ObjectInputStream(fileInputStream)
        ) {
            return (Trainee) ob.readObject();

        }
    }

    public static String serializeTraineeToJsonString(Trainee trainee) throws IOException {
        Gson gson = new Gson();
        return gson.toJson(trainee);
    }

    public static Trainee deserializeTraineeFromJsonString(String json) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(json, Trainee.class);
    }

    public static void serializeTraineeToJsonFile(File file, Trainee trainee) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new Gson();
            gson.toJson(trainee, writer);
        }
    }

    public static Trainee deserializeTraineeFromJsonFile(File file) throws IOException {
        try (Reader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Trainee.class);

        }
    }
}

