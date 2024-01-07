import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Entity {//varlık sınıfım burdan kendi karakterimiz ve dusmanlı türeticez ara sınıf olduğu icin abstract
    int attack,health,coordinatex,coordinatey;
    BufferedImage img ;
    Entity(String enemyimgpath, int attack, int health){
        try {
            this.img= ImageIO.read(new FileInputStream(enemyimgpath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.attack=attack;
        this.health=health;
    }
}
