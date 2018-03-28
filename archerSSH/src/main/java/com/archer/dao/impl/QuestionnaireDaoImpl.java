package com.archer.dao.impl;

import org.springframework.stereotype.Component;

import com.archer.core.impl.BaseDaoImpl;
import com.archer.dao.QuestionnaireDao;
import com.archer.model.Questionnaire;
@Component("questionnaireDao")
public class QuestionnaireDaoImpl extends BaseDaoImpl<Questionnaire> implements QuestionnaireDao{

}
