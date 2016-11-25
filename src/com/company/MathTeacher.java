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
    Scene scene;
    VBox layout;

    Label question;
    Label Title;
    TextField answer;
    Label correction;
    Button submit;

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
        layout.getChildren().addAll(Title, question, answer, submit, correction);

        scene = new Scene(layout, 600, 600);
        teacher.setScene(scene);
        teacher.show();
        generated = generate();
    }

    @Override
    public void handle(Event event) {


        if (event.getSource() == submit) {
            if (!next) {
                if (answer.getText().equals(generated)) {
                    correction.setText("Correct !");
                    Music(true);
                } else {
                    correction.setText("Wrong, Correct answer is: " + generated);
                    Music(false);
                }
                submit.setText("Next");
                next = true;

            } else {
                answer.setText("");
                generated = generate();
                next = false;
                submit.setText("Submit");
                correction.setText("");
            }
        }
    }

    public String generate() {

        int first = r.nextInt(100);
        int second = r.nextInt(100);
        int op = r.nextInt(3);
        String generated = "";
        char operation = ' ';
        if (op == 0) {
            generated = String.valueOf(first + second);
            operation = '+';
        }
        if (op == 1) {
            generated = String.valueOf(first - second);
            operation = '-';
        }
        if (op == 2) {
            generated = String.valueOf(first * second);
            operation = 'x';
        }
        question.setText(first + " " + operation + " " + second + " = ?");

        System.out.println(generated);
        return generated;
    }

    public static void Music(boolean t) {
        AudioPlayer joueur = AudioPlayer.player;

        AudioStream background;
        AudioStream stream;

        AudioData data = null;

        AudioDataStream test = null;

        try {

            if (t) {
                background = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\IdeaProjects\\Tutoriel\\Correct-answer.wav"));
                data = background.getData();
            }

            if (!t) {
                stream = new AudioStream(new FileInputStream("C:\\Users\\Oussama\\IdeaProjects\\Tutoriel\\Wrong-answer-sound-effect.wav"));
                data = stream.getData();
            }
            test = new AudioDataStream(data);

        } catch (IOException e) {
            e.printStackTrace();
        }

        joueur.start(test);

    }
}
