public class Enemy extends Entity{//rakiplerin ozelliklerini tutan sınıf
    String name;

    Enemy(String enemyimgpath, int attack, int defense, int health, String name){
            super(enemyimgpath,attack,defense,health);
            this.name=name;
    }


}
