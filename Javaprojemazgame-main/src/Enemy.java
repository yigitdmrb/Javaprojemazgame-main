import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Enemy {//rakiplerin ozelliklerini tutan sınıf
    int attack, defense,health,coordinatex,coordinatey;
    String name;
    BufferedImage enemyimg ;

    Enemy(String enemyimgpath, int attack, int defense, int health, String name){
        try {
            this.enemyimg= ImageIO.read(new FileInputStream(enemyimgpath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.attack=attack;
        this.defense = defense;
        this.health=health;
        this.name=name;
    }

}
