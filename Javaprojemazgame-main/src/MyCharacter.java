import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;

public class MyCharacter extends Entity {
    static int[] yon_sayaclari={0,0,0,0};
    MyCharacter(String characterimgpath, int attack, int health){
        super(characterimgpath,attack,health);
        super.setCoordinatex(64);  super.setCoordinatey(64);
    }
    MyCharacter(int attack, int health){
        super("Javaprojemazgame-main/src/Images/player.png",attack,health);
        super.setCoordinatex(64);  super.setCoordinatey(64);
    }
    public void leftanimation(int input)throws IOException {
            yon_sayaclari[0] += input;
            if (yon_sayaclari[0] % 2 == 0)
                this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player_left.png")));
            else
                this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player_left_2.png")));
    }
    public void rightanimation(int input)throws IOException {
        yon_sayaclari[1]+=input;
        if(yon_sayaclari[1]%2==0)
            this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player_right.png")));
        else
            this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player_right_2.png")));
    }
    public void upanimation(int input)throws IOException {
        yon_sayaclari[2]+=input;
        if(yon_sayaclari[2]%2==0)
            this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player_back.png")));
        else
            this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player_back_2.png")));
    }
    public void downanimation(int input)throws IOException {
        yon_sayaclari[3]+=input;
        if(yon_sayaclari[3]%2==0)
            this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player.png")));
        else
            this.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/player_2.png")));
    }
    public String getCharacterStats() {
        return "Sağlık: " + getHealth() + "\n" +
                "Saldırı: " + getAttack() + "\n";
    }
}

