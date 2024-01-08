import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Entity {//varlık sınıfım burdan kendi karakterimiz ve dusmanlı türeticez ara sınıf olduğu icin abstract
     private int attack,health,coordinatex,coordinatey;
    private BufferedImage img ;
    Entity(String imgpath, int attack, int health){
        try {
            this.img= ImageIO.read(new FileInputStream(imgpath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.attack=attack;
        this.health=health;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCoordinatex() {
        return coordinatex;
    }

    public void setCoordinatex(int coordinatex) {
        this.coordinatex = coordinatex;
    }

    public int getCoordinatey() {
        return coordinatey;
    }

    public void setCoordinatey(int coordinatey) {
        this.coordinatey = coordinatey;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
