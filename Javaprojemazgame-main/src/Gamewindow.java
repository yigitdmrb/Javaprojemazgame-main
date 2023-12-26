import javax.swing.*;//tüm swing kütüphaneleri eklendi swing arayüz için


public class Gamewindow extends JFrame {
    static Maze maze ;
    public static void main(String[] args) {
        Gamewindow window = new Gamewindow();//pencere nesnesi
        window.setTitle("Maze Game");
        window.setSize(525,384);//pencere boyutu
        window.setLocation(400,150);//sol üst köşeye olan uzaklık
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// çarpıya baasınca programı sonlandırır
        window.setFocusable(false);
        window.setResizable(false);//ekran boyutunu değiştirmeyi engeller

        Maze maze = new Maze("Javaprojemazgame-main/src/Images/snake_2.png");
        maze.setSize(525,384);
        maze.requestFocus();//klavyeden islemleri algılaması için focus isteği
        maze.addKeyListener(maze);
        maze.setFocusable(true);
        maze.setFocusTraversalKeysEnabled(false);
        window.add(maze);
        window.mazeTanimlama(maze);
        window.setVisible(true);//pencere gözükmesi için yazılmazsa programı göremeyiz
    }
    void mazeTanimlama(Maze maze){
        Gamewindow.maze =maze;
    }
}