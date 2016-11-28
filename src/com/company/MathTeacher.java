
package com.company;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sun.misc.Launcher;
import sun.audio.*;

import java.awt.*;
import java.awt.Color;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    Scene levelUpScene;
    VBox levelUpLayout;

    //opening scene
    Scene openingScene;
    VBox openingLayout;

    //stage stuff
    Label question;
    Label Title;
    TextField answer;
    Label correction;
    Button submit;
    Label currentScore;

    int score = 0;
    int stage = 1;

    Random r = new Random();

    String generated;
    String name;
    boolean next = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        teacher = primaryStage;

      /*  Image image = null;
        try {
            image = new Image("C:\\Users\\Oussama\\IdeaProjects\\Tutoriel\\Test.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageView  v = new ImageView();
        v.setImage(image);
        v.setTranslateX(200);
        v.setTranslateY(200);
        //v.setFitWidth(600);
        */

        Title = new Label("MATH TEACHER");
        Title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 40));
        Title.setTranslateY(-30);
        //Title.setUnderline(true);
        Title.setTextFill(Paint.valueOf("#FFFFFF"));

        currentScore = new Label("Stage: " + stage + " / Score: " + score);
        currentScore.setFont(Font.font("", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 25));
        currentScore.setTextFill(Paint.valueOf("#FFFFFF"));

        submit = new Button();
        submit.setText("Start");
        submit.setOnAction(this);
        submit.setTextFill(Paint.valueOf("#ffffff"));
        submit.setFont(Font.font("", FontWeight.EXTRA_BOLD, 15));
        submit.setBackground(new Background(new BackgroundFill(Paint.valueOf("0098ECFF"), null, null)));

        answer = new TextField();
        answer.setMaxSize(200, 5);
        answer.setStyle("");
        answer.setPromptText("Type your answer here...");
        answer.setAlignment(Pos.CENTER);


        question = new Label("Fill in your name");
        question.setFont(Font.font("", FontWeight.EXTRA_BOLD, null, 30));
        question.setTextFill(Paint.valueOf("#ffffff"));
        question.setFont(Font.font("", FontWeight.EXTRA_BOLD, 40));

        correction = new Label();
        correction.setFont(Font.font(30));

        layout = new VBox(50);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(Title, question, answer, submit, correction, currentScore);
        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#009866FF"), CornerRadii.EMPTY, Insets.EMPTY)));

        levelUpLayout = new VBox(50);
        levelUpLayout.setAlignment(Pos.CENTER);
        levelUpLayout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#009866FF"), CornerRadii.EMPTY, Insets.EMPTY)));

        openingLayout = new VBox(50);
        openingLayout.setAlignment(Pos.CENTER);
        openingLayout.getChildren().addAll(Title, question,answer ,submit);
        openingLayout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#009866FF"), CornerRadii.EMPTY, Insets.EMPTY)));

        openingScene = new Scene(openingLayout, 600, 600);

        levelUpScene = new Scene(levelUpLayout, 600, 600);

        scene = new Scene(layout, 600, 600);


        teacher.setScene(openingScene);
        teacher.setResizable(false);
        teacher.show();
        //generated = generate();
    }

    @Override
    public void handle(Event event) {


        if (event.getSource() == submit) {
            if (submit.getText() == "Start") {
                layout.getChildren().removeAll(Title, question, answer, submit, correction, currentScore);
                layout.getChildren().addAll(Title, question, answer, submit, correction, currentScore);
                teacher.setScene(scene);
                generated = generate();
                submit.setText("Submit");
                name = answer.getText();
                answer.setText("");
                currentScore.setText(name+ ": Stage: " + stage + " / Score: " + score);

            } else {
                if (!next) {
                    if (answer.getText().equals(generated)) {
                        correction.setText("Great answer, "+name+ " !");
                        correction.setTextFill(Paint.valueOf("#ffffff"));
                        Music(0);
                        score += 5;
                        currentScore.setText(name+ ": Stage: " + stage + " / Score: " + score);
                    } else {
                        correction.setText("Wrong, Correct answer is: " + generated);
                        correction.setTextFill(Paint.valueOf("#EE1515FF"));
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
                        if (stage == 5) question.setText("Congratulations, Level up !\n YOU UNLOCKED: DIVISION");
                        else question.setText("Congratulations, Level up !");

                        question.setTextFill(Paint.valueOf("#ffffff"));
                        question.setAlignment(Pos.CENTER);

                        submit.setText("Continue...");
                        submit.setTextFill(Paint.valueOf("ffffff"));
                        submit.setFont(Font.font("", FontWeight.EXTRA_BOLD, 20));
                        submit.setMaxSize(200, 100);

                        currentScore.setText("Stage: " + stage + " / Score: " + score);

                        levelUpLayout.getChildren().addAll(Title, question, submit, correction, currentScore);
                        teacher.setScene(levelUpScene);
                        teacher.show();
                        Music(2);

                        submit.setOnAction(event1 -> {
                            generated = generate();
                            submit.setOnAction(this);
                            submit.setText("Submit");
                            submit.setOnAction(this);
                            submit.setTextFill(Paint.valueOf("#ffffff"));
                            submit.setFont(Font.font("", FontWeight.EXTRA_BOLD, 15));
                            submit.setBackground(new Background(new BackgroundFill(Paint.valueOf("0098ECFF"), null, null)));
                            submit.setMaxSize(120, 5);

                            layout.getChildren().removeAll(Title, question, answer, submit, correction, currentScore);
                            layout.getChildren().addAll(Title, question, answer, submit, correction, currentScore);
                            teacher.setScene(scene);
                            teacher.show();

                        });
                    }
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
            if (stage < 5) op = r.nextInt(3);
            else op = r.nextInt(4);

            if (op == 0) {
                result = first + second;
                operation = '+';
            } else if (op == 1) {
                result = first - second;
                operation = '-';
            } else if (op == 2) {
                result = first * second;
                operation = 'x';
            } else if (op == 3) {
                result = first / second;
                operation = '/';
            }
        } while (result < 0 || result > (100 * stage));

        generated = String.valueOf(result);
        question.setText(first + "    " + operation + "    " + second + "     =   ?");

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
