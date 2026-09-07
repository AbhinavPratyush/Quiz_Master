package com.QuizMaster.UserServ.DB;

import com.QuizMaster.UserServ.Attempts.Attempt;
import com.QuizMaster.UserServ.DTO.AttemptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttemptServiceDB {

    @Autowired
    AttemptRepository attemptRepo;

    
    
    public boolean isActive(Long quizID, String userID){
        if(attemptRepo.noOfAttemptsLeft(userID,quizID)==0)
            return false;
        return true;
    }
    public Optional<Attempt> isLastExp(Long quizID, String userID){
        return attemptRepo.lastOpenAttempt(quizID, userID);


    }

    public List<AttemptDTO> loadAttempts(String userId) {

        List<PreviousAttemptsProjection> rows = attemptRepo.loadAttempts(userId);
        List<AttemptDTO> send=new ArrayList<>();
        for(PreviousAttemptsProjection row:rows){

            AttemptDTO a=new AttemptDTO(
                    row.getAttemptId(),
                    row.getScore(),
                    row.getHasSubmitted(),
                    row.getStartedAt(),
                    row.getTimeLimit()
            );
            System.out.println(row.getAttemptId()+"is created");
            send.add(a);
        }

        return send;


    }
}
