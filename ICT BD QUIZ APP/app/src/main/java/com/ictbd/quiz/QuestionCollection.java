package com.ictbd.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ictbd.quiz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestionCollection extends AppCompatActivity {

    RadioGroup radioGroup;
    TextView lblQuestion;
    RadioButton optionA;
    RadioButton optionB;
    RadioButton optionC;
    RadioButton optionD;
    Button confirm;
    String rightAnswer;
    String Answer;
    public static List<QuestionModule> question_list;
    int score;
    public static String SUBJECT_NAME = "";
    public static ArrayList<ArrayList<QuestionModule>> questionBank = new ArrayList<>();
    public static ArrayList<HashMap<String, String>> subjectList = new ArrayList<>();
    LinearLayout rootLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        rootLay = findViewById(R.id.rootLay);
        confirm = findViewById(R.id.confirm);
        lblQuestion = findViewById(R.id.lblPergunta);
        optionA = findViewById(R.id.opcaoA);
        optionB = findViewById(R.id.opcaoB);
        optionC = findViewById(R.id.opcaoC);
        optionD = findViewById(R.id.opcaoD);
        radioGroup = findViewById(R.id.radioGroup);
        score = 0;

        loadQuestion();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadQuestion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rootLay != null)
            rootLay.startAnimation(AnimationUtils.loadAnimation(QuestionCollection.this, R.anim.middle_to_top));
    }

    private void loadQuestion() {
        if (question_list != null && question_list.size() > 0) {
            QuestionModule q = question_list.remove(0);
            lblQuestion.setText(q.getQuestion());
            List<String> answers = q.getAnswers();

            optionA.setText(answers.get(0));
            optionB.setText(answers.get(1));
            optionC.setText(answers.get(2));
            optionD.setText(answers.get(3));
            rightAnswer = q.getRightAnswer();
        } else {
            Intent intent = new Intent(this, ScoreActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
    }

    public void loadAnswer(View view) {
        int op = radioGroup.getCheckedRadioButtonId();

        if (op == R.id.opcaoA) {
            Answer = "A";
        } else if (op == R.id.opcaoB) {
            Answer = "B";
        } else if (op == R.id.opcaoC) {
            Answer = "C";
        } else if (op == R.id.opcaoD) {
            Answer = "D";
        } else {
            return; // no option selected
        }

        radioGroup.clearCheck();
        startActivity(isRightOrWrong(Answer));
    }

    private Intent isRightOrWrong(String Answer) {
        Intent screen;
        if (Answer.equals(rightAnswer)) {
            this.score += 1;
            screen = new Intent(this, RightActivity.class);
        } else {
            screen = new Intent(this, WrongActivity.class);
        }
        return screen;
    }

    // ===================================================================
    public static ArrayList<QuestionModule> questions;

    public static void createQuestionBank() {
        subjectList = new ArrayList<>();
        questionBank = new ArrayList<>();

        // ------------- Subject 1: Math
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("What is 12 × 8?", "B", "80", "96", "104", "108"));
            add(new QuestionModule("What is the square root of 144?", "A", "12", "14", "16", "18"));
            add(new QuestionModule("Solve: 15 + 27 - 9", "C", "31", "33", "33", "35"));
            add(new QuestionModule("What is 7²?", "C", "42", "48", "49", "50"));
            add(new QuestionModule("What is 15% of 200?", "B", "25", "30", "35", "40"));
        }};
        QuestionModule.createQuestionsForSubject("Math", R.drawable.math, questions);

// ------------- Subject 2: Tech
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("Which language is used for Android apps?", "C", "Python", "C++", "Java", "Ruby"));
            add(new QuestionModule("What does HTML stand for?", "B", "Hyperlinks Text Markup Language", "HyperText Markup Language", "Home Tool Markup Language", "Hyperlink Markup Level"));
            add(new QuestionModule("Which symbol is used for comments in Java?", "A", "//", "#", "/*", "<!--"));
            add(new QuestionModule("What is a loop that never ends called?", "D", "For loop", "While loop", "Do-while loop", "Infinite loop"));
            add(new QuestionModule("Which keyword is used to create a class in Java?", "C", "method", "object", "class", "package"));
        }};
        QuestionModule.createQuestionsForSubject("Tech", R.drawable.tech, questions);

// ------------- Subject 3: General Knowledge
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("Which planet is known as the Red Planet?", "A", "Mars", "Venus", "Jupiter", "Saturn"));
            add(new QuestionModule("What is the largest ocean on Earth?", "B", "Atlantic Ocean", "Pacific Ocean", "Indian Ocean", "Arctic Ocean"));
            add(new QuestionModule("Who wrote 'Romeo and Juliet'?", "C", "Charles Dickens", "Mark Twain", "William Shakespeare", "Jane Austen"));
            add(new QuestionModule("Which gas do plants absorb from the atmosphere?", "D", "Oxygen", "Nitrogen", "Helium", "Carbon Dioxide"));
            add(new QuestionModule("In which year did the first man land on the Moon?", "B", "1967", "1969", "1971", "1973"));
        }};
        QuestionModule.createQuestionsForSubject("GK", R.drawable.gk, questions);

// ------------- Subject 4: Animals
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("Which mammal lays eggs?", "C", "Kangaroo", "Elephant", "Platypus", "Lion"));
            add(new QuestionModule("What is the fastest land animal?", "A", "Cheetah", "Lion", "Horse", "Elephant"));
            add(new QuestionModule("Which bird is known for its colorful feathers?", "D", "Crow", "Sparrow", "Pigeon", "Peacock"));
            add(new QuestionModule("A group of lions is called?", "A", "Pride", "Herd", "Pack", "Flock"));
            add(new QuestionModule("Which animal is known as the Ship of the Desert?", "B", "Elephant", "Camel", "Horse", "Llama"));
        }};
        QuestionModule.createQuestionsForSubject("Animals", R.drawable.animals, questions);

// ------------- Subject 5: Sports
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("Which sport uses a racket and a shuttlecock?", "C", "Tennis", "Badminton", "Badminton", "Squash"));
            add(new QuestionModule("How many players are on a soccer team?", "B", "9", "11", "10", "12"));
            add(new QuestionModule("Which country won the FIFA World Cup 2018?", "A", "France", "Croatia", "Brazil", "Germany"));
            add(new QuestionModule("In which sport is the term 'home run' used?", "D", "Football", "Basketball", "Hockey", "Baseball"));
            add(new QuestionModule("Which sport is known as the 'king of sports'?", "B", "Basketball", "Soccer", "Tennis", "Cricket"));
        }};
        QuestionModule.createQuestionsForSubject("Sports", R.drawable.sports, questions);

// ------------- Subject 6: History
        questions = new ArrayList<QuestionModule>() {{
            add(new QuestionModule("Who was the first President of the USA?", "A", "George Washington", "Thomas Jefferson", "Abraham Lincoln", "John Adams"));
            add(new QuestionModule("In which year did World War II end?", "B", "1944", "1945", "1946", "1947"));
            add(new QuestionModule("Who discovered America?", "C", "Vasco da Gama", "Ferdinand Magellan", "Christopher Columbus", "Marco Polo"));
            add(new QuestionModule("The Great Wall is located in which country?", "D", "Japan", "India", "Korea", "China"));
            add(new QuestionModule("Who was known as the 'Maid of Orleans'?", "B", "Queen Elizabeth", "Joan of Arc", "Marie Antoinette", "Catherine the Great"));
        }};
        QuestionModule.createQuestionsForSubject("History", R.drawable.history, questions);



    }
}
