import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Enemies {
    int attack,defance,health;
    String name;
    BufferedImage enemyimg ;

    Enemies(String enemyimgpath,int attack,int defance,int health,String name){
        try {
            this.enemyimg= ImageIO.read(new FileInputStream(""+enemyimgpath+""));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.attack=attack;
        this.defance=defance;
        this.health=health;
        this.name=name;
    }

}
