package com.arz.pdms;

import com.arz.pdms.test.model.Answer;
import com.arz.pdms.test.model.Question;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        try {

            File file = new File("D:\\Ramsath\\PMS\\question.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Question.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Question question= (Question) jaxbUnmarshaller.unmarshal(file);

            List<Question> fQueList = question.getQuestion();


            for (Question fQue: fQueList) {
                System.out.println(fQue.getId() + " " + fQue.getQuestionname());
                System.out.println("Answers:");
                List<Answer> list = fQue.getAnswers();
                for (Answer ans : list)
                    System.out.println(ans.getId() + " " + ans.getAnswername() + "  " + ans.getPostedby());

                List<Question> sQueList = fQue.getQuestion();
                if(sQueList!=null){
                    for (Question sQue: sQueList) {
                        System.out.println(sQue.getId() + " " + sQue.getQuestionname());
                        System.out.println("Answers:");
                        List<Answer> sAlist = sQue.getAnswers();
                        for (Answer sAns : sAlist)
                            System.out.println(sAns.getId() + " " + sAns.getAnswername() + "  " + sAns.getPostedby());

                        List<Question> ssQueList = sQue.getQuestion();
                        for (Question ssQue: ssQueList) {
                            System.out.println(ssQue.getId() + " " + ssQue.getQuestionname());
                            System.out.println("Answers:");
                            List<Answer> ssAlist = ssQue.getAnswers();
                            for (Answer ssAns : ssAlist)
                                System.out.println(ssAns.getId() + " " + ssAns.getAnswername() + "  " + ssAns.getPostedby());

                        }

                    }
                }
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }
}
