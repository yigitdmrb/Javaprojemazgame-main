import javax.swing.*;

public class Gamewindow extends JFrame {
    static Maze maze ;
    public static void main(String[] args) {
        Gamewindow window = new Gamewindow();//pencere nesnesi
        window.setTitle("Maze Game");
        window.setSize(1036,742);//pencere boyutu
        window.setLocation(200,20);//sol üst köşeye olan uzaklık
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// çarpıya baasınca programı sonlandırır
        window.setFocusable(false);
        window.setResizable(false);//ekran boyutunu değiştirmeyi engeller
        ImageIcon image = new ImageIcon("Javaprojemazgame-main/src/Images/doors/golden_statue_1.png");
        window.setIconImage(image.getImage());

        Maze maze = new Maze("Javaprojemazgame-main/src/Images/snake_2.png");
        maze.setSize(525*2,384*2);
        maze.requestFocus();//klavyeden islemleri algılaması için focus isteği
        maze.addKeyListener(maze);
        maze.setFocusable(true);
        maze.setFocusTraversalKeysEnabled(false);
        window.add(maze);
        window.mazeTanimlama(maze);
        window.setVisible(true);//pencere gözükmesi için yazılmazsa programı göremeyiz
    }
    void mazeTanimlama(Maze maze){
        Gamewindow.maze =maze;//labirentte savas baslayinca maze nesnesine odaklanmayı engellemek icin tanımladım
    }
}