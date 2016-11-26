
package com.company;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.misc.Launcher;
import sun.audio.*;

import java.io.*;

import java.util.Random;

/**
 * Created by Oussama on 24/11/2016.
 */
public class MathTeacher extends Application implements EventHandler {
    Stage teacher;

    //first stage stuff
    Scene scene;
    VBox layout;

    //Moving stage stuff
    Scene scene2;
    VBox layout2;

    //stage stuff
    Label question;
    Label Title;
    TextField answer;
    Label correction;
    Button submit;
    Label currentScore;

    int score = 20;
    int stage = 1;

    Random r = new Random();

    String generated;

    boolean next = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        teacher = primaryStage;

        Title = new Label("MATH TEACHER");
        Title.setFont(Font.font("Arial", 40));
        Title.setTranslateY(-50);
        Title.setUnderline(true);

        currentScore = new Label("Stage: " + stage + " / Score: " + score);
        currentScore.setFont(Font.font("Arial", 20));

        submit = new Button();
        submit.setText("Submit");
        submit.setOnAction(this);

        answer = new TextField();
        answer.setPromptText("Type your answer here...");
        answer.setAlignment(Pos.CENTER);

        question = new Label("Questions...");
        question.setFont(Font.font(30));

        correction = new Label();
        correction.setFont(Font.font(30));

        layout = new VBox(50);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(Title, question, answer, submit, correction, currentScore);

        layout2 = new VBox(50);
        layout2.setAlignment(Pos.CENTER);


        scene2 = new Scene(layout2, 600, 600);

        scene = new Scene(layout, 600, 600);
        teacher.setScene(scene);
        teacher.setResizable(false);
        teacher.show();
        generated = generate();
    }

    @Override
    public void handle(Event event) {

        if (event.getSource() == submit) {
            if (!next) {
                if (answer.getText().equals(generated)) {
                    correction.setText("Correct !");
                    Music(0);
                    score += 5;
                    currentScore.setText("Stage: " + stage + " / Score: " + score);
                } else {
                    correction.setText("Wrong, Correct answer is: " + generated);
                    Music(1);
                }
                submit.setText("Next");
                next = true;

            } else {
                answer.setText("");
                generated = generate();
                next = false;
                submit.setText("Submit");
                correction.setText("");

                if (score >= stage * 25) {
                    stage += 1;
                    question.setText("Congratulations, Level up !");
                    question.setAlignment(Pos.CENTER);
                    submit.setText("Continue...");
                    currentScore.setText("Stage: " + stage + " / Score: " + score);
                    layout2.getChildren().addAll(Title, question, submit, correction, currentScore);
                    teacher.setScene(scene2);
                    teacher.show();
                    Music(2);

                    submit.setOnAction(event1 -> {
                        generated = generate();
                        layout.getChildren().removeAll(Title, question, answer, submit, correction, currentScore);
                        layout.getChildren().addAll(Title, question, answer, submit, correction, currentScore);
                        teacher.setScene(scene);
                        teacher.show();
                        submit.setOnAction(this);
                        submit.setText("Submit");
                    });
                }
            }
        }
    }

    public String generate() {
        int first, second, op, result = 0;
        String generated = "";
        char operation = ' ';

        do {
            first = r.nextInt(10 * stage);
            second = r.nextInt(10 * stage);
            op = r.nextInt(3);

            if (op == 0) {
                result = first + second;
                operation = '+';
            }
            if (op == 1) {
                result = first - second;
                operation = '-';
            }
            if (op == 2) {
                result = first * second;
                operation = 'x';
            }
        } while (result < 0 || result > (100 * stage));

        generated = String.valueOf(result);
        question.setText(first + " " + operation + " " + second + " = ?");

        System.out.println(generated);
        return generated;
    }

    public static void Music(int t) {
        AudioPlayer joueur = AudioPlayer.player;

        AudioStream background = null;

        AudioData data;

        AudioDataStream output = null;

        try {

            if (t == 0) {
                background = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\IdeaProjects\\Tutoriel\\Correct-answer.wav"));
            }

            if (t == 1) {
                background = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\IdeaProjects\\Tutoriel\\Wrong-answer-sound-effect.wav"));
            }
            if (t == 2) {
                background = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\IdeaProjects\\Tutoriel\\Next-Level-Sound.wav"));
            }
            data = background.getData();
            output = new AudioDataStream(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        joueur.start(output);

    }
}
