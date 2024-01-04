import javax.swing.*;

public class Gamewindow extends JFrame {
    static Maze maze;
    public static void main(String[] args) {

        Gamewindow window = new Gamewindow();
        window.setTitle("Maze Game");
        window.setSize(1036, 742);
        window.setLocation(200, 20);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setFocusable(false);
        window.setResizable(false);
        ImageIcon image = new ImageIcon("Javaprojemazgame-main/src/Images/doors/golden_statue_1.png");
        window.setIconImage(image.getImage());


        // Menü oluşturma
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Menü");
        JMenuItem newGameItem = new JMenuItem("Yeni Oyun Başlat");
        JMenuItem exitItem = new JMenuItem("Oyundan Çık");
        JMenuItem infoItem = new JMenuItem("Bilgi");
        JMenuItem storyItem = new JMenuItem("Hikaye");

        //Bilgi seçeneği
        infoItem.addActionListener(e -> {
            JOptionPane.showMessageDialog(window, "     Canavarları yenmek için üzerlerine git ve saldır.   " +
                    "  \nEğer güçlendirmeleri almazsan canavarlar seni öldürebilir.","Bilgi",JOptionPane.INFORMATION_MESSAGE);
        });

        //Hikaye Seçeneği
        storyItem.addActionListener(e -> {
            String text = "Saltun, kaybolmuş ve unutulmuş bir askerdi. Savaşın ardından labirentin içinde, eski bir\n" +
                    "harabe haline gelmiş duvarlar arasında kaybolmuştu. Gözleri, savaşın yorgunluğunu ve\n" +
                    "geçmişin izlerini taşıyordu.\n\n" +
                    "Eski bir asker olarak, stratejik zekası ve savaşma becerileriyle labirentin içinde \n" +
                    "ilerliyordu. Karşısına çıkacak canavarlardan habersiz labirentin içinde yeni bir.\n" +
                    "mücadeleye atılıyordu. Bakalım Saltun sandıktan çıkacak süprizlerler beraber bu \n" +
                    "korkunç labirentten kurtulabilecek mi ?\n\n";
            JOptionPane.showMessageDialog(window, text,"Hikaye",JOptionPane.INFORMATION_MESSAGE);
        });



        // Yeni Oyun Başlat seçeneği için ActionListener ekleme
        newGameItem.addActionListener(e -> {
            Maze bir=resetGame();
            window.add(bir);
            window.mazeTanimlama(bir);
            window.setVisible(true);
            JOptionPane.showMessageDialog(window, "Yeni oyun başlatıldı!","Yeni Oyun",JOptionPane.INFORMATION_MESSAGE);
        });

        // Oyundan Çık seçeneği için ActionListener ekleme
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(newGameItem);
        gameMenu.add(exitItem);
        gameMenu.add(infoItem);
        gameMenu.add(storyItem);
        menuBar.add(gameMenu);

        // Pencereye menüyü ekleme
        window.setJMenuBar(menuBar);

        // Labirent oluşturma ve pencereye ekleme
        Maze maze = new Maze("Javaprojemazgame-main/src/Images/snake_2.png");
        maze.setSize(525 * 2, 384 * 2);
        maze.requestFocus();
        maze.addKeyListener(maze);
        maze.setFocusable(true);
        maze.setFocusTraversalKeysEnabled(false);
        window.add(maze);
        window.mazeTanimlama(maze);
        window.setVisible(true);
    }

    void mazeTanimlama(Maze maze) {
        Gamewindow.maze = maze;
    }

    // Yeni oyun başlatma işlevi
    static Maze resetGame() {
        // Düşmanların koordinatlarının eski yerlerine geri yüklenmesi için temizleme
        Maze.enemies.clear();
        Maze maze = new Maze("Javaprojemazgame-main/src/Images/snake_2.png");
        maze.setSize(525 * 2, 384 * 2);
        maze.requestFocus();
        maze.addKeyListener(maze);
        maze.setFocusable(true);
        maze.setFocusTraversalKeysEnabled(false);
        // Karakterin koordinatlarını eski yerlerine geri yükle
        MyCharacter character = maze.character;
        character.coordinatex = 64;
        character.coordinatey = 64;
        return maze;
    }
}

