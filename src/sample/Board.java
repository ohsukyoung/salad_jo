package sample;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {

    static List<Article> list = new ArrayList<>();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // 직렬화 입력받기: 파일로부터 직렬화된 내용을 읽어 객체로 변환시킨 뒤
        // 메모리에 적재시킨다.
        try {
            readFromFile();
        } catch(FileNotFoundException e) {}

        Scanner s = new Scanner(System.in);
        while(true) {
            prompt(s);
        }
    }

    private static void prompt(Scanner s) throws FileNotFoundException, IOException {

        displayBoard();

        System.out.print(">> ");
        String command = s.nextLine();
        if(command.equalsIgnoreCase("w") || command.equalsIgnoreCase("write")) {
            write(s);
        } else if (command.equalsIgnoreCase("r") || command.equalsIgnoreCase("remove")) {
            remove(s);
        } else if (command.equalsIgnoreCase("x") || command.equalsIgnoreCase("exit")) {
            System.exit(0);
        }

    }

    private static void write(Scanner s) throws FileNotFoundException, IOException {
        System.out.print("작성자 이름? ");
        String writer = s.nextLine();

        System.out.print("글 내용? ");
        String message = s.nextLine();

        int seq = 0;
        if(list.size() != 0) {
            seq = list.get(list.size() - 1).seq + 1;
        }

        list.add(new Article(seq, writer, message));
        System.out.println();

        // 직렬화 출력하기: 글이 작성되었으면 메모리의 객체를 직렬화한 뒤
        // 물리적인 파일로 하드디스크에 저장한다.
        saveToFile();

    }

    private static void remove(Scanner s) throws FileNotFoundException, IOException {
        System.out.print("삭제할 번호? ");
        int removeNum = s.nextInt();
        list.remove(removeNum);
    }

    private static void displayBoard() {
        System.out.println("순서\t글쓴이\t메시지");
        System.out.println("-----------------------");

        if(list.size() == 0) {
            System.out.println("[아직 글이 없습니다.]");
        }

        list.forEach((x) -> {
            System.out.println(x);
        });
    }

    private static void saveToFile() throws FileNotFoundException, IOException {

        // 파일 출력 스트림 객체를 만든 후, 이름을 "board.ser"라고 지정하고
        // fos를 바탕으로 오브젝트 출력 스트림을 생성한 뒤 writeObject 한다.
        try(FileOutputStream fos = new FileOutputStream("board.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);) {
            oos.writeObject(list);
        }
    }

    @SuppressWarnings("unchecked")
    private static void readFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        // 파일 입력 스트림 객체를 만든 후, 이름을 "board.ser"라고 지정하면 이 파일로부터 읽겠다는 뜻이며
        // fis를 바탕으로 오브젝트 입력 스트림을 생성한 뒤 readObject 한다.
        // 오브젝트 형태로 읽으면 안에 있는 내용이 무슨 타입인지 정확히 알기 어려우므로 타입 캐스팅 해준다.
        try(FileInputStream fis = new FileInputStream("board.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);) {
            list = (List<Article>) ois.readObject();
        }
    }
}