import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Map;
import java.util.HashMap;



// 직원 안내 메세지 ----------------------------------------------------------------
class Emp {
    private String staff;
    public Emp(String staff){
        this.staff = staff;
    }

    public void empWelcome(){
        System.out.println(staff + "안녕하세요. [샐러드조]입니다.\n 원하는 내용을 입력해주세요.");
    }

}

// 키오스크 ----------------------------------------------------------------
class Kiosk {
    private final PdSetting pdS = new PdSetting();
    private static BufferedReader br;

    // 선택 메뉴 리스트
    static final int e_rcmnd    = 1;   // 추천메뉴
    static final int e_mySalad  = 2;   // 나만의 샐러드
    static final int e_drink    = 3;   // 음료
    static final int e_side     = 4;   // 사이드
    static final int e_cancel = -1;  // 취소
    
    int userSelect;     // 유저 선택값

    public void kioskStart() throws IOException{
        menuDisp();
        menuSelect();
        menuRun();
    }

    public void menuDisp(){
        System.out.println("=============================");
        System.out.println("\t [[샐러드먹조]]");
        System.out.println("\t 1. 추천메뉴");
        System.out.println("\t 2. 나만의 샐러드");
        System.out.println("\t 3. 음료");
        System.out.println("\t 4. 사이드");
        System.out.println("\t - 뒤로가기(c)");
        System.out.println("=============================");
    }

    public void menuSelect() throws IOException{
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.printf(">> 선택: ");
        userSelect = Integer.parseInt(br.readLine());
    }

    public void menuRun(){
        //Choice cho = new Choice();
        switch (userSelect){
            case e_rcmnd    : menuRcmd();   break;
            case e_mySalad  : menuMySalad();break;
            case e_drink    : menuDrink();  break;
            case e_side     : menuSide();   break;
            case e_cancel   : menuCancel(); break;
        }
    }
    
    public void menuRcmd(){     // 추천메뉴

    }
    public void menuMySalad(){  // 나만의 샐러드
        System.out.println("2. 나만의 샐러드************");
        System.out.println(pdS.getsBaseMap());
    }

    public void menuDrink(){    // 음료
        
    }
    public void menuSide(){     // 사이드
        
    }
    public void menuCancel(){   // 취소
        
    }
}



// 선택
class Choice{
    public void choseBase(){

    }
    public void choseMain(){

    }
    public void choseDrink(){

    }
    public void choseSide(){

    }
    public void choseCancle(){

    }

}

// 회원 ----------------------------------------------------------------
class Member{
    private String tel; // 전화번호
    private String pw;  // 비밃번호
    private int point;  // 포인트

    // getter, setter
    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }

    public String getPw() { return pw; }

    public void setPw(String pw) { this.pw = pw; }

    public int getPoint() { return point; }

    public void setPoint(int point) { this.point = point; }
}

// 재료
/*class Material{
    static final int s_Base     = 1;    // 베이스
    static final int s_Main     = 2;    // 메인토핑
    static final int s_Side     = 3;    // 사이드토핑
    static final int s_Source   = 4;    // 소스
    static final int s_Cheese   = 5;    // 치즈
}*/

enum Material{
    S_BASE("베이스"), S_MAIN("메인토핑"), S_SIDE("사이드토핑"), S_SOURCE("소스"), S_CHEESE("치즈");

    private String material;

    // 생성자(싱글톤)
    private Material(String material){
        this.material = material;
    }

    // getter
    public String getMaterial(){
        return material;
    }
}


// 제품
class Product{
    private int p_material; // 분류번호
   // private String p_name;  // 이름
    private int p_unit;     // 단위
    private int p_count;    // 개수
    private int p_calorie;  // 칼로리
    private int p_stock;    // 적정 재고
    private int p_price;    // 금액

    Product(){}
//("이름", new Product(분류번호, 단위, 개수, 칼로리, 적정재고, 금액))
    Product(int p_material,int p_unit,int p_count,int p_calorie,int p_stock,int p_price){
        this.p_material = p_material;
        this.p_unit = p_unit;
        this.p_count = p_count;
        this.p_calorie = p_calorie;
        this.p_stock = p_stock;
        this.p_price = p_price;
    }

    // getter, setter
    public int getP_material() { return p_material; }
    public void setP_material(int p_material) { this.p_material = p_material; }
    //public String getP_name() { return p_name; }
    //public void setP_name(String p_name) { this.p_name = p_name; }
    public int getP_unit() { return p_unit; }
    public void setP_unit(int p_unit) { this.p_unit = p_unit; }
    public int getP_count() { return p_count; }
    public void setP_count(int p_count) { this.p_count = p_count; }
    public int getP_calorie() { return p_calorie; }
    public void setP_calorie(int p_calorie) { this.p_calorie = p_calorie; }
    public int getP_stock() { return p_stock; }
    public void setP_stock(int p_stock) { this.p_stock = p_stock; }
    public int getP_price() { return p_price; }
    public void setP_price(int p_price) { this.p_price = p_price; }
}

    //S_BASE("베이스"), S_MAIN("메인토핑"), S_SIDE("사이드토핑"), S_SOURCE("소스"), S_CHEESE("치즈");
