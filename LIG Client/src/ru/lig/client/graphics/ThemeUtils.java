/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.lig.client.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import javax.swing.JTextArea;
import ru.lig.client.Client_Loader;
import static ru.lig.client.graphics.Frame.listeners;
import ru.lig.client.graphics.components.Button;
import ru.lig.client.graphics.components.Panel;
import ru.lig.client.graphics.components.SpecialTextArea;
import ru.lig.client.graphics.components.SpecialTextField;
import ru.lig.client.utils.BaseUtils;
import ru.lig.client.utils.Ticker;

/**
 *
 * @author Константин
 */
public class ThemeUtils {
    
    public Frame frame;
    
    public static final int
            categoriesX = 182, categoriesY = 225, categoriesWidth = 112, categoriesHeight = 36,
            questionsX = categoriesX + categoriesWidth + 10, questionsY = categoriesY + 5,
            sendAnswerX = 322, sendAnswerY = 230,
            answerX = categoriesX + categoriesWidth + 100, answerY = 450,
            questionX = categoriesX, questionY = categoriesY,
            roundX = 0, roundY = 0,
            pointsX = 0, pointsY = 0,
            hideX = 560, hideY = 2, closeX = 951, closeY = 2, dragButtonW = 35, dragButtonH = 24,
            draggerW = 640, draggerH = 30,
            scoresX = categoriesX + categoriesWidth + 155, scoresY = 500, scoresW = 100, scoresH = 20,
            timeX = categoriesX + categoriesWidth + 200, timeY = 460, timeW = 100, timeH = 20;
    
    public ThemeUtils(Frame frame) {
        this.frame = frame;
        int round = Client_Loader.round;
        int width = 91, height = 27;
        for(int i = 0; i < frame.questions.length; i++) {
            int row = i / 5, column = i % 5, cost = round * BaseUtils.multiplier * (column + 1);
            int x = questionsX + column * (width + 5), y = questionsY + row * (categoriesHeight + 5);
            frame.questions[i] = new Button(cost + "", 1, x, y, width, height);
        }
        width = 120; height = 36;
        frame.sendAnswer = new Button("Подтвердить", 2, sendAnswerX, sendAnswerY, width, height);
        frame.answer = new SpecialTextField(answerX, answerY, 96, 36, Color.GRAY, "Ваш ответ..");
        frame.question = new SpecialTextArea(questionX, questionY, 620, 180, Color.GRAY, "");
        frame.question.setVisible(false);
        frame.scores = new SpecialTextArea(scoresX, scoresY, scoresW, scoresH, Color.GRAY, "");
        frame.time = new SpecialTextArea(timeX, timeY, timeW, timeH, Color.GRAY, "");
        frame.round = new JTextArea("Раунд " + round);
        frame.round.setBounds(roundX, roundY, frame.round.getWidth(), frame.round.getHeight());
        frame.points.setBounds(pointsX, pointsY, frame.points.getWidth(), frame.points.getHeight());
        for(int i = 0; i < frame.categories.length; i++) {
            frame.categories[i] = new Button(Client_Loader.categories[i], 1, categoriesX, categoriesY + i * (categoriesHeight + 5),
                    categoriesWidth, categoriesHeight);
            frame.categories[i].setEnabled(false);
            frame.categories[i].setBackground(null);
        }
        for(int i = 0; i < frame.questions.length; i++) frame.questions[i].addActionListener(listeners);
        frame.sendAnswer.addActionListener(listeners);
        frame.answer.addFocusListener(listeners);
        frame.answer.addKeyListener(listeners);
        Panel p = frame.panel;
        p.add(frame.answer);
        p.add(frame.question);
        p.add(frame.round);
        p.add(frame.points);
        for(int i = 0; i < frame.categories.length; i++) p.add(frame.categories[i]);
        for(int i = 0; i < frame.questions.length; i++) p.add(frame.questions[i]);
        p.add(frame.sendAnswer);
        frame.dragger.setBounds(0, 0, draggerW, draggerH);
        frame.dragger.title.setForeground(Color.DARK_GRAY);
        frame.dragger.title.setText("Клиент онлайн-игры LIG версии " + Client_Loader.version);
        frame.hide.load(FileUtils.dragbutton, true);
        frame.close.load(FileUtils.dragbutton, false);
        frame.hide.setBounds(hideX, hideY, dragButtonW, dragButtonH);
        frame.close.setBounds(closeX, closeY, dragButtonW, dragButtonH);
        //p.add(frame.hide);
        p.add(frame.close);
        p.add(frame.dragger);
        p.add(frame.scores);
        p.add(frame.time);
        setTableState();
    }
    
    public void setQuestionState() {
        frame.question.setText(Client_Loader.currentQuestion);
        frame.question.setVisible(true);
        for(int i = 0; i < frame.categories.length; i++) frame.categories[i].setVisible(false);
        for(int i = 0; i < frame.questions.length; i++) frame.questions[i].setVisible(false);
        frame.answer.setVisible(true);
        frame.sendAnswer.setVisible(true);
        frame.time.setText("Время: 60");
        frame.time.setVisible(true);
        new Ticker();
        frame.answer.setText("Ваш ответ..");
        frame.scores.setText("Очки: " + Client_Loader.points + "");
        int currentId = Client_Loader.currentQuestionId;
        if(FileUtils.questionImages.containsKey(currentId)) {
            BufferedImage[] images = FileUtils.questionImages.get(currentId);
            for(int i = 0; i < images.length && i < 4 && images[i] != null; i++) {
                ImageFrame iF = frame.ifs[i];
                iF.panel = new Panel(frame, iF.width, iF.height, images[i]);
                int x, y;
                x = (i & 1) == 0 ? (int) frame.getBounds().getMinX() - iF.width - 5 : (int) frame.getBounds().getMaxX() + 5;
                y = i > 1 ? (int) frame.getBounds().getMaxY() - iF.height : (int) frame.getBounds().getMinY();
                iF.update(x, y);
                iF.add(iF.panel);
                //iF.panel.add(iF.dragger);
                if(!Client_Loader.noImages) iF.setVisible(true);
            }
        }
    }
    
    public void setTableState() {
        frame.question.setVisible(false);
        for(int i = 0; i < frame.categories.length; i++) frame.categories[i].setVisible(true);
        for(int i = 0; i < frame.questions.length; i++) {
            frame.questions[i].setVisible(true);
            frame.questions[i].setEnabled(false);
        }
        frame.answer.setVisible(false);
        frame.sendAnswer.setVisible(false);
        Iterator<Integer> iterator = Client_Loader.questions.iterator();
        while(iterator.hasNext()) frame.questions[iterator.next()].setEnabled(true);
        for(int i = 0; i < frame.questions.length; i++) if(!frame.questions[i].isEnabled())
            frame.questions[i].setText("N/a");
        frame.scores.setText("Очки: " + Client_Loader.points + "");
        frame.time.setVisible(false);
        for(int i = 0; i < 4; i++) {
            ImageFrame iF = frame.ifs[i];
            iF.setVisible(false);
            if(iF.panel != null) {
                iF.panel.remove(iF.dragger);
                iF.remove(iF.panel);
                iF.panel = null;
            }
            iF.update(iF.x, iF.y);
        }
    }
    
}
