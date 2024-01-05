import javax.swing.*;

public class Gamewindow extends JFrame {
    //patch notes: heal resmini değiştirdim , oyundan çıkmak için menüden çıkış yapman gerekiyor , karakter statları eklendi
    static Maze maze;
    public static void main(String[] args) {

        Gamewindow window = new Gamewindow();
        window.setTitle("Maze Game");
        window.setSize(1036, 764);
        window.setLocation(200, 20);
        window.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
        JMenuItem infoCharacterItem = new JMenuItem("Karakter statları");


        //Bilgi seçeneği
        infoItem.addActionListener(e -> JOptionPane.showMessageDialog(window, """
                     Canavarları yenmek için üzerlerine git ve saldır.  \s
                Eğer güçlendirmeleri almazsan canavarlar seni öldürebilir.
                Her iki saldırında bir güç toplayarak kurt vuruşu yapabilirsin.""","Bilgi",JOptionPane.INFORMATION_MESSAGE));

        //Hikaye Seçeneği
        storyItem.addActionListener(e -> {
            String text = """
                    Saltun, kaybolmuş ve unutulmuş bir askerdi. Savaşın ardından labirentin içinde, eski bir
                    harabe haline gelmiş duvarlar arasında kaybolmuştu. Gözleri, savaşın yorgunluğunu ve
                    geçmişin izlerini taşıyordu.

                    Eski bir asker olarak, stratejik zekası ve savaşma becerileriyle labirentin içinde\s
                    ilerliyordu. Karşısına çıkacak canavarlardan habersiz labirentin içinde yeni bir.
                    mücadeleye atılıyordu. Bakalım Saltun sandıktan çıkacak süprizlerler beraber bu\s
                    korkunç labirentten kurtulabilecek mi ?

                    """;
            JOptionPane.showMessageDialog(window, text,"Hikaye",JOptionPane.INFORMATION_MESSAGE);
        });



        // Yeni Oyun Başlat seçeneği için ActionListener ekleme
        newGameItem.addActionListener(e -> {
            Gamewindow.maze.setVisible(false);
            Maze bir=resetGame();
            window.add(bir);
            window.mazeTanimlama(bir);
            window.setVisible(true);
            JOptionPane.showMessageDialog(window, "Yeni oyun başlatıldı!","Yeni Oyun",JOptionPane.INFORMATION_MESSAGE);
        });
        //Menüde karakter statları gösteriyor
        infoCharacterItem.addActionListener(e -> {
            MyCharacter character = Gamewindow.maze.character;
            String characterStats = character.getCharacterStats();

            JOptionPane.showMessageDialog(window, characterStats, "Karakter Statları", JOptionPane.INFORMATION_MESSAGE);
        });

        // Oyundan Çık seçeneği için ActionListener ekleme
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(newGameItem);
        gameMenu.add(exitItem);
        gameMenu.add(infoItem);
        gameMenu.add(storyItem);
        gameMenu.add(infoCharacterItem);
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
        character.health=20;
        character.coordinatex = 64;
        character.coordinatey = 64;
        character.attack=2;
        return maze;
    }
}
