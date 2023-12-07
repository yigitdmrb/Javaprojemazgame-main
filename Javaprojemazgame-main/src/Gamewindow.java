import javax.swing.*;//tüm swing kütüphaneleri eklendi swing arayüz için
import java.awt.*;//bu nerden geldi bilmiyom ama lazım sanırım slşkdsldfsfdkş


public class Gamewindow extends JFrame {
    public static void main(String[] args) {
        Gamewindow window = new Gamewindow();//pencere nesnesi ve contructda baslık gönderiliyor
        window.setTitle("Maze Game");
        window.setSize(525,384);//pencere boyutu
        window.setLocation(400,150);//sol üst köşeye olan uzaklık
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// çarpıya baasınca programı sonlandırır
        window.setFocusable(false);
        window.setResizable(false);//ekran boyutunu değiştirmeyi engeller


        Forest forest = new Forest("Javaprojemazgame-main/src/Images/wall_vines_6.png");
        forest.setSize(525,384);
        forest.requestFocus();//klavyeden islemleri algılaması için focus isteği
        forest.addKeyListener(forest);
        forest.setFocusable(true);
        forest.setFocusTraversalKeysEnabled(false);
        window.add(forest);
        window.setVisible(true);//pencere gözükmesi için yazılmazsa programı göremeyiz
    }
}