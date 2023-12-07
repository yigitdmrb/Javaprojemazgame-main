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
    static class TreesCoordinate{
        int x,y;
        TreesCoordinate(int x, int y){
            this.x=x; this.y=y;
        }
    }
    ArrayList<TreesCoordinate> treesCoordinates = new ArrayList<TreesCoordinate>();

    final private BufferedImage tree;
    final private BufferedImage characterimg;
    final private BufferedImage floorimg;
    static int characterx =32;
    static int charactery =32;
    static int[] nottree= new int[5];
    {
        try {
            floorimg = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/mesh_3_old.png"));
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
            this.tree=ImageIO.read(new FileInputStream(filedirectory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.green);
        Random rnd = new Random();
        for (int k = 0; k < 5; k++) {
            nottree[k]= rnd.nextInt(1,5); }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey= e.getKeyCode();
        if (pressedKey==KeyEvent.VK_LEFT) {
            characterx -= tree.getWidth()/8;
        }
        else if (pressedKey==KeyEvent.VK_RIGHT) {
            characterx += tree.getWidth()/8;
        }
        else if (pressedKey==KeyEvent.VK_DOWN) {
            charactery+=tree.getHeight()/8;
        }
        else if (pressedKey==KeyEvent.VK_UP) {
            charactery-=tree.getHeight()/8;
        }

        if (checkCollision()) {
            // Çakışma durumunda karakterin konumunu eski konumuna geri al
            if (pressedKey==KeyEvent.VK_LEFT) {
                characterx += tree.getWidth()/8;
            }
            else if (pressedKey==KeyEvent.VK_RIGHT) {
                characterx -= tree.getWidth()/8;
            }
            else if (pressedKey==KeyEvent.VK_DOWN) {
                charactery-=tree.getHeight()/8;
            }
            else if (pressedKey==KeyEvent.VK_UP) {
                charactery+=tree.getHeight()/8;
            }
        }
        repaint();
    }
    private boolean checkCollision() { //resimlerin çakışması kontrol ediliyor
        Rectangle characterRect = new Rectangle(characterx, charactery, characterimg.getWidth(), characterimg.getHeight());
        for (TreesCoordinate coordinatetree : treesCoordinates) {
            Rectangle treeRect = new Rectangle(coordinatetree.x, coordinatetree.y, tree.getWidth(), tree.getHeight());
            if (treeRect.intersects(characterRect)) {
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
        int[][] labirentMatrisi = {
                {1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
                {1,0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0,1},
                {1,0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0,1},
                {1,0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 0,1},
                {1,0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0,1},
                {1,1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0,1},
                {1,0, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0,1},
                {1,0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0,1},
                {1,0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0,1},
                {1,1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0,1},
                {1,1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,1},
        };
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i < 16; i++) {
                if (labirentMatrisi[k][i]==0)
                    g.drawImage(floorimg, i * tree.getWidth(),  k * tree.getHeight(), this);
                else {
                    g.drawImage(tree, i * tree.getWidth(),  k * tree.getHeight(), this);
                    treesCoordinates.add(new TreesCoordinate(i * tree.getWidth(),k * tree.getHeight()));
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
