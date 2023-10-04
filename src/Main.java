import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.util.Map;
import java.util.HashMap;

import java.util.List;
import java.util.ArrayList;

import java.util.Iterator;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 직원 안내 메세지 ----------------------------------------------------------------
*/
class Emp {
    private String staff;
    public Emp(String staff){
        this.staff = staff;
    }

    public void empWelcome(){
        System.out.println(staff + "안녕하세요. [샐러드조]입니다.\n 원하는 내용을 입력해주세요.");
    }

}

/*
 회원 ----------------------------------------------------------------
*/
class Member{
    private String tel; // 전화번호
    private String pw;  // 비밀번호
    private int point;  // 포인트

    // getter, setter
    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }

    public String getPw() { return pw; }

    public void setPw(String pw) { this.pw = pw; }

    public int getPoint() { return point; }

    public void setPoint(int point) { this.point = point; }
}

/*
 키오스크 ----------------------------------------------------------------
*/
class Kiosk {
    private final PdSetting pdS = new PdSetting();
    private static BufferedReader br;

    SelectMenu sMenu = new SelectMenu();

    // 2dep
    dep2_infoBase dep2InfoBa = new dep2_infoBase();
    dep2_infoMain dep2InfoMa = new dep2_infoMain();
    dep2_infoSide dep2InfoSi = new dep2_infoSide();
    dep2_infoSource dep2InfoSo = new dep2_infoSource();
    dep2_infoCheese dep2InfoCh = new dep2_infoCheese();

    //사장추천
    dep1_infoCeo dep1InfoCe = new dep1_infoCeo();

    // 음료
    dep1_infoDrink dep1InfoDr = new dep1_infoDrink();

    // 사이드
    dep1_infoSide dep1InfoSi = new dep1_infoSide();

//    infoCancel choCancel = new infoCancel();

    // 선택 메뉴 리스트
    static final int e_rcmnd    = 1;   // 사장추천
    static final int e_mySalad  = 2;   // 나만의 샐러드
    static final int e_drink    = 3;   // 음료
    static final int e_side     = 4;   // 사이드
    static final int e_cancel = -1;  // 취소
    
    int userSelect;     // 유저 선택값

    public void kioskStart(){
        menuDisp();
        menuRun();
    }

    public void menuDisp(){
        System.out.println("=============================");
        System.out.println("\t [[샐러드먹조]]");
        System.out.println("\t 1. 사장추천");
        System.out.println("\t 2. 나만의 샐러드");
        System.out.println("\t 3. 음료");
        System.out.println("\t 4. 사이드");
//        System.out.println("\t - 뒤로가기(c)");
        System.out.println("=============================");
        userSelect = sMenu.menuSelect(4);
    }


    public void menuRun(){
        switch (userSelect){
            case e_rcmnd    : menuRcmd();   break;
            case e_mySalad  : menuMySalad();break;
            case e_drink    : menuDrink();  break;
            case e_side     : menuSide();   break;
            case e_cancel   : menuCancel(); break;
        }
    }
    
    public void menuRcmd(){     // 사장추천
        System.out.println("\n1. 사장추천 -------------------------------------- ");
        dep1InfoCe.menuInfo();
    }
    public void menuMySalad(){  // 나만의 샐러드
        System.out.println("\n2. 나만의 샐러드 -------------------------------------- ");

        dep2InfoBa.menuInfo();
        dep2InfoMa.menuInfo();
        dep2InfoSi.menuInfo();
        dep2InfoSo.menuInfo();
        dep2InfoCh.menuInfo();

    }

    public void menuDrink(){    // 음료

        System.out.println("\n2. 음료 -------------------------------------- ");
        dep1InfoDr.menuInfo();
    }
    public void menuSide(){     // 사이드
        System.out.println("\n2. 사이드 -------------------------------------- ");
        dep1InfoSi.menuInfo();
    }
    public void menuCancel(){   // 취소
        
    }
}

/*
 메뉴 선택 ----------------------------------------------------------------
*/
abstract class Super_Select{
    int userSelect = 0;
    protected String selmessage01 = ">> 메뉴 선택: ";
    protected String selmessage02 = "수량이 남은 수량에서 벗어났습니다.";
    int minNum = 1;
    static BufferedReader br;
    public int menuSelect(int listSize){
        br = new BufferedReader(new InputStreamReader(System.in));
        try{
            do {
                System.out.printf(selmessage01);
                userSelect = Integer.parseInt(br.readLine());
                if(userSelect<minNum || userSelect> listSize)
                    System.out.println(selmessage02);
            }while (userSelect<minNum || userSelect> listSize);
        }catch (IOException e){
            System.out.println("e.toString: "+e.toString());
            System.out.println("e.getMessage: "+e.getMessage());
            System.out.println("printStackTrace................");
            e.printStackTrace();
        }
        return userSelect;
    }
}

