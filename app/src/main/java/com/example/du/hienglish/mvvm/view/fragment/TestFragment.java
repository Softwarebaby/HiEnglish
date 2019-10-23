package com.example.du.hienglish.mvvm.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.du.hienglish.R;
import com.example.du.hienglish.mvvm.model.Question;
import com.example.du.hienglish.mvvm.view.adapter.TestAdapter;
import com.example.du.hienglish.mvvm.view.widget.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestFragment extends Fragment {
    private final int QUESTION_COUNT = 10;

    @BindView(R.id.practise_tvNum)
    TextView numberText;
    @BindView(R.id.practise_tvTestTime)
    TextView timeText;
    @BindView(R.id.text_question)
    TextView questionText;
    @BindView(R.id.list_answer)
    ListView listView;
    @BindView(R.id.btn_last)
    Button lastBtn;
    @BindView(R.id.btn_next)
    Button nextBtn;

    private List<Question> questionList;
    private int index = 0;
    private int score = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);

        initData();
        initLastBtn();
        initNextBtn();
        initView(questionList.get(index));
        startTime();
        return view;
    }

    private void initData() {
        questionList = new ArrayList<>();
        for (int i = 0; i < QUESTION_COUNT; i++) {
            Question question = new Question();
            question.setQuestion("Du's unique voice ____ especially toyoung people.");
            List<String> answers = new ArrayList<>();
            answers.add("A attracts");
            answers.add("B draws");
            answers.add("C catches");
            answers.add("D appeals");
            question.setRightIndex(0);
            question.setAnswers(answers);
            questionList.add(question);
        }
    }

    private void initView(Question question) {
        initLastBtn();
        initNextBtn();
        questionText.setText(question.getQuestion());
        numberText.setText((index+1) + "/" + QUESTION_COUNT);
        List<String> answers = question.getAnswers();
        TestAdapter testAdapter = new TestAdapter(getActivity(), answers);
        listView.setAdapter(testAdapter);
        TestOnItemClickListener testOnItemClickListener = new TestOnItemClickListener();
        listView.setOnItemClickListener(testOnItemClickListener);
    }

    private void initLastBtn() {
        if (index == 0) {
            lastBtn.setVisibility(View.GONE);
        } else {
            lastBtn.setVisibility(View.VISIBLE);
        }
        lastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index >= 0) {
                    initView(lastQuestion());
                }
            }
        });
    }

    private void initNextBtn() {
        if (index == QUESTION_COUNT - 1) {
            nextBtn.setText("提交");
        } else {
            nextBtn.setText("下一题");
        }
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index < QUESTION_COUNT - 1) {
                    initView(nextQuestion());
                } else {
                    showAlertDialog();
                }
            }
        });
    }

    private Question nextQuestion() {
        return questionList.get(++index);
    }

    private Question lastQuestion() {
        return questionList.get(--index);
    }

    private void startTime() {

    }

    private void endTime() {

    }

    private void showAlertDialog() {
        AlertDialog.show(getActivity(), "注意",
                "已经是最后一题， 确定要提交吗？", new AlertDialog.PositiveOnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        super.onClick(dialog, which);
                        endTime();
                        AlertDialog.show(getActivity(), "您的最终成绩是：" + score);
                        score = 0;
                    }
                });
    }

    private class TestOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Question question = questionList.get(index);
            if (question.getRightIndex() == position) {
                score++;
            }
            if (index < QUESTION_COUNT - 1) {
                initView(nextQuestion());
            } else {
                showAlertDialog();
            }
        }
    }
}
