import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Maze extends JPanel implements KeyListener {//mapi burda oluşturuyorum ve klavye işlemleri burda algılanıcak

    ArrayList<ChestCoordinate> chestCoordinates = new ArrayList<>();//sandık kortdinatları
    ArrayList<WallCoordinate> wallCoordinates = new ArrayList<>();//duvar kordinatları
    static ArrayList<Enemy> enemies = new ArrayList<>();
    boolean finish;
    int kvsayaci=0;//kurt vurusu icin sayac
    final private BufferedImage wall;
    static int[][] labirentMatrisi;
    final private BufferedImage floorimg;
    final private BufferedImage exitImage;
    final private BufferedImage chestImage;
    Image customImage = new ImageIcon("Javaprojemazgame-main/src/Images/doors/stop_recall.png").getImage();
    ImageIcon icon = new ImageIcon(customImage);
    MyCharacter character = new MyCharacter("Javaprojemazgame-main/src/Images/player.png",2,20);
    static class WallCoordinate {
        int x,y;
        WallCoordinate(int x, int y){
            this.x=x; this.y=y;
        }
    }
    static class ChestCoordinate {
        int x,y;
        String buffpath,buffAbility;
        ChestCoordinate(int x, int y,String buffpath,String buffAbility){
            this.x=x; this.y=y;
            this.buffpath = buffpath;
            this.buffAbility=buffAbility;
            }
        }

    Maze()  {
        finish=false;
        chestCoordinates.add(new ChestCoordinate(64,64*6,"Javaprojemazgame-main/src/Images/sword.png","attack"));
        chestCoordinates.add(new ChestCoordinate(64*5,64,"Javaprojemazgame-main/src/Images/strawberry.png","health"));
        chestCoordinates.add(new ChestCoordinate(12*64,64*3,"Javaprojemazgame-main/src/Images/meat_ration.png","health"));//eski resim yerine et koydum ,daha mantıklı
        try {
            floorimg = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/grass_0_new.png"));
            wall =ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/snake_2.png"));
            exitImage = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/doors/return_depths.png"));
            chestImage = ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/chest.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setBackground(Color.black);
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/deep_troll_shaman.png",5,10,"Saman Trol"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/halfling_new.png",2,10,"Halfing"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/hill_giant_new.png",4,10,"Dev"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/rock_troll.png",20,15,"Kaya Trol"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/salamander_stormcaller.png",2,10,"Salamander"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/sphinx_new.png",5,25,"Ejderha"));
        enemies.add(new Enemy("Javaprojemazgame-main/src/Images/enemies/stone_giant_new.png",2,10,"Tas Dev"));
        labirentMatrisi = new int[][]{ //0 lar yol 1 ler duvar 2 ler dusman 3 cıkis 4 sandik
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 2, 4, 1, 1, 0, 0, 2, 0, 0, 1, 0, 1},
                {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 4, 1, 2, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1},
                {1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1},
                {1, 4, 1, 0, 0, 0, 1, 1, 0, 1, 0, 2, 0, 1, 0, 1},
                {1, 2, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 2, 1},
                {1, 1, 1, 1, 1, 0, 0, 0, 2, 0, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1},
        };
        repaint();//her sey tanımlandıktan sonra cizdirme
    }


    @Override
    public void keyPressed(KeyEvent e){//tus algılama haraket icin
        if(character.getHealth() > 0 && !finish) {
            int pressedKey = e.getKeyCode();
            if (pressedKey == KeyEvent.VK_LEFT) {
               character.setCoordinatex(character.getCoordinatex() - 8);
                try {
                    character.leftanimation(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (pressedKey == KeyEvent.VK_RIGHT) {
                character.setCoordinatex(character.getCoordinatex() + 8);
                try {
                    character.rightanimation(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (pressedKey == KeyEvent.VK_DOWN) {
                try {
                    character.downanimation(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                character.setCoordinatey(character.getCoordinatey() + 8);
            } else if (pressedKey == KeyEvent.VK_UP) {
                try {
                    character.upanimation(1);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                character.setCoordinatey(character.getCoordinatey() - 8);
            }

            if (checkCollision()) {
                // Çakışma durumunda karakterin konumunu eski konumuna geri al
                if (pressedKey == KeyEvent.VK_LEFT) {
                    try {
                        character.leftanimation(-1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    character.setCoordinatex(character.getCoordinatex() + 8);
                } else if (pressedKey == KeyEvent.VK_RIGHT) {
                    try {
                        character.rightanimation(-1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    character.setCoordinatex(character.getCoordinatex() - 8);
                } else if (pressedKey == KeyEvent.VK_DOWN) {
                    try {
                        character.downanimation(-1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    character.setCoordinatey(character.getCoordinatey() - 8);
                } else if (pressedKey == KeyEvent.VK_UP) {
                    try {
                        character.upanimation(-1);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    character.setCoordinatey(character.getCoordinatey() + 8);
                }
            }
            repaint();
        }
    }
    private boolean checkCollision() { //resimlerin çakışması kontrol ediliyor
        Rectangle characterRect = new Rectangle(character.getCoordinatex(), character.getCoordinatey(), 64,64);
        for (WallCoordinate coordinatwall : wallCoordinates) {
            Rectangle wallRect = new Rectangle(coordinatwall.x, coordinatwall.y, wall.getWidth()*2, wall.getHeight()*2);
            if (wallRect.intersects(characterRect)) {
                return true; // Çakışma durumu
            }
            for (Enemy enemy : enemies)//düsmanla cakısma kontrol
            {
                Rectangle enemyRect = new Rectangle(enemy.getCoordinatex(),enemy.getCoordinatey(),64,64);
                if (enemyRect.intersects(characterRect)) {
                    fight(enemy);
                    return true;
                }
            }
            for (ChestCoordinate chest : chestCoordinates)//chestle cakisma
            {
                Rectangle chestRect = new Rectangle(chest.x,chest.y,64,64);
                if (chestRect.intersects(characterRect)&&labirentMatrisi[chest.y / 64][chest.x / 64] ==4) {
                    Image buffimg = new ImageIcon(chest.buffpath).getImage();
                    ImageIcon bufficon = new ImageIcon(buffimg);
                    JOptionPane optionPane = new JOptionPane("Bir Buff kazandın!", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, bufficon, new Object[]{}, null);
                    JDialog dialog = optionPane.createDialog(this, "Tebrikler");
                    dialog.setModal(false);
                    dialog.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    dialog.setVisible(true);
                    if(chest.buffAbility.equals("attack"))//atak buffı alınırsa
                        character.setAttack(character.getAttack()+3);
                    else//heal buffı alınırsa
                        character.setHealth(20);
                    labirentMatrisi[chest.y / 64][chest.x / 64] =0;//buff alınınca sandıgı silmek icin
                    return true;
                }
            }

        }
        return false; // Çakışma yok

    }
    public void fight(Enemy enemy){
        JFrame panel = new JFrame();//savas ekranı
        panel.setLayout(new GridLayout(2, 1));//sayfa duzeni icin
        JLabel imageLabel = new JLabel(new ImageIcon(enemy.getImg()));
        panel.add(imageLabel);
        String message = "<html><div style='text-align: center;'>Düşmanla karşılaşıldı!<br>";
        message += "Düşman: " + enemy.getName() +"&nbsp;&nbsp;";
        message += "Sağlık: " + enemy.getHealth() +"<br>";
        message += "Saldırı: " + enemy.getAttack() +"&nbsp;&nbsp;" ;
        JLabel textLabel = new JLabel(message);
        textLabel.setForeground(Color.white);
        panel.add(textLabel);

        JButton attackButton = new MyJButton("Saldır");
        JButton ultiButton = new MyJButton("Kurt vuruşu");
        ultiButton.setEnabled(false);
        attackButton.addActionListener(e -> {
            enemy.setHealth(enemy.getHealth() - character.getAttack());
           character.setHealth(character.getHealth()-enemy.getAttack());
            // Saldırı sonrası güncellenmiş bilgileri göster
            String updatedMessage = "<html><div style='text-align: center;'>Düşmanla karşılaşıldı!<br>";
            updatedMessage += "Düşman: " + enemy.getName() +"&nbsp;&nbsp;";
            updatedMessage += "Sağlık: " + enemy.getHealth() +"<br>";
            updatedMessage += "Saldırı: " + enemy.getAttack() +"&nbsp;&nbsp;" ;
            textLabel.setText(updatedMessage);
            ultiButton.setEnabled(false);
            kvsayaci++;
            if (kvsayaci%2==0){
                ultiButton.setEnabled(true);
            }
            if (character.getHealth()<0) {

                panel.dispose();
                try {
                    character.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/mezar.png")));
                    repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane optionPane = new JOptionPane("Kaybettiniz", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                JDialog dialog = optionPane.createDialog(this, "Üzgünüz :(");
                dialog.setModal(false);
                optionPane.setIcon(icon);
                dialog.setVisible(true);
                Gamewindow.maze.setFocusable(true);
            }
            else if(enemy.getHealth()<=0){
                panel.dispose();
                Gamewindow.maze.setFocusable(true);
                enemy.setHealth(0);
                repaint();}

        });
        ultiButton.addActionListener(e -> { //ulti butonu çift hasar verir
            enemy.setHealth(enemy.getHealth() - character.getAttack()*2);
            // Saldırı sonrası güncellenmiş bilgileri göster
            String updatedMessage = "<html><div style='text-align: center;'>Düşmanla karşılaşıldı!<br>";
            updatedMessage += "Düşman: " + enemy.getName() +"&nbsp;&nbsp;";
            updatedMessage += "Sağlık: " + enemy.getHealth() +"<br>";
            updatedMessage += "Saldırı: " + enemy.getAttack() +"&nbsp;&nbsp;" ;
            ultiButton.setEnabled(false);//basıldıktan sonra aktifligini kaldırmak icin
            textLabel.setText(updatedMessage);
            if (character.getHealth()<0) {

                panel.dispose();
                try {
                    character.setImg(ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/mezar.png")));
                    repaint();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane optionPane = new JOptionPane("Kaybettiniz", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                JDialog dialog = optionPane.createDialog(this, "Üzgünüz :(");
                dialog.setModal(false);
                optionPane.setIcon(icon);
                dialog.setVisible(true);
                Gamewindow.maze.setFocusable(true);
            }
            else if(enemy.getHealth()<=0){
                panel.dispose();
                Gamewindow.maze.setFocusable(true);
                enemy.setHealth(0);
                repaint();}

        });
        panel.add(attackButton);
        panel.add(ultiButton);
        panel.getContentPane().setBackground(Color.black);
        panel.setLocation(400,150);
        panel.setSize(500,250);
        panel.setFocusable(true);
        Gamewindow.maze.setFocusable(false);//arkada kalan ekranda islem yapılmasını engelliyor
        panel.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//sekmeyi kapatmayı engelleme
        panel.setVisible(true);

    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void paint(Graphics g) {//cizdirme fonksiyonu
        super.paint(g);
        int enemyIndex =0;
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i < 16; i++) {
                if (labirentMatrisi[k][i]==0){
                    g.drawImage(floorimg, i * 64,  k * 64, 64,64,this);
                }
                else if(labirentMatrisi[k][i]==2){
                    g.drawImage(floorimg, i * 64,  k * 64,64,64, this);
                    if(enemies.get(enemyIndex).getHealth()!=0){
                        g.drawImage(enemies.get(enemyIndex).getImg(), i * 64,  k * 64, 64,64,this);//???
                        enemies.get(enemyIndex).setCoordinatex(i*64); enemies.get(enemyIndex).setCoordinatey(k * 64);
                    }
                    else {
                        labirentMatrisi[k][i]=0;
                        enemies.remove(enemies.get(enemyIndex));
                        enemyIndex--;
                        repaint();
                    }
                    enemyIndex++;

                }
                else if (labirentMatrisi[k][i] == 1){
                    g.drawImage(wall, i * 64,  k * 64,64,64, this);
                    wallCoordinates.add(new WallCoordinate(i * 64,k * 64));
                }
                else if (labirentMatrisi[k][i] == 3) { // Çıkış kapısı koordinatına geldiğinizde
                    g.drawImage(floorimg, i * 64,  k * 64,64,64, this);
                    g.drawImage(exitImage, i * 64, k * 64, 64, 64, this);
                }
                else if (labirentMatrisi[k][i] == 4) {
                    g.drawImage(floorimg, i * 64,  k * 64,64,64, this);
                    g.drawImage(chestImage, i * 64,  k * 64,64,64, this);
                }
            }
            try {
                g.drawImage(characterHealthBar(character.getHealth()),character.getCoordinatex()+5,character.getCoordinatey()-92,54,86,this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (labirentMatrisi[character.getCoordinatey() / 64][character.getCoordinatex() / 64] == 3) { //çıkışa ulaşınca tebriller yazısı çıkıyor
                JOptionPane.showMessageDialog(this, "Tebrikler! Çıkışa ulaştın!", "Tebrikler", JOptionPane.INFORMATION_MESSAGE, icon);
                labirentMatrisi[character.getCoordinatey() / 64][character.getCoordinatex() / 64]=0;
                finish=true;
            }
            g.drawImage(character.getImg(),character.getCoordinatex(),character.getCoordinatey(),64,64,this);
        }
    }
    public void repaint()
    {
        super.repaint();
    }
    public BufferedImage characterHealthBar(int health) throws IOException{
        BufferedImage healthBarimg=null;
        if (health == 20){
            healthBarimg= ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/enemies/no_damage.png"));
        }
        else if (health>16 && health<20){
            healthBarimg= ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/damage_meter_lightly_damaged.png"));
        }
        else if (health>12){
            healthBarimg= ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/damage_meter_moderately_damaged.png"));
        }
        else if(health>8)
        {
            healthBarimg= ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/damage_meter_heavily_damaged.png"));
        }
        else if(health>4)
        {
            healthBarimg= ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/damage_meter_severely_damaged.png"));
        }
        else if(health>0){
            healthBarimg=ImageIO.read(new FileInputStream("Javaprojemazgame-main/src/Images/damage_meter_almost_dead.png"));
        }
        return healthBarimg;
    }
}