// 메뉴 선택
class SelectMenu extends Super_Select{
    @Override
    public int menuSelect(int listSize){
        selmessage01 = ">> 메뉴 선택: ";
        selmessage02 = "메뉴 리스트 번호에서 벗어났습니다. 다시 입력해주세요.";
        super.menuSelect(listSize);
        return userSelect;
    }
}

// 개수 선택
class SelectCount extends Super_Select{
    @Override
    public int menuSelect(int listSize){
        minNum = 0;
        selmessage01 = ">> 수량 선택: ";
        selmessage02 = "수량이 남은 수량에서 벗어났습니다. 다시 입력해주세요.";
        super.menuSelect(listSize);
        return userSelect;
    }
}

/*
 안내 ----------------------------------------------------------------
*/
interface Impl_Info {
    public void menuInfo();     // 메뉴선택
    public void menuCount();    // 갯수선택
}

abstract class Super_Info implements Impl_Info {
    private static BufferedReader br;
    List<Product> mList = new PdSetting().getsBaseList();
    Iterator<Product> citList;

    int userSelect = 0; // 유저 선택
    int userStock = 0; // 유저 수량 개수
    int pdStock = 0; // 재고 개수

    public void infoHeader(){
        System.out.printf("%-4s| %-8s|\t%-8s|\t%-8s|\t%-8s\t|\t%-8s\n", "번호", "상품명", "단위", "칼로리", "가격", "남은수량");
    }

    public void infoBody(){
        citList = mList.iterator();
        for (int i=1; i<=mList.size();i++){
            Product itS = citList.next();
            System.out.printf("%-4d   %-8s \t%-8s \t%-8s\t \t%-8d \t%-8d\n", i, itS.getP_name(), itS.getP_unit(), itS.getP_calorie(), itS.getP_price(), itS.getP_stock());
        }
    }

    @Override
    public void menuInfo(){
        SelectMenu sMenu = new SelectMenu();
        SelectCount sCount = new SelectCount();

        infoHeader();   // 정보 표 헤더
        infoBody();     // 정보 표 바디

        // 유저 메뉴 숫자 선택
        userSelect = sMenu.menuSelect(mList.size());

        // 유저 재고 개수 선택
        pdStock = mList.get(userSelect-1).getP_stock(); // 선택한 재고개수
        userStock = sCount.menuSelect(pdStock);

        // 재고 빼기
        mList.get(userSelect-1).setP_stock(pdStock - userStock);

//        테스트를 위한 코드
        pdStock = mList.get(userSelect-1).getP_stock(); // 선택한 재고개수
        System.out.println(pdStock);
    }

    @Override
    public void menuCount(){

    }
}
// 2dep print ----------------------------------------------------------------
class dep2_infoBase extends Super_Info {
    @Override
    public void menuInfo(){
        System.out.println("\t\t\t\t[ 베이스 ■ ■ ■ ■ ]");
        mList = new PdSetting().getsBaseList();
        super.menuInfo();
    }

    @Override
    public void menuCount(){}
}
class dep2_infoMain extends Super_Info {
    @Override
    public void menuInfo(){
        System.out.println("\t\t\t\t[ ■ 메인토핑 ■ ■ ■ ]");
        mList = new PdSetting().getsMainList();
        super.menuInfo();
    }

    @Override
    public void menuCount() {
    }
}
class dep2_infoSide extends Super_Info {
    @Override
    public void menuInfo(){
        System.out.println("\t\t\t\t[ ■ ■ 사이드토핑 ■ ■ ]");
        mList = new PdSetting().getsSideList();
        super.menuInfo();
    }

    @Override
    public void menuCount() {
    }
}
class dep2_infoSource extends Super_Info {
    
    @Override
    public void menuInfo(){
        System.out.println("\t\t\t\t[ ■ ■ ■ 소스 ■ ]");
        mList = new PdSetting().getsSourceList();
        super.menuInfo();
    }

    @Override
    public void menuCount() {
    }
}
class dep2_infoCheese extends Super_Info {
    @Override
    public void menuInfo(){
        System.out.println("\t\t\t\t[ ■ ■ ■ ■ 치즈 ]");
        mList = new PdSetting().getsCheeseList();
        super.menuInfo();
    }