class PdSetting{
    //private Map<String,Product> pdMap = new HashMap<String,Product>();  // 제품- HashMap
    private Map<String,Product> sBaseMap = new HashMap<String,Product>();   // 제품>베이스 HashMap
    private Map<String,Product> sMainMap = new HashMap<String,Product>();   // 제품>메인토핑 HashMap
    private Map<String,Product> sSideMap = new HashMap<String,Product>();   // 제품>사이드토핑 HashMap
    private Map<String,Product> sSourceMap = new HashMap<String,Product>(); // 제품>소스 HashMap
    private Map<String,Product> sCheeseMap = new HashMap<String,Product>(); // 제품>피즈 HashMap
    private Map<String,Member> mbMap = new HashMap<String,Member>();        // 멤버 HashMap

    //제품 셋팅
    //testProductData
    public PdSetting(){
        setS_Base();
        setS_Main();
        setS_Side();
        setS_Source();
        setS_Cheese();
    }

    /*
    1. 양상추, 오이, 토마토, 양파
    2. 닭고기, 소고기, 연어, 우삼겹, 베이컨
    3. 토마토, 올리브, 할라피뇨, 새우, 당근, 오이
    //4. 양송이 크림스프, 콘치즈 스프
    4. 오리엔탈, 발사믹, 시저, 크리미, 칠리, 마요네즈
    5. 아메리칸치즈, 모짜렐라치즈, 리코타치즈, 부라타치즈
     */
//        pdMap.put("이름",new Product(분류번호, 단위, 개수, 칼로리, 적정재고, 금액));
    void setS_Base(){
        sBaseMap.put("양상추",new Product(Material.S_BASE.ordinal(), 1, 100, 200, 300, 400));
        sBaseMap.put("오이",new Product(Material.S_BASE.ordinal(), 1, 100, 200, 300, 400));
        sBaseMap.put("토마토",new Product(Material.S_BASE.ordinal(), 1, 100, 200, 300, 400));
        sBaseMap.put("양파",new Product(Material.S_BASE.ordinal(), 1, 100, 200, 300, 400));


    }
    void setS_Main(){
        sMainMap.put("닭고기",new Product(Material.S_MAIN.ordinal(), 1, 100, 200, 300, 400));
        sMainMap.put("소고기",new Product(Material.S_MAIN.ordinal(), 1, 100, 200, 300, 400));
        sMainMap.put("연어",new Product(Material.S_MAIN.ordinal(), 1, 100, 200, 300, 400));
        sMainMap.put("우삼겹",new Product(Material.S_MAIN.ordinal(), 1, 100, 200, 300, 400));
        sMainMap.put("베이컨",new Product(Material.S_MAIN.ordinal(), 1, 100, 200, 300, 400));
    }
    void setS_Side(){
        sSideMap.put("토마토",new Product(Material.S_SIDE.ordinal(), 1, 100, 200, 300, 400));
        sSideMap.put("올리브",new Product(Material.S_SIDE.ordinal(), 1, 100, 200, 300, 400));
        sSideMap.put("할라피뇨",new Product(Material.S_SIDE.ordinal(), 1, 100, 200, 300, 400));
        sSideMap.put("새우",new Product(Material.S_SIDE.ordinal(), 1, 100, 200, 300, 400));
        sSideMap.put("당근",new Product(Material.S_SIDE.ordinal(), 1, 100, 200, 300, 400));
        sSideMap.put("오이",new Product(Material.S_SIDE.ordinal(), 1, 100, 200, 300, 400));
    }
    void setS_Source(){
        sSourceMap.put("오리엔탈",new Product(Material.S_SOURCE.ordinal(), 1, 100, 200, 300, 400));
        sSourceMap.put("발사믹",new Product(Material.S_SOURCE.ordinal(), 1, 100, 200, 300, 400));
        sSourceMap.put("시저",new Product(Material.S_SOURCE.ordinal(), 1, 100, 200, 300, 400));
        sSourceMap.put("크리미",new Product(Material.S_SOURCE.ordinal(), 1, 100, 200, 300, 400));
        sSourceMap.put("칠리",new Product(Material.S_SOURCE.ordinal(), 1, 100, 200, 300, 400));
        sSourceMap.put("마요네즈",new Product(Material.S_SOURCE.ordinal(), 1, 100, 200, 300, 400));
    }
    void setS_Cheese(){
        sCheeseMap.put("아메리칸치즈",new Product(Material.S_CHEESE.ordinal(), 1, 100, 200, 300, 400));
        sCheeseMap.put("모짜렐라치즈",new Product(Material.S_CHEESE.ordinal(), 1, 100, 200, 300, 400));
        sCheeseMap.put("리코타치즈",new Product(Material.S_CHEESE.ordinal(), 1, 100, 200, 300, 400));
        sCheeseMap.put("부라타치즈",new Product(Material.S_CHEESE.ordinal(), 1, 100, 200, 300, 400));
    }

    //getter
    public Map<String, Product> getsBaseMap() { return sBaseMap; }
    public Map<String, Product> getsMainMap() { return sMainMap; }
    public Map<String, Product> getsSideMap() { return sSideMap; }
    public Map<String, Product> getsSourceMap() { return sSourceMap; }
    public Map<String, Product> getsCheeseMap() { return sCheeseMap; }
    public Map<String, Member> getMbMap() { return mbMap; }
}



// 메인 ----------------------------------------------------------------
public class Main{
    public static Emp emp = new Emp("😊");

    public static void main(String[] args) throws IOException{
        // 직원 인사
        //emp.empWelcome();

        // test 배열 출력
        //Map<String, Product> testBase = new PdSetting().getsBaseMap();
        //System.out.println(testBase);
        Kiosk ks = new Kiosk();
        ks.kioskStart();

    }
}
