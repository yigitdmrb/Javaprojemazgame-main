import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Maze extends JPanel implements KeyListener {//mapi burda oluşturuyorum ve klavye işlemleri burda algılanıcak
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
    final private BufferedImage floorimg;
    static MyCharacter character = new MyCharacter("Javaprojemazgame-main/src/Images/player.png",2,4,25);

    { try {
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
    public void keyPressed(KeyEvent e){
        int pressedKey= e.getKeyCode();
        if (pressedKey==KeyEvent.VK_LEFT) {
            character.coordinatex -= 8;
            try {
                character.leftanimation(1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (pressedKey==KeyEvent.VK_RIGHT) {
            character.coordinatex += 8;
            try {
                character.rightanimation(1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if (pressedKey==KeyEvent.VK_DOWN) {
            try {
                character.downanimation(1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            character.coordinatey+= 8;
        }
        else if (pressedKey==KeyEvent.VK_UP) {
            try {
                character.upanimation(1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            character.coordinatey-= 8;
        }

        if (checkCollision()) {
            // Çakışma durumunda karakterin konumunu eski konumuna geri al
            if (pressedKey==KeyEvent.VK_LEFT) {
                try {
                    character.leftanimation(-1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                character.coordinatex += 8;
            }
            else if (pressedKey==KeyEvent.VK_RIGHT) {
                try {
                    character.rightanimation(-1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                character.coordinatex -= 8;
            }
            else if (pressedKey==KeyEvent.VK_DOWN) {
                try {
                    character.downanimation(-1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                character.coordinatey-= 8;
            }
            else if (pressedKey==KeyEvent.VK_UP) {
                try {
                    character.upanimation(-1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                character.coordinatey+=8;
            }
        }
        repaint();
    }
    private boolean checkCollision() { //resimlerin çakışması kontrol ediliyor
        Rectangle characterRect = new Rectangle(character.coordinatex, character.coordinatey, character.img.getWidth()*4, character.img.getHeight()*4);
        for (WallCoordinate coordinatwall : wallCoordinates) {
            Rectangle wallRect = new Rectangle(coordinatwall.x, coordinatwall.y, wall.getWidth()*2, wall.getHeight()*2);
            if (wallRect.intersects(characterRect)) {
                return true; // Çakışma durumu
            }
            else{//dusmanla cakısma kontrol
                for (Enemy enemy : enemies)
                {
                    Rectangle enemyRect = new Rectangle(enemy.coordinatex,enemy.coordinatey,enemy.img.getWidth()*2,enemy.img.getHeight()*2);
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
        JLabel imageLabel = new JLabel(new ImageIcon(enemy.img));
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
                enemy.health -= character.attack;
                // Saldırı sonrası güncellenmiş bilgileri göster
                String updatedMessage = "<html><div style='text-align: center;'>Düşmanla karşılaşıldı!<br>";
                updatedMessage += "Düşman: " + enemy.name +"&nbsp;&nbsp;";
                updatedMessage += "Sağlık: " + enemy.health +"<br>";
                updatedMessage += "Saldırı: " + enemy.attack +"&nbsp;&nbsp;" ;
                updatedMessage += "Savunma: " + enemy.defense + "<br></div></html>";

                textLabel.setText(updatedMessage);
                if (enemy.health < 0) {

                    panel.dispose();
                    Gamewindow.maze.setFocusable(true);
                    enemy.health = 0;
                    repaint();//düsman haritadan silinmiyordu tekrar cizdirdim
                }

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
    public void paint(Graphics g) {//cizdirme fonksiyonu
        super.paint(g);

        int sayac=0;
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i < 16; i++) {
                if (labirentMatrisi[k][i]==0)
                    g.drawImage(floorimg, i * 64,  k * 64, 64,64,this);
                else if(labirentMatrisi[k][i]==2){
                    g.drawImage(floorimg, i * 64,  k * 64,64,64, this);
                    if(enemies.get(sayac).health!=0){
                        g.drawImage(enemies.get(sayac).img, i * 64,  k * 64, 64,64,this);
                        enemies.get(sayac).coordinatex=i * 64; enemies.get(sayac).coordinatey=k * 64;
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
                    g.drawImage(wall, i * 64,  k * 64,64,64, this);
                    wallCoordinates.add(new WallCoordinate(i * 64,k * 64));
                }
            }
            g.drawImage(character.img,character.coordinatex,character.coordinatey,64,64,this);
        }
    }
    public void repaint()
    {
        super.repaint();
    }
}