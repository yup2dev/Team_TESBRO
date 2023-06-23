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

    public Lesson getLesson(Integer id) {
        Optional<Lesson> lesson = this.lessonRepository.findById(id);
        if (lesson.isPresent()) {
            return lesson.get();
        } else {
            throw new DataNotFoundException("lesson not found");
        }
    }

    public List<Lesson> getAcLesson(Integer id){
        return lessonRepository.findByAcademyId(id);
    }

    public List<Lesson> getTimeList(LocalDate data) {
        if (data != null) {
            return lessonRepository.findByLessonDate(data);
        } return null;
    }

    public List<LocalTime> getLessonTimes(LocalDate date, List<Lesson> lessonList) {
        List<LocalTime> timeList = new ArrayList<>();
        if (date != null && lessonList != null) {
            for (Lesson lesson : lessonList) {
                if (lesson.getLessonDate().equals(date)) {
                    LocalTime lessonTime = lesson.getLessonTime();
                    timeList.add(lessonTime);
                }
            }
        }
        return timeList;
    }
}