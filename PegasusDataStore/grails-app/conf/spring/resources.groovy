package spring

import dao.HorseDAO
import dao.JockeyDAO
import dao.RaceCourseDAO
import dao.RaceDAO
import dao.RaceHorseJockeyDAO

beans = {
    raceDAO(RaceDAO)
    raceHorseJockeyDAO(RaceHorseJockeyDAO)
    horseDAO(HorseDAO)
    jockeyDAO(JockeyDAO)
    raceCourseDAO(RaceCourseDAO)
}