    @Override
    public void menuCount() {
    }
}

class dep1_infoCeo extends Super_Info {
    List<CeoRcmd> cList = new PdSetting().getCeoList();
    Iterator<CeoRcmd> citList = cList.iterator();
    @Override
    public void infoHeader(){
        System.out.printf("%-4s| %-8s|\t%-8s|\t%-8s\t|\t%-8s\n", "번호", "상품명", "상세재료", "칼로리", "가격");
    }
    @Override
    public void infoBody(){
        for (int i=1; i<=cList.size();i++){
            CeoRcmd itS = citList.next();
            System.out.printf("%-4d   %-8s \t%-8s\t \t%-8d \t%-8d\n", i, itS.getC_name(), itS.getC_detail(), itS.getC_calorie(), itS.getC_price());
        }
    }

    @Override
    public void menuInfo(){
        super.menuInfo();
    }

    @Override
    public void menuCount() {
    }
}

class dep1_infoDrink extends Super_Info {
    @Override
    public void infoHeader(){
        System.out.printf("%-4s| %-8s|\t%-8s|\t%-8s\t|\t%-8s\n", "번호", "상품명", "칼로리", "가격", "남은수량");
    }
    @Override
    public void infoBody(){
        citList = mList.iterator();
        for (int i=1; i<=mList.size();i++){
            Product itS = citList.next();
            System.out.printf("%-4d   %-8s \t%-8s\t \t%-8s \t%-8s\n", i, itS.getP_name(), itS.getP_calorie(), itS.getP_price(), itS.getP_stock());
        }
    }

    @Override
    public void menuInfo(){
        mList = new PdSetting().getDrinkList();
        super.menuInfo();
    }

    @Override
    public void menuCount() {
    }
}

class dep1_infoSide extends Super_Info {
    @Override
    public void infoHeader(){
        System.out.printf("%-4s| %-8s|\t%-8s|\t%-8s\t|\t%-8s\n", "번호", "상품명", "칼로리", "가격", "남은수량");
    }
    @Override
    public void infoBody(){
        citList = mList.iterator();
        for (int i=1; i<=mList.size();i++){
            Product itS = citList.next();
            System.out.printf("%-4d   %-8s \t%-8s\t \t%-8s \t%-8s\n", i, itS.getP_name(), itS.getP_calorie(), itS.getP_price(), itS.getP_stock());
        }
    }

    @Override
    public void menuInfo(){
        mList = new PdSetting().getSideList();
        super.menuInfo();
    }

    @Override
    public void menuCount() {
    }
}

class infoCancel{}

/*
 2dep 재료 ----------------------------------------------------------------
*/
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

/*
 주문 ----------------------------------------------------------------
*/
class UserOrder{
    private String u_Base;  // 베이스
    private int u_BaCount;
    private String u_Main;  // 메인
    private int u_MaCount;
    private String u_Side;  // 사이드
    private int u_SiCount;
    private String u_Source; // 소스
    private int u_SoCount;
    private String u_Cheese; // 치즈
    private int u_ChCount;

    UserOrder(String u_Base, int u_BaCount, String u_Main, int u_MaCount, String u_Side, int u_SiCount, String u_Source, int u_SoCount, String u_Cheese, int u_ChCount){
        this.u_Base = u_Base;
        this.u_BaCount = u_BaCount;
        this.u_Main = u_Main;
        this.u_MaCount = u_MaCount;
        this.u_Side = u_Side;
        this.u_SiCount = u_SiCount;
        this.u_Source = u_Source;
        this.u_SoCount = u_SoCount;
        this.u_Cheese = u_Cheese;
        this.u_ChCount = u_ChCount;
    };

