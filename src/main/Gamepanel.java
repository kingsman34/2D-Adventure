package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;

public class Gamepanel extends JPanel implements Runnable {

    final int originalTileSize = 16;//16X16
    final int scale = 3;

    public final int tileSize = originalTileSize*3;//48X48tile
    public final int maxScreencol = 16;
    public final int maxScreenrow = 12;
    public final int screenWidth = tileSize*maxScreencol;//768pixels
    public final int screenHeight = tileSize*maxScreenrow;//576pixels

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Sound sound = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;

    public Player player = new Player(this,keyH);
    public SuperObject obj[] = new SuperObject[10];

    public Gamepanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setUpGame(){
        aSetter.setObject();
        playMusic(0);
    }
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread!=null){
            currentTime = System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            lastTime=currentTime;
            if(delta>=1){
                update();
                repaint();
                delta--;
            }
        }
    }
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        for(int i = 0;i< obj.length;i++){
            if(obj[i]!=null)
                obj[i].draw(g2,this);
        }
        player.draw(g2);
        g2.dispose();
    }
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();

    }
    public void stopMusic(){
        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
