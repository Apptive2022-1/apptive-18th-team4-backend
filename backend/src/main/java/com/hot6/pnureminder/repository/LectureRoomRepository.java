package com.hot6.pnureminder.repository;

import com.hot6.pnureminder.entity.LectureRoom;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface LectureRoomRepository extends JpaRepository<LectureRoom, Integer> {
    List<LectureRoom> findAllByBuildingNum(Integer buildingNum);


    @EntityGraph(attributePaths = {"building"})
    @Query("SELECT lr FROM LectureRoom lr JOIN lr.building b WHERE b.buildingName = :buildingName AND lr NOT IN (SELECT l.lectureRoom FROM Lecture l WHERE l.dayOfWeek = :dayOfWeek " +
            "AND ((l.startTime <= :currentTime AND :currentTime <= :endOfOngoingLecture) OR (l.startTime <= :startOfNextLecture)))")
    List<LectureRoom> findAvailableLectureRooms(String buildingName, Integer dayOfWeek, LocalTime endOfOngoingLecture, LocalTime startOfNextLecture, LocalTime currentTime);


    @EntityGraph(attributePaths = {"building"})
    @Query("SELECT lr, l FROM LectureRoom lr LEFT JOIN Lecture l ON l.lectureRoom = lr WHERE lr.building.buildingName = :buildingName AND (l IS NULL OR (l.dayOfWeek = :dayOfWeek AND ((l.startTime <= :currentTime AND :currentTime <= :endOfOngoingLecture) OR (l.startTime <= :startOfNextLecture))))")
    List<Object[]> findAvailableLectureRoomsAndLectures(String buildingName, Integer dayOfWeek, LocalTime endOfOngoingLecture, LocalTime startOfNextLecture, LocalTime currentTime);


}