    public String getU_Base() { return u_Base; }
    public void setU_Base(String u_Base) { this.u_Base = u_Base; }
    public int getU_BaCount() { return u_BaCount; }
    public void setU_BaCount(int u_BaCount) { this.u_BaCount = u_BaCount; }
    public String getU_Main() { return u_Main; }
    public void setU_Main(String u_Main) { this.u_Main = u_Main; }
    public int getU_MaCount() { return u_MaCount; }
    public void setU_MaCount(int u_MaCount) { this.u_MaCount = u_MaCount; }
    public String getU_Side() { return u_Side; }
    public void setU_Side(String u_Side) { this.u_Side = u_Side; }
    public int getU_SiCount() { return u_SiCount; }
    public void setU_SiCount(int u_SiCount) { this.u_SiCount = u_SiCount; }
    public String getU_Source() { return u_Source; }
    public void setU_Source(String u_Source) { this.u_Source = u_Source; }
    public int getU_SoCount() { return u_SoCount; }
    public void setU_SoCount(int u_SoCount) { this.u_SoCount = u_SoCount; }
    public String getU_Cheese() { return u_Cheese; }
    public void setU_Cheese(String u_Cheese) { this.u_Cheese = u_Cheese; }
    public int getU_ChCount() { return u_ChCount; }
    public void setU_ChCount(int u_ChCount) { this.u_ChCount = u_ChCount; }
}

class Order{
    // 이름
    // 년월일/시간
    // 샐러드(베이스-개수,메인토핑-개수,사이드토핑-개수,소스-개수,치즈-개수)
    // 총 칼로리, 총 결재금액
    private String o_name;
    private String o_nowTime;
    private int o_totCalorie;
    private int o_totPrice;

}
class OrderCart{
    public void nowTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd a HH:mm:ss");
        Date nowDate = new Date();
        System.out.println(simpleDateFormat.format(nowDate));
    }
}


/*
 제품 ----------------------------------------------------------------
*/
// 샐러드 재료 -------------------------------
class Product{
    private int p_material; // 분류번호
    private String p_name;  // 이름
    private String p_unit;  // 단위
    private int p_count;    // 개수
    private int p_calorie;  // 칼로리
    private int p_stock;    // 적정 재고
    private int p_price;    // 금액

    // 생성자
    Product(){}

    // 사용자 정의 생성자 ------
    // 음료, 사이드
    Product(String p_name,int p_count,int p_calorie,int p_stock,int p_price){
        //이름, 개수, 칼로리, 적정재고, 금액
        this.p_name = p_name;
        this.p_count = p_count;
        this.p_calorie = p_calorie;
        this.p_stock = p_stock;
        this.p_price = p_price;
    }
    // 나만의 샐러드 재료
    Product(int p_material,String p_name,String p_unit,int p_count,int p_calorie,int p_stock,int p_price){
        //("이름", new Product(분류번호, 단위, 개수, 칼로리, 적정재고, 금액))
        this.p_material = p_material;
        this.p_name = p_name;
        this.p_unit = p_unit;
        this.p_count = p_count;
        this.p_calorie = p_calorie;
        this.p_stock = p_stock;
        this.p_price = p_price;
    }

    // getter, setter
    public int getP_material() { return p_material; }
    public void setP_material(int p_material) { this.p_material = p_material; }
    public String getP_name() { return p_name; }
    public void setP_name(String p_name) { this.p_name = p_name; }
    public String getP_unit() { return p_unit; }
    public void setP_unit(String p_unit) { this.p_unit = p_unit; }
    public int getP_count() { return p_count; }
    public void setP_count(int p_count) { this.p_count = p_count; }
    public int getP_calorie() { return p_calorie; }
    public void setP_calorie(int p_calorie) { this.p_calorie = p_calorie; }
    public int getP_stock() { return p_stock; }
    public void setP_stock(int p_stock) { this.p_stock = p_stock; }
    public int getP_price() { return p_price; }
    public void setP_price(int p_price) { this.p_price = p_price; }
}
// 사장 추천 -------------------------------
class CeoDetail{
    private String c_dt01;
    private String c_dt02;
    private String c_dt03;

    public CeoDetail(String c_dt01, String c_dt02, String c_dt03) {
        this.c_dt01 = c_dt01;
        this.c_dt02 = c_dt02;
        this.c_dt03 = c_dt03;
    }

    public String getC_dt01() { return c_dt01; }

    public void setC_dt01(String c_dt01) { this.c_dt01 = c_dt01; }

    public String getC_dt02() { return c_dt02; }

    public void setC_dt02(String c_dt02) { this.c_dt02 = c_dt02; }

    public String getC_dt03() { return c_dt03; }

    public void setC_dt03(String c_dt03) { this.c_dt03 = c_dt03; }

    @Override
    public String toString() {
        return "{"+ c_dt01 + ',' + c_dt02 + ',' + c_dt03 + '}';
    }
}

class CeoRcmd{
    private String c_name;      // 사장님추천메뉴명
    private CeoDetail c_detail; // 상세재료
    private int c_calorie;      // 칼로리
    private int c_price;        // 가격

