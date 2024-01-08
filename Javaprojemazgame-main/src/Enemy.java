public class Enemy extends Entity{//rakiplerin ozelliklerini tutan sınıf
    private String name;
    Enemy(String enemyimgpath, int attack, int health, String name){
            super(enemyimgpath,attack,health);
            this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
