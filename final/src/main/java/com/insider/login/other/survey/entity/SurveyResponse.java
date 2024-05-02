package com.insider.login.other.survey.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class SurveyResponse {

    @Id
    @Column(name = "SURVEY_RESPONSE_NO", nullable = false)
    private int surveyResponseNo;       // 응답 번호

    @Column(name = "MEMBER_ID", nullable = false)
    private int memberId;               // 응답 제출자

    @Column(name = "ANSWER_NO", nullable = false)
    private int answerNo;               // 답변 번호

    protected SurveyResponse() {
    }

    public SurveyResponse(int surveyResponseNo, int memberId, int answerNo) {
        this.surveyResponseNo = surveyResponseNo;
        this.memberId = memberId;
        this.answerNo = answerNo;
    }

    public int getSurveyResponseNo() {
        return surveyResponseNo;
    }

    public int getMemberId() {
        return memberId;
    }

    public int getAnswerNo() {
        return answerNo;
    }

    @Override
    public String toString() {
        return "SurveyResponse{" +
                "surveyResponseNo=" + surveyResponseNo +
                ", memberId=" + memberId +
                ", answerNo=" + answerNo +
                '}';
    }
}