    CeoRcmd(String c_name, CeoDetail c_detail, int c_calorie, int c_price){
        this.c_name = c_name;
        this.c_detail = c_detail;
        this.c_calorie = c_calorie;
        this.c_price = c_price;
    }

    // getter, setter
    public String getC_name() { return c_name; }
    public void setC_name(String c_name) { this.c_name = c_name; }
    public CeoDetail getC_detail() { return c_detail; }
    public void setC_detail(CeoDetail c_detail) { this.c_detail = c_detail; }
    public int getC_calorie() { return c_calorie; }
    public void setC_calorie(int c_calorie) { this.c_calorie = c_calorie; }
    public int getC_price() { return c_price; }
    public void setC_price(int c_price) { this.c_price = c_price; }
}
/*
제품 셋팅 ----------------------------------------------------------------
*/
class PdSetting{
    //S_BASE("베이스"), S_MAIN("메인토핑"), S_SIDE("사이드토핑"), S_SOURCE("소스"), S_CHEESE("치즈");
    // 나만의 샐러드 -------------------------------
    private List<Product> sBaseList = new ArrayList<Product>();         // 제품>베이스 ArrayList
    private List<Product> sMainList = new ArrayList<Product>();         // 제품>메인토핑 ArrayList
    private List<Product> sSideList = new ArrayList<Product>();         // 제품>사이드토핑 ArrayList
    private List<Product> sSourceList = new ArrayList<Product>();       // 제품>소스 ArrayList
    private List<Product> sCheeseList = new ArrayList<Product>();       // 제품>치즈 ArrayList
    // 사장추천 -------------------------------
    private List<CeoRcmd> ceoList = new ArrayList<CeoRcmd>();    // 제품>사장님추천 ArrayList
    // 음료 -------------------------------
    private List<Product> drinkList = new ArrayList<Product>();     // 제품>음료 ArrayList
    // 사이드 -------------------------------
    private List<Product> sideList = new ArrayList<Product>();      // 제품>사이드 ArrayList
    // 회원 -------------------------------
    private Map<String,Member> mbMap = new HashMap<String,Member>();            // 멤버 ArrayList

    //testProductData
    public PdSetting(){
        // 나만의 샐러드
        setS_Base();
        setS_Main();
        setS_Side();
        setS_Source();
        setS_Cheese();

        //사장추천
        set_Ceo();

        // 음료
        set_Drink();
        
        //사이드
        set_Side();
    }

    /*
    1. 양상추, 오이, 토마토, 양파
    2. 닭고기, 소고기, 연어, 우삼겹, 베이컨
    3. 토마토, 올리브, 할라피뇨, 새우, 당근, 오이
    //4. 양송이 크림스프, 콘치즈 스프
    4. 오리엔탈, 발사믹, 시저, 크리미, 칠리, 마요네즈
    5. 아메리칸, 모짜렐라, 리코타, 부라타
     */
//        pdMap.put("이름",new Product(분류번호, 단위, 개수, 칼로리, 적정재고, 금액));
    void setS_Base(){
        sBaseList.add(new Product(Material.S_BASE.ordinal(), "양상추", "1", 100, 200, 5, 400));
        sBaseList.add(new Product(Material.S_BASE.ordinal(), "오이", "1", 100, 200, 4, 400));
        sBaseList.add(new Product(Material.S_BASE.ordinal(), "토마토", "1", 100, 200, 3, 400));
        sBaseList.add(new Product(Material.S_BASE.ordinal(), "양파", "1", 100, 200, 1, 400));
    }
    void setS_Main(){
        sMainList.add(new Product(Material.S_MAIN.ordinal(), "닭고기", "1", 100, 200, 5, 400));
        sMainList.add(new Product(Material.S_MAIN.ordinal(), "소고기", "1", 100, 200, 5, 400));
        sMainList.add(new Product(Material.S_MAIN.ordinal(), "연어", "1", 100, 200, 5, 400));
        sMainList.add(new Product(Material.S_MAIN.ordinal(), "우삼겹", "1", 100, 200, 5, 400));
        sMainList.add(new Product(Material.S_MAIN.ordinal(), "계란", "1", 100, 200, 5, 400));
    }
    void setS_Side(){
        sSideList.add(new Product(Material.S_SIDE.ordinal(), "토마토", "1", 100, 200, 5, 400));
        sSideList.add(new Product(Material.S_SIDE.ordinal(), "올리브", "1", 100, 200, 5, 400));
        sSideList.add(new Product(Material.S_SIDE.ordinal(), "크렌베리", "1", 100, 200, 5, 400));
        sSideList.add(new Product(Material.S_SIDE.ordinal(), "새우", "1", 100, 200, 5, 400));
        sSideList.add(new Product(Material.S_SIDE.ordinal(), "옥수수", "1", 100, 200, 5, 400));
        sSideList.add(new Product(Material.S_SIDE.ordinal(), "오이", "1", 100, 200, 5, 400));
    }
    void setS_Source(){
        sSourceList.add(new Product(Material.S_SOURCE.ordinal(), "오리엔탈","1", 100, 200, 5, 400));
        sSourceList.add(new Product(Material.S_SOURCE.ordinal(), "발사믹","1", 100, 200, 5, 400));
        sSourceList.add(new Product(Material.S_SOURCE.ordinal(), "시저","1", 100, 200, 5, 400));
        sSourceList.add(new Product(Material.S_SOURCE.ordinal(), "크리미","1", 100, 200, 5, 400));
        sSourceList.add(new Product(Material.S_SOURCE.ordinal(), "칠리","1", 100, 200, 5, 400));
        sSourceList.add(new Product(Material.S_SOURCE.ordinal(), "마요네즈","1", 100, 200, 5, 400));
    }
    void setS_Cheese(){
        sCheeseList.add(new Product(Material.S_CHEESE.ordinal(), "아메리칸","1", 100, 200, 5, 400));
        sCheeseList.add(new Product(Material.S_CHEESE.ordinal(), "모짜렐라","1", 100, 200, 5, 400));
        sCheeseList.add(new Product(Material.S_CHEESE.ordinal(), "리코타","1", 100, 200, 5, 400));
        sCheeseList.add(new Product(Material.S_CHEESE.ordinal(), "부라타","1", 100, 200, 5, 400));
    }
    
