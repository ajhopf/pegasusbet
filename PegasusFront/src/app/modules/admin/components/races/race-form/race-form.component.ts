import { Component, OnInit } from '@angular/core';
import { RacecourseService } from "../../../../../services/racecourse/racecourse.service";
import { take } from "rxjs";
import { RaceCourse } from "../../../../../models/race-course/RaceCourse";
import { FormArray, FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Horse } from "../../../../../models/horse/Horse";
import { HorseService } from "../../../../../services/horse/horse.service";
import { JockeyService } from "../../../../../services/jockey/jockey.service";
import { Jockey } from "../../../../../models/jockey/Jockey";
import { MessageService } from "primeng/api";
import { minItemsValidator } from "../../../../shared/validators/custom-validators";
import { CreateRaceRequest, HorseJockey } from "../../../../../models/races/CreateRaceRequest";
import { DatePipe } from "@angular/common";
import { RacesService } from "../../../../../services/races/races.service";


@Component({
  selector: 'app-race-form',
  templateUrl: './race-form.component.html',
  styleUrls: ['./race-form.component.css']
})
export class RaceFormComponent implements OnInit{
  racecourses: RaceCourse[] = []
  horses: Horse[] = []
  jockeys: Jockey[] = []

  selectedHorse: Horse | undefined
  selectedJockey: Jockey | undefined

  lineupNumber = 1

  createRaceForm = this.formBuilder.group({
    racecourse: [null, Validators.required],
    date: [null, Validators.required],
    horseJockeys: this.formBuilder.array([], minItemsValidator(2))
  })

  get horseJockeys(): FormArray {
    return this.createRaceForm.get('horseJockeys') as FormArray;
  }

  constructor(
    private formBuilder: FormBuilder,
    private raceService: RacesService,
    private racecourseService: RacecourseService,
    private horseService: HorseService,
    private jockeyService: JockeyService,
    private messageService: MessageService,
    private datePipe: DatePipe
    ) {}

  ngOnInit(): void {
    this.fetchRacecourses()
    this.fetchHorses()
    this.fetchJockeys()
  }

  handleCreateRaceSubmit() {
    const date = this.createRaceForm.get('date')?.value;

    let formattedDate = '01/01/2025'
    let formattedTime = '10:10'
    const raceCourse = this.createRaceForm.value.racecourse! as RaceCourse

    if (date) {
      formattedDate = this.datePipe.transform(date, 'dd/MM/yyyy')!
      formattedTime = this.datePipe.transform(date, 'HH:mm')!
    }

    const raceRequest: CreateRaceRequest = {
      date: formattedDate,
      time: formattedTime,
      horseJockeys: this.createRaceForm.value.horseJockeys as HorseJockey[],
      raceCourseId: raceCourse.id
    }

    this.raceService.createRace(raceRequest)
      .pipe(take(1))
      .subscribe({
        next: (value) => {
          console.log(value)

          this.messageService.add({
            severity: 'success',
            summary: 'Corrida criada com sucesso!'
          })

          this.createRaceForm.controls.horseJockeys.clear()
          this.createRaceForm.reset()
        },
        error: (err) => {
          console.log(err)

          this.messageService.add({
            severity: 'error',
            summary: 'Falha ao criar a corrida'
          })
        }
      })
  }

  addHorseJockey() {
    if (this.selectedHorse && this.selectedJockey) {

      const alreadyExists = this.horseJockeys.controls.some(control => {
        const horseJockey = control.value;
        return horseJockey.horseId === this.selectedHorse!.id || horseJockey.jockeyId === this.selectedJockey!.id;
      });

      if (alreadyExists) {
        this.messageService.add({
          severity: 'warn',
          summary: 'Cavalo ou Jockey já foram selecionados para a corrida'
        })
        return
      }

      const horseJockeyGroup = this.formBuilder.group({
        horseId: [this.selectedHorse.id, Validators.required],
        jockeyId: [this.selectedJockey.id, Validators.required],
        number: [this.lineupNumber, Validators.required ]
      });

      this.horseJockeys.push(horseJockeyGroup)

      this.lineupNumber += 1
      this.selectedHorse = undefined
      this.selectedJockey = undefined
    } else {
      this.messageService.add({
        severity: 'warn',
        summary: 'Você deve selecionar um cavalo e um jockey'
      })
      return
    }
  }

  removeHorseJockey(index: number) {
    this.horseJockeys.removeAt(index);
  }

  getHorseName(id: number) {
    return this.horses.find(h => h.id == id)?.name
  }

  getJockeyName(id: number) {
    return this.jockeys.find(j => j.id == id)?.name
  }

  fetchRacecourses(): void {
    this.racecourseService.fetchRacecourses(0, 1000)
      .pipe(take(1))
      .subscribe({
        next: (response) => {
          if (response) {
            this.racecourses = response.raceCourses
          }
        },
        error: (err) => {
          console.log(err)
        }
      })
  }

  fetchHorses() {
    this.horseService.fetchHorses()
      .pipe(take(1))
      .subscribe({
        next: (response) => {
          if (response) {
            this.horses = response.horses
          }
        },
        error: (err) => {
          console.log(err)
        }
      })
  }

  fetchJockeys() {
    this.jockeyService.fetchJockeys(0, 1000)
      .pipe(take(1))
      .subscribe({
        next: (response) => {
          if (response) {
            this.jockeys = response.jockeys
          }
        },
        error: (err) => {
          console.log(err)
        }
      })
  }
}
