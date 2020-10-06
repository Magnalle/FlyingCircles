package ru.geekbrains.java_two.lesson_a;

import javax.swing.*;
import java.awt.*;
import java.awt.desktop.SystemEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameCanvas extends JPanel {

    private MainCircles controller;
    long lastFrameTime;
    private static final int FPS_SLEEP_TIME = 17;
    private static final float COLOR_CHANGE_DARK = 0.3f, COLOR_CHANGE_BRIGHT = 1.f;
    private float[] deltaColor = {0.5f, 0.5f, 0.5f};

    GameCanvas(MainCircles controller) {
        this.controller = controller;
        lastFrameTime = System.nanoTime();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) { // while (true) {
        super.paintComponent(g);
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastFrameTime) * 0.000000001f;
        controller.onDrawFrame(this, g, deltaTime);
        lastFrameTime = currentTime;
        try {
            Thread.sleep(FPS_SLEEP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();                              // }
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        //System.out.println(e.toString());
        if(e.getID() == MouseEvent.MOUSE_RELEASED)
            if(e.getButton() == MouseEvent.BUTTON1)
                controller.addSprite();
            else if(e.getButton() == MouseEvent.BUTTON3)
                controller.deleteSprite();

    }

    public void changeBackgroundColor(float deltaTime)
    {
        float[] components = this.getBackground().getRGBColorComponents(null);
        for(int i = 0; i < 3; i++) {
            components[i] = components[i] + (float)(deltaColor[i] * deltaTime * Math.random());
            if(components[i] < COLOR_CHANGE_DARK){
                components[i] = COLOR_CHANGE_DARK;
                deltaColor[i] = -deltaColor[i];
            }
            if(components[i] > COLOR_CHANGE_BRIGHT) {
                components[i] = COLOR_CHANGE_BRIGHT;
                deltaColor[i] = -deltaColor[i];
            }
        }
        this.setBackground(new Color(components[0], components[1], components[2]));
    }

    public int getLeft() { return 0; }
    public int getRight() { return getWidth() - 1; }
    public int getTop() { return 0; }
    public int getBottom() { return getHeight() - 1; }

}