    void set_Ceo(){
        ceoList.add(new CeoRcmd("시저치킨샐러드",new CeoDetail("닭고기","크렌베리","시저"),200,200));
        ceoList.add(new CeoRcmd("콥샐러드",new CeoDetail("계란","옥수수","시저"),200,200));
        ceoList.add(new CeoRcmd("연어샐러드",new CeoDetail("연어","토마토","크리미"),200,200));
    }

    void set_Drink(){
        drinkList.add(new Product("콜라", 100, 200, 5, 400));
        drinkList.add(new Product("사이다", 100, 200, 5, 400));
        drinkList.add(new Product("콜라Zero", 100, 200, 5, 400));
        drinkList.add(new Product("사이다Zero", 100, 200, 5, 400));
    }
    void set_Side(){
        sideList.add(new Product("양송이스프", 100, 200, 5, 400));
        sideList.add(new Product("콘치즈스프", 100, 200, 5, 400));
    }

    //getter
    public List<Product> getsBaseList() { return sBaseList; }
    public List<Product> getsMainList() { return sMainList; }
    public List<Product> getsSideList() { return sSideList; }
    public List<Product> getsSourceList() { return sSourceList; }
    public List<Product> getsCheeseList() { return sCheeseList; }

    public List<CeoRcmd> getCeoList() { return ceoList; }
    public List<Product> getDrinkList() { return drinkList; }
    public List<Product> getSideList() { return sideList; }

    public Map<String, Member> getMbMap() { return mbMap; }

    // setter
    public void setsBaseList(List<Product> sBaseList) { this.sBaseList = sBaseList; }
    public void setsMainList(List<Product> sMainList) { this.sMainList = sMainList; }
    public void setsSideList(List<Product> sSideList) { this.sSideList = sSideList; }
    public void setsSourceList(List<Product> sSourceList) { this.sSourceList = sSourceList; }
    public void setsCheeseList(List<Product> sCheeseList) { this.sCheeseList = sCheeseList; }

    public void setCeoList(List<CeoRcmd> ceoList) { this.ceoList = ceoList; }
    public void setDrinkList(List<Product> drinkList) { this.drinkList = drinkList; }
    public void setSideList(List<Product> sideList) { this.sideList = sideList; }

    public void setMbMap(Map<String, Member> mbMap) { this.mbMap = mbMap; }
}



/*
 메인 ----------------------------------------------------------------
*/
public class Main{
    public static Emp emp = new Emp(":)");

    public static void main(String[] args) throws IOException{
        // 직원 인사
        //emp.empWelcome();



        Kiosk ks = new Kiosk();
        ks.kioskStart();


    }
}
