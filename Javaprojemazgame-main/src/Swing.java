import javax.swing.*;//tüm swing kütüphaneleri eklendi swing arayüz için
import java.awt.*;//bu nerden geldi bilmiyom ama lazım sanırım slşkdsldfsfdkş
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Swing {
    public static void main(String[] args) {
        JFrame window = new JFrame("Maze Game");//pencere nesnesi ve contructda baslık gönderiliyor
        window.setSize(500,550);//pencere boyutu
        window.setLocation(400,150);//sol üst köşeye olan uzaklık
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);// çarpıya baasınca programı sonlandırır

        window.setResizable(false);//ekran boyutunu değiştirmeyi engeller
        window.getContentPane().setLayout(new GridLayout(8,1));//eklenen şeyler düzenli olsun diye gerekliymiş

        JPanel buttons= new JPanel(new FlowLayout());
        JButton button1 =new JButton("up");//button nesenesi oluşturdum
        buttons.add(button1);//butonu pencere ekletiyorum
        JButton button2 =new JButton("left");
        buttons.add(button2);
        JButton button3 =new JButton("right");
        buttons.add(button3);
        JButton button4 =new JButton("down");
        buttons.add(button4);
        ImageIcon icon = new ImageIcon("Javaprojemazgame-main/src/Images/tree_icon2.png");
        JLabel button1control = new JLabel();
        buttons.add(button1control);

        JPanel trees= new JPanel(new FlowLayout());
        JLabel[] treeArray = new JLabel[4];
        for (int i=0;i<4;i++ )
        {
            treeArray[i]= new JLabel();
            treeArray[i].setIcon(icon);

            if (i==2){
                JLabel nottree = new JLabel("    ");
                trees.add(nottree);
            }
            else
            {
                trees.add(treeArray[i]);
            }

        }

        JPanel trees2 = new JPanel(new FlowLayout());
        JLabel[] treeArray2 = new JLabel[4];
        for (int i=0;i<4;i++ )
        {
            treeArray2[i]= new JLabel();
            treeArray2[i].setIcon(icon);

            if (i==1){
                JLabel nottree2 = new JLabel("    ");
                trees2.add(nottree2);
            }
            else
            {
                trees2.add(treeArray2[i]);
            }

        }


        window.add(buttons);
        window.add(trees);
        window.add(trees2);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//buton1 e tıklandığında olucaklar
                button1control.setText(button1.getText()+" e tıklandı");
                JOptionPane.showMessageDialog(null,button1.getText()+" e tıklandı");//ekrana bilgi yazdırır
            }
        });
        window.setVisible(true);//pencere gözükmesi için yazılmazsa programı göremeyiz
    }
}