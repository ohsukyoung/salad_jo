package sample;

import java.io.Serial;
//@serial annotation 은 이 문서를 직렬화 형태를 수록하는 문서 페이지에 넣어 달라고 javadoc 유틸리티에 알려주는 역할
import java.io.Serializable;

// 직렬화하려는 클래스는 반드시 Serializable을 implements 해야 한다.
public class Article implements Serializable{

    /**
     * 이클립스에서 Adds a generated serial version ID 기능을 이용하면
     * 자동으로 시리얼 아이디를 생성해준다.
     */
    @Serial
    private static final long serialVersionUID = -83252522547L;
    /** 
     * serialVersionUID: 
     * 1. sSerialVersionUID 지정하지 않으면 컴파일러가 계산한 값 부여 -> 컴파일러에 따라 할당되는 값이 다를 수 있음
     * 2. 컴파일러는 Serializable Class 혹은 Outer Class를 참고하여 만들기 때문에 만약 클래스 변경이 있다면 serialVersionUID 변경이 있을 수 있음
     **/

    public int seq;
    public String writer, message;

    public Article(int seq, String writer, String message) {
        super();
        this.seq = seq;
        this.writer = writer;
        this.message = message;
    }

    @Override
    public String toString() {
        return seq + "\t" + writer + "\t" + message;
    }

}