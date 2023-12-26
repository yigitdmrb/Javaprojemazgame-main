import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.EventListener;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Maze extends JPanel implements KeyListener, EventListener {//mapi burda oluşturuyorum ve klavye işlemleri burda algılanıcak
    static class WallCoordinate {
        int x,y;
        WallCoordinate(int x, int y){
            this.x=x; this.y=y;
        }
    }
    ArrayList<WallCoordinate> wallCoordinates = new ArrayList<>();
  static ArrayList<Enemy> enemies = new ArrayList<>();

    final private BufferedImage wall;
    static int[][] labirentMatrisi;
    final private BufferedImage characterimg;
    final private BufferedImage floorimg;
    static int characterx =32;
    static int charactery =32;
    static int[] nottree= new int[5];

    { try {
            characterimg = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/demigod_male.png"));
            floorimg = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/grass_0_new.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }


    Maze(String filedirectory)  {
        try {
            this.wall =ImageIO.read(new FileInputStream(filedirectory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.green);
        Random rnd = new Random();
        for (int k = 0; k < 5; k++) {
            nottree[k]= rnd.nextInt(1,5); }
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/deep_troll_shaman.png",5,3,10,"Saman Trol"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/halfling_new.png",5,3,10,"Halfing"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/hill_giant_new.png",5,3,10,"Dev"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/rock_troll.png",5,3,10,"Kaya Trol"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/salamander_stormcaller.png",5,3,10,"Salamander"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/sphinx_new.png",5,3,10,"Ejderha"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/stone_giant_new.png",5,3,10,"Tas Dev"));
        labirentMatrisi = new int[][]{ //0 lar yol 1 ler duvar olucak
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 2, 0, 1, 1, 0, 0, 0, 1, 1, 1, 2, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 2, 0, 1},
                {1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {1, 2, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 0, 0, 0, 2, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        };

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey= e.getKeyCode();
        if (pressedKey==KeyEvent.VK_LEFT) {
            characterx -= wall.getWidth()/8;
        }
        else if (pressedKey==KeyEvent.VK_RIGHT) {
            characterx += wall.getWidth()/8;
        }
        else if (pressedKey==KeyEvent.VK_DOWN) {
            charactery+= wall.getHeight()/8;
        }
        else if (pressedKey==KeyEvent.VK_UP) {
            charactery-= wall.getHeight()/8;
        }

        if (checkCollision()) {
            // Çakışma durumunda karakterin konumunu eski konumuna geri al
            if (pressedKey==KeyEvent.VK_LEFT) {
                characterx += wall.getWidth()/8;
            }
            else if (pressedKey==KeyEvent.VK_RIGHT) {
                characterx -= wall.getWidth()/8;
            }
            else if (pressedKey==KeyEvent.VK_DOWN) {
                charactery-= wall.getHeight()/8;
            }
            else if (pressedKey==KeyEvent.VK_UP) {
                charactery+= wall.getHeight()/8;
            }
        }
        repaint();
    }
    private boolean checkCollision() { //resimlerin çakışması kontrol ediliyor
        Rectangle characterRect = new Rectangle(characterx, charactery, characterimg.getWidth(), characterimg.getHeight());
        for (WallCoordinate coordinatwall : wallCoordinates) {
            Rectangle wallRect = new Rectangle(coordinatwall.x, coordinatwall.y, wall.getWidth()-4, wall.getHeight()-4);
            if (wallRect.intersects(characterRect)) {
                return true; // Çakışma durumu
            }
            else{//dusmanla cakısma kontrol
                for (Enemy enemy : enemies)
                {
                   Rectangle enemyRect = new Rectangle(enemy.coordinatex,enemy.coordinatey,32,32);
                   if (enemyRect.intersects(characterRect)) {
                       fight(enemy);
                       return true;
                   }

                }
            }
        }
        return false; // Çakışma yok

    }
    public void fight(Enemy enemy){
        JFrame panel = new JFrame();//savas ekranı
        panel.setLayout(new GridLayout(2, 1));
        JLabel imageLabel = new JLabel(new ImageIcon(enemy.enemyimg));
        panel.add(imageLabel);
        String message = "<html><div style='text-align: center;'>Düşmanla karşılaşıldı!<br>";
        message += "Düşman: " + enemy.name +"&nbsp;&nbsp;";
        message += "Sağlık: " + enemy.health +"<br>";
        message += "Saldırı: " + enemy.attack +"&nbsp;&nbsp;" ;
        message += "Savunma: " + enemy.defense + "<br></div></html>";

        JLabel textLabel = new JLabel(message);
        textLabel.setForeground(Color.white);
        panel.add(textLabel);

        JButton attackButton = new MyJButton("Saldır");
        JButton defenseButton = new MyJButton("Savun");

        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Saldırı butonuna tıklanınca düşmanın sağlığını 2 azalt
                enemy.health -= 2;

                // Düşmanın sağlığı negatif olmasın
                if (enemy.health < 0) {
                    enemy.health = 0;
                    Gamewindow.maze.setFocusable(true);
                   panel.dispose();
                }

                // Saldırı sonrası güncellenmiş bilgileri göster
                String updatedMessage = "<html><div style='text-align: center;'>Düşmanla karşılaşıldı!<br>";
                updatedMessage += "Düşman: " + enemy.name +"&nbsp;&nbsp;";
                updatedMessage += "Sağlık: " + enemy.health +"<br>";
                updatedMessage += "Saldırı: " + enemy.attack +"&nbsp;&nbsp;" ;
                updatedMessage += "Savunma: " + enemy.defense + "<br></div></html>";

                textLabel.setText(updatedMessage);
            }
        });

        defenseButton.addActionListener(new ActionListener() { //savunma butonu
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        panel.add(attackButton);
        panel.add(defenseButton);
        panel.getContentPane().setBackground(Color.black);
        panel.setLocation(400,150);
        panel.setSize(500,250);
        panel.setFocusable(true);
        Gamewindow.maze.setFocusable(false);//arkada kalan ekranda islem yapılmasını engelliyor
        panel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//sekmeyi kapatmayı engelleme
        panel.setVisible(true);
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int sayac=0;
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i < 16; i++) {
                if (labirentMatrisi[k][i]==0)
                    g.drawImage(floorimg, i * 32,  k * 32, this);
                else if(labirentMatrisi[k][i]==2){
                    g.drawImage(floorimg, i * 32,  k * 32, this);
                    if(enemies.get(sayac).health!=0){
                        g.drawImage(enemies.get(sayac).enemyimg, i * 32,  k * 32, this);
                        enemies.get(sayac).coordinatex=i * 32; enemies.get(sayac).coordinatey=k * 32;
                    }
                    else {
                        labirentMatrisi[k][i]=0;
                        enemies.remove(enemies.get(sayac));
                        sayac--;
                        repaint();
                    }
                    sayac++;

                }
                else{
                    g.drawImage(wall, i * 32,  k * 32, this);
                    wallCoordinates.add(new WallCoordinate(i * 32,k * 32));
                }
            }
            g.drawImage(characterimg,characterx,charactery,this);
        }
    }
    public void repaint()
    {
        super.repaint();
    }
}
