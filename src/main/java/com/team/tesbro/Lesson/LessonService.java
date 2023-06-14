package com.team.tesbro.Lesson;

import com.team.tesbro.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;

    public List<Lesson> getList() {
        return lessonRepository.findAll();
    }

    public List<Lesson> acLesson(Integer id){
        return lessonRepository.findByLessonId(id);
    }

    public Lesson getLesson(Integer id) {
        Optional<Lesson> lesson = this.lessonRepository.findById(id);
        if (lesson.isPresent()) {
            return lesson.get();
        } else {
            throw new DataNotFoundException("lesson not found");
        }
    }

    public List<Lesson> getTimeList(LocalDate data) {
        if (data != null) {
            return lessonRepository.findByLessonDate(data);
        } return null;
    }

    public List<LocalTime> getLessonTimes(LocalDate data){
        List<LocalTime> timeList = new ArrayList<>();
        List<Lesson> lessonList = new ArrayList<>();
        if(data != null){
             lessonList = lessonRepository.findByLessonDate(data);
            for (Lesson lesson : lessonList) {
                LocalTime lessonTime = lesson.getLessonTime();
                timeList.add(lessonTime);
            } return timeList;
        } return null;
    }
}
