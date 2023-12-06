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

public class Forest extends JPanel implements KeyListener, EventListener {//mapi burda oluşturuyorum ve klavye işlemleri burda algılanıcak
    static class TreesCoordinate{
        int x,y;
        TreesCoordinate(int x, int y){
            this.x=x; this.y=y;
        }
    }

    final private BufferedImage tree;
    final private BufferedImage characterimg;
    static int characterx =45;
    static int charactery =0;
    static int[] nottree= new int[5];

    TreesCoordinate[] treesCoordinates = new TreesCoordinate[25];


    {
        try {
            characterimg = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/character.png"));
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
            characterx -= tree.getWidth();
        }
        else if (pressedKey==KeyEvent.VK_RIGHT) {
            characterx += tree.getWidth();
        }
        else if (pressedKey==KeyEvent.VK_DOWN) {
            charactery+=tree.getHeight();
        }

        if (checkCollision()) {
            // Çakışma durumunda karakterin konumunu eski konumuna geri al
            if (pressedKey == KeyEvent.VK_LEFT) {
                characterx += tree.getWidth();
            } else if (pressedKey == KeyEvent.VK_RIGHT) {
                characterx -= tree.getWidth();
            } else if (pressedKey == KeyEvent.VK_DOWN) {
                charactery -= tree.getHeight();
            }
        }
        repaint();
    }
    private boolean checkCollision() { //resimlerin çakışması kontrol ediliyor
        Rectangle characterRect = new Rectangle(characterx, charactery, characterimg.getWidth(), characterimg.getHeight());
        Rectangle treeRect = new Rectangle(15, 30, tree.getWidth(), tree.getHeight());
        return characterRect.intersects(treeRect);
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);


        for (int k = 0; k <= 4; k++) {

            for (int i = 1; i <= 4; i++) {
                if (i == nottree[k])
                    g.fillOval(15 + i * tree.getWidth(), 30+k*tree.getHeight()*2, tree.getWidth(), tree.getHeight());
                else {
                    g.drawImage(tree, 15 + i * tree.getWidth(), 30 + k * tree.getHeight() * 2, this);

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
