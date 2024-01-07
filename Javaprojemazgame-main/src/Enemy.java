public class Enemy extends Entity{//rakiplerin ozelliklerini tutan sınıf
    String name;
    Enemy(String enemyimgpath, int attack, int health, String name){
            super(enemyimgpath,attack,health);
            this.name=name;
    }
}
