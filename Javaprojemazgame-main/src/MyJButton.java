import javax.swing.*;
import java.awt.*;
public class MyJButton extends JButton {//butonlar icin belirli sablon olusturdum
    MyJButton(String text){
        super(text);
        ozellestir();
    }
    public void ozellestir(){
        setBackground(Color.darkGray);
        setForeground(Color.white);
    }

}
