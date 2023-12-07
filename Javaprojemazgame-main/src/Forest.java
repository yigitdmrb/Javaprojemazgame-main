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

public class Forest extends JPanel implements KeyListener, EventListener {//mapi burda oluşturuyorum ve klavye işlemleri burda algılanıcak
    static class WallCoordinate {
        int x,y;
        WallCoordinate(int x, int y){
            this.x=x; this.y=y;
        }
    }
    ArrayList<WallCoordinate> wallCoordinates = new ArrayList<>();
  static Enemies[] enemies = new Enemies[7];

    final private BufferedImage wall;
    final private BufferedImage characterimg;
    final private BufferedImage floorimg;
    static int characterx =32;
    static int charactery =32;
    static int[] nottree= new int[5];
    {
        try {
            floorimg = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/grass_0_new.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    {
        try {
            characterimg = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/demigod_male.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Forest(String filedirectory)  {
        try {
            this.wall =ImageIO.read(new FileInputStream(filedirectory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.green);
        Random rnd = new Random();
        for (int k = 0; k < 5; k++) {
            nottree[k]= rnd.nextInt(1,5); }
        enemies[0]=new Enemies("Javaprojemazgame-main/src/Images/enemies/deep_troll_shaman.png",5,3,10,"Saman Trol");
        enemies[1]=new Enemies("Javaprojemazgame-main/src/Images/enemies/halfling_new.png",5,3,10,"Halfing");
        enemies[2]=new Enemies("Javaprojemazgame-main/src/Images/enemies/hill_giant_new.png",5,3,10,"Dev");
        enemies[3]=new Enemies("Javaprojemazgame-main/src/Images/enemies/rock_troll.png",5,3,10,"Kaya Trol");
        enemies[4]=new Enemies("Javaprojemazgame-main/src/Images/enemies/salamander_stormcaller.png",5,3,10,"Salamander");
        enemies[5]=new Enemies("Javaprojemazgame-main/src/Images/enemies/sphinx_new.png",5,3,10,"Ejderha");
        enemies[6]=new Enemies("Javaprojemazgame-main/src/Images/enemies/stone_giant_new.png",5,3,10,"Tas Dev");


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
        for (WallCoordinate coordinatetree : wallCoordinates) {
            Rectangle wallRect = new Rectangle(coordinatetree.x, coordinatetree.y, wall.getWidth(), wall.getHeight());
            if (wallRect.intersects(characterRect)) {
                return true; // Çakışma durumu
            }
        }
        return false; // Çakışma yok

    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int[][] labirentMatrisi = { //0 lar yol 1 ler duvar olucak
                {1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
                {1,0, 0, 0, 2, 0, 1, 1, 0, 0, 0, 1, 1, 1, 2,1},
                {1,0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0,1},
                {1,0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0,1},
                {1,0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 2, 0,1},
                {1,1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0,1},
                {1,0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 2, 0, 1, 0,1},
                {1,2, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0,1},
                {1,0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0,1},
                {1,1, 1, 1, 1, 0, 0, 0, 2, 1, 0, 1, 0, 1, 0,1},
                {1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
        };
        int sayac=0;
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i < 16; i++) {
                if (labirentMatrisi[k][i]==0)
                    g.drawImage(floorimg, i * 32,  k * 32, this);
                else if(labirentMatrisi[k][i]==1){
                    g.drawImage(wall, i * 32,  k * 32, this);
                    wallCoordinates.add(new WallCoordinate(i * 32,k * 32));
                }
                else{
                    g.drawImage(floorimg, i * 32,  k * 32, this);
                    g.drawImage(enemies[sayac].enemyimg, i * 32,  k * 32, this);
                    sayac++;
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
