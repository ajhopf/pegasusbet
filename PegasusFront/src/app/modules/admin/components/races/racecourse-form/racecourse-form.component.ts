import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms'
import { minItemsValidator } from '../../../../shared/validators/custom-validators'
import { RacecourseService } from '../../../../../services/racecourse/racecourse.service'
import { CreateRacecourseRequest } from '../../../../../models/race-course/CreateRacecourseRequest'
import { take } from 'rxjs'
import { DialogService } from 'primeng/dynamicdialog'
import { MessageService } from 'primeng/api'

@Component({
  selector: 'app-racecourse-form',
  templateUrl: './racecourse-form.component.html',
  styleUrls: ['./racecourse-form.component.css']
})
export class RacecourseFormComponent {

  constructor(
    private formBuilder: FormBuilder,
    private racecourseService: RacecourseService,
    private messageService: MessageService,
  ) {}

  createRacecourseForm = this.formBuilder.group({
    racecourse: [null, Validators.required]
  })

  handleCreateRacecourseSubmit() {
    console.log(this.createRacecourseForm.value)

    const racecourse = this.createRacecourseForm.value.racecourse

    if (racecourse) {
      const racecourseRequest: CreateRacecourseRequest = {
        name: racecourse
      }

      this.racecourseService
        .createRacecourse(racecourseRequest)
        .pipe(take(1))
        .subscribe({
          next: racecourseResponse => {
            console.log(racecourseResponse)

            this.messageService.add({
              severity: 'success',
              summary: 'Circuito criado com sucesso',
              detail: `Id: ${racecourseResponse.raceCourse.id} | Circuito: ${racecourseResponse.raceCourse.name}`,
            })
          },
          error: err => {
            this.messageService.add({
              severity: 'error',
              summary: 'Falha ao criar circuito',
            })
          }
        })
    }



  }
